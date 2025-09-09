use i18n::util::file::input;
use itertools::Itertools;
use std::collections::HashMap;
use unicode_normalization::UnicodeNormalization;

fn line_parse(lines: &str) -> Vec<(&str, &str)> {
    lines
        .split("\n")
        .filter(|l| !l.is_empty())
        .map(|l| l.split_once(" ").unwrap())
        .collect()
}

// Finds all the character positions of the once that are willing to mutate
fn possible_mutations(word: &str) -> Vec<usize> {
    word.chars()
        .enumerate()
        .filter_map(|(pos, c)| (c.nfd().collect::<String>() != c.to_string()).then_some(pos))
        .collect::<Vec<usize>>()
}

// Returns all the unicode variants of a (pass)word
fn get_mutations(word: &str) -> Vec<String> {
    let options = possible_mutations(&word);
    let base = std::iter::once(word.to_string());

    let variants = (1..=options.len())
        .flat_map(|i| options.iter().combinations(i))
        .map(|opts| {
            word.chars()
                .enumerate()
                .map(|(o, c)| {
                    if opts.contains(&&o) {
                        c.nfd().collect::<String>()
                    } else {
                        c.to_string()
                    }
                })
                .collect::<String>()
        });

    return variants.chain(base).collect();
}

fn main() {
    let input = input(10);
    let (auth, login_attempts) = input.split_once("\n\n").unwrap();

    let auth: HashMap<_, _> = line_parse(auth).into_iter().collect();
    let mut password_map: HashMap<String, (Vec<String>, bool)> = HashMap::new();

    let valid: i32 = line_parse(login_attempts)
        .iter()
        .map(|(usr, pwd)| {
            let simple = pwd.nfc().collect::<String>();

            if !password_map.contains_key(&simple) {
                // No cache present. Find all virants and check if there is a valid one.
                let variants = get_mutations(&simple);

                let hash = *auth.get(usr).expect("Hash not found");
                let has_valid = variants
                    .iter()
                    .any(|v| bcrypt::verify(v, hash).expect("Invalid hash"));

                password_map.insert(simple.clone(), (variants, has_valid));
            }

            // Now we can check normalized passwords :)
            password_map
                .get(&simple)
                .expect("Expected password to be present")
                .1 as i32
        })
        .sum();

    print!("Amount of valid attempts: {}", valid);
}

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

fn possible_mutations(word: &str) -> Vec<usize> {
    word
        .chars()
        .enumerate()
        .filter_map(|(pos, c)| {
            (c.nfd().collect::<String>() != c.to_string()).then_some(pos)
        })
        .collect::<Vec<usize>>()
}

fn get_mutations(word: &str) -> Vec<String> {
    let mut variants = vec![];
    variants.push(word.to_string());

    let options = possible_mutations(&word);

    for i in 1..=options.len() {
        for opts in options.iter().combinations(i) {
            let fixed = word.clone()
                .chars()
                .enumerate()
                .map(|(o, c)| {
                    if opts.contains(&&o) {
                        c.nfd().collect::<String>()
                    } else {
                        c.to_string()
                    }
                })
                .collect::<String>();
            variants.push(fixed);
        }
    }
    return variants;
}

fn main() {
    let input = input(10);
    let (auth, login_attempts) = input.split_once("\n\n").unwrap();

    let mut auth_db = HashMap::new();
    for (k, v) in line_parse(auth) {
        auth_db.insert(k, v);
    }

    let mut password_map: HashMap<String, (Vec<String>, bool)> = HashMap::new();

    let mut amount: i32 = 0;

    for (usr, pwd) in line_parse(login_attempts) {
        let simple = pwd.nfc().collect::<String>();

        let cached = password_map.contains_key(&simple);

        if !cached {
            let variants = get_mutations(&simple);

            let h = *auth_db.get(usr).expect("Hash not found");
            let has_valid = variants
                .iter()
                .any(|v| bcrypt::verify(v, h).expect("Invalid hash"));

            password_map.insert(simple.clone(), (variants, has_valid));
        }

        let valid = password_map.get(&simple).expect("Expected password to be present").1 as i32;
        amount += valid;
    }

    dbg!(amount);
}

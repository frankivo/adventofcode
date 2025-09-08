use i18n::util::file::input;
use itertools::Itertools;
use substring::Substring;
use std::{collections::HashMap, env::var};
use unicode_normalization::UnicodeNormalization;

fn line_parse(lines: &str) -> Vec<(&str, &str)> {
    lines
        .split("\n")
        .filter(|l| !l.is_empty())
        .map(|l| l.split_once(" ").unwrap())
        .collect()
}

fn main() {
    let input = input(10);
    let (auth, login_attempts) = input.split_once("\n\n").unwrap();

    // let mut auth_db = HashMap::new();
    // for (k, v) in line_parse(auth) {
    //     auth_db.insert(k, v);
    // }

    let mut password_map : HashMap<&str, Vec<String>> = HashMap::new();

    for (usr, pwd) in line_parse(login_attempts) {
        dbg!(pwd);

        let simple = &pwd.nfc().collect::<String>();
        dbg!(simple);

        let variants = password_map.entry(&simple).or_default();
        variants.push(simple.to_string());

        let options = simple
            .chars()
            .enumerate()
            .filter_map(|(pos, c)| (c.nfd().collect::<String>() != c.to_string()).then_some(pos))
            .collect::<Vec<usize>>();

        for i in 1..=options.len() {
            for opts in options.iter().combinations(i) {
                let fixed = pwd.chars().enumerate().map(|(o, c)| if opts.contains(&&o) {c.nfd().collect::<String>() } else {c.to_string()} ).collect::<String>();
                variants.push(fixed); // Only insert into the password_map if one of these variant results in a valid password.
            }
        }



        break;
    }

    //     let h = *auth_db.get(usr).expect("Hash not found");
    //     let p = pwd.nfc().collect::<String>();
    //     let v = bcrypt::verify(p, h).expect("Invalid hash");
    //     dbg!(usr, v);
    // }
}

use i18n::util::file::input;
use std::collections::HashMap;
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

    let mut auth_db = HashMap::new();
    for (k, v) in line_parse(auth) {
        auth_db.insert(k, v);
    }

    for (usr, pwd) in line_parse(login_attempts) {
        let h = *auth_db.get(usr).expect("Hash not found");
        let p = pwd.nfc().collect::<String>();
        let v = bcrypt::verify(p, h).expect("Invalid hash");
        dbg!(usr, v);
    }
}

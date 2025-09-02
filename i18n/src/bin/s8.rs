
use i18n::util::file;
use regex::Regex;

use unidecode::unidecode;

fn valid(pw: &str) -> bool {
    // a length of at least 4 and at most 12
    (4..=12).contains(&pw.chars().count())
    // at least one digit
    && Regex::new(r"[\d]").unwrap().is_match(pw)
    // at least one accented or unaccented vowel1 (a, e, i, o, u) (examples: i, Á or ë).
    && Regex::new(r"[aeiou]").unwrap().is_match(&normalize(pw).to_lowercase())
}

fn normalize(pw: &str) -> String {
    return  unidecode(pw);
}


fn main() {
    for v in file::input(8).lines().map(|pw|(pw, valid(pw))) {
        dbg!(v);
    }

    let valid = file::input(8).lines().filter(|pw| valid(pw)).count();
    println!("valid: {}", valid);

    dbg!(normalize("pD9Ĉ*jXh"));
}

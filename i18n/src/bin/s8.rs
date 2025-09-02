use i18n::util::file;
use regex::Regex;

fn valid(pw: &str) -> bool {
    // a length of at least 4 and at most 12
    (4..=12).contains(&pw.chars().count())
    // at least one digit
    && Regex::new(r"[\d]").unwrap().is_match(pw)
    // at least one uppercase letter (with or without accents, examples: A or Ż)
}

fn main() {
    for v in file::input(8).lines().map(|pw|(pw, valid(pw))) {
        dbg!(v);
    }

    let valid = file::input(8).lines().filter(|pw| valid(pw)).count();
    println!("valid: {}", valid);
}

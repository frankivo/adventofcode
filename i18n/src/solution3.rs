mod file;

use regex::Regex;

fn valid(pw: &str) -> bool {
    // a length of at least 4 and at most 12
    (4..=12).contains(&pw.chars().count())
    // at least one digit
    && Regex::new(r"[\d]").unwrap().is_match(pw)
    // at least one uppercase letter (with or without accents, examples: A or Ż)
    && pw.chars().any(|c| c.is_uppercase())
    // at least one lowercase letter (with or without accents, examples: a or ŷ)
    && pw.chars().any(|c| c.is_lowercase())
    // at least one character that is outside the standard 7-bit ASCII character set (examples: Ű, ù or ř)
    && pw.chars().any(|c| !c.is_ascii())
}

fn main() {
    let valid = file::input(3).lines().filter(|pw| valid(pw)).count();
    println!("valid: {}", valid);
}

mod file;

use regex::Regex;

fn valid_length(pw: &str) -> bool {
    let valid = 4..=12;
    return valid.contains(&pw.chars().count());
}

fn has_digit(pw: &str) -> bool {
    let rx = Regex::new(r"[\d]").unwrap();
    return rx.is_match(pw);
}

fn valid_casing(pw: &str) -> bool {
    return pw != pw.to_uppercase() && pw != pw.to_lowercase();
}

fn has_non_ascii(pw: &str) -> bool {
    let rx = Regex::new(r"^[\x20-\x7E]+$").unwrap();
    return !rx.is_match(pw);
}

fn valid(pw: &str) -> bool {
    return valid_length(pw)
        && has_digit(pw)
        && valid_casing(pw)
        && has_non_ascii(pw);
}

fn main() {
    let input = file::input(3);

    let valid = input.lines().filter(|pw| valid(pw)).count();
    println!("valid: {}", valid);
}

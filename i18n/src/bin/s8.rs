use i18n::util::file;
use itertools::Itertools;
use regex::Regex;
use unidecode::unidecode;

fn valid(pw: &str) -> bool {
    // a length of at least 4 and at most 12
    (4..=12).contains(&pw.chars().count())
    // at least one digit
    && Regex::new(r"[\d]").unwrap().is_match(pw)
    // at least one accented or unaccented vowel1 (a, e, i, o, u) (examples: i, Á or ë).
    && Regex::new(r"[aeiou]").unwrap().is_match(&unidecode(pw).to_lowercase())
    // at least one accented or unaccented consonant, examples: s, ñ or ŷ
    && Regex::new(r"[b-df-hj-np-tv-z]").unwrap().is_match(&unidecode(pw).to_lowercase())
    // no recurring letters in any form.
    && !unidecode(pw).to_lowercase().chars().counts().values().any(|&c| c > 1)
}

fn main() {
    let valid = file::input(8).lines().filter(|pw| valid(pw)).count();
    println!("valid: {}", valid);
}

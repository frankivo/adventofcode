use i18n::util::file;
use itertools::Itertools;
use regex::Regex;
use unidecode::unidecode;

fn valid(pw: &str, vowels: &Regex, consonants: &Regex) -> bool {
    let ascii_str = unidecode(pw).to_lowercase();

    // a length of at least 4 and at most 12
    (4..=12).contains(&pw.chars().count())
    // at least one digit
    && pw.chars().any(|c| c.is_ascii_digit())
    // at least one accented or unaccented vowel1 (a, e, i, o, u) (examples: i, Á or ë).
    && vowels.is_match(&ascii_str)
    // at least one accented or unaccented consonant, examples: s, ñ or ŷ
    && consonants.is_match(&ascii_str)
    // no recurring letters in any form.
    && !ascii_str.chars().counts().values().any(|&c| c > 1)
}

fn main() {
    let vowels = Regex::new(r"[aeiou]").unwrap();
    let consonats = Regex::new(r"[b-df-hj-np-tv-z]").unwrap();

    let valid = file::input(8)
        .lines()
        .filter(|pw| valid(pw, &vowels, &consonats))
        .count();
    println!("valid: {}", valid);
}

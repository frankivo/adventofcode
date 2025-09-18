use encoding::all::{ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE};
use encoding::{DecoderTrap, Encoding};
use i18n::util::file::input;
use itertools::Itertools;
use regex::Regex;

fn is_word(word: &str) -> bool {
    let matcher = Regex::new(r"[A-Za-z]").unwrap();
    matcher.is_match(word)
}

fn try_decode(hex: &[u8], enc: &'static dyn Encoding) -> Option<String> {
    if let Ok(word) = enc.decode(&hex, DecoderTrap::Strict) {
        let word = word.replace('\u{feff}', "");
        if is_word(&word) {
            return Some(word);
        }
    }
    None
}

fn decode(raw: &str) -> String {
    let pairs = raw.chars().tuples::<(char, char)>();
    let word_hex: Vec<u8> = pairs
        .map(|p| {
            let m = format!("{}{}", p.0, p.1);
            u8::from_str_radix(&m, 16).unwrap()
        })
        .collect();

    let encodings: [&'static dyn Encoding; 4] = [UTF_8, UTF_16LE, UTF_16BE, ISO_8859_1];
    for enc in encodings {
        if let Some(s) = try_decode(&word_hex, enc) {
            return s;
        }
    }

    return "error".to_owned();
}

fn main() {
    let input = input(13);
    let (words, crossword) = input.split_once("\n\n").expect("Failed to read data");

    let words: Vec<String> = words.lines().map(decode).collect();
    let solution: usize = crossword
        .lines()
        .map(|q| {
            let rx = Regex::new(&format!("^{}$", q.trim())).unwrap();
            words
                .iter()
                .position(|word| rx.is_match(&word))
                .map_or(0, |idx| idx + 1)
        })
        .sum();

    println!("Soltion: {}", solution);
}

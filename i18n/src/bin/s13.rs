use encoding::all::{ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE};
use encoding::{DecoderTrap, Encoding};
use i18n::util::file::input;
use itertools::Itertools;

fn decode(raw: &str) -> String {
    let pairs = raw.chars().tuples::<(char, char)>();
    let word: Vec<u8> = pairs
        .map(|p| {
            let m = format!("{}{}", p.0, p.1);
            u8::from_str_radix(&m, 16).unwrap()
        })
        .collect();

    if let Some(s) = UTF_8.decode(&word, DecoderTrap::Strict).ok() {
        dbg!(&s.replace("\u{feff}", ""), "utf8");
        return s;
    } else if let Some(s) = UTF_16LE.decode(&word, DecoderTrap::Strict).ok() {
        dbg!(&s.replace("\u{feff}", ""), "UTF_16LE");
        return s;
    } else if let Some(s) = UTF_16BE.decode(&word, DecoderTrap::Strict).ok() {
        dbg!(&s.replace("\u{feff}", ""), "UTF_16BE");
        return s;
    } else if let Some(s) = ISO_8859_1.decode(&word, DecoderTrap::Strict).ok() {
        dbg!(&s.replace("\u{feff}", ""), "ISO_8859_1");
        return s;
    }

    return "error".to_owned();
}

fn main() {
    let input = input(13);
    let (words, _crossword) = input.split_once("\n\n").expect("Failed to read data");

    let _data: Vec<String> = words.lines().map(decode).collect();
    // for w in data {
    //     dbg!(w);
    // }
}

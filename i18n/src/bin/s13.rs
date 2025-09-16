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
    String::from_utf8(word).unwrap_or("error".to_owned())
}

fn main() {
    let input = input(13);
    let (words, _crossword) = input.split_once("\n\n").expect("Failed to read data");

    let data = words.lines().map(decode);
    for w in data {
        dbg!(w);
    }
}

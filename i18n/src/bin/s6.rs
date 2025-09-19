use encoding::all::ISO_8859_1;
use encoding::{EncoderTrap, Encoding};
use i18n::util::{crossword, file};

fn latin(word: &str) -> String {
    return String::from_utf8(ISO_8859_1.encode(word, EncoderTrap::Ignore).unwrap()).unwrap();
}

fn main() {
    let input = file::input(6);
    let (words, crossword) = input.split_once("\n\n").unwrap();

    let words: Vec<String> = words
        .lines()
        .enumerate()
        .map(|(idx, word)| {
            let idx = idx + 1;
            match (idx % 3 == 0, idx % 5 == 0) {
                (true, true) => latin(&latin(word)),
                (true, false) | (false, true) => latin(word),
                _ => word.to_string(),
            }
        })
        .collect();

    let solution = crossword::solve(crossword, &words);

    println!("Soltion: {}", solution);
}

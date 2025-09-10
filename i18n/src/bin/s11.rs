use std::collections::HashMap;

use i18n::util::file::input;
use unicode_normalization::UnicodeNormalization;

fn rot(msg: &str, mv: i32) -> String {
    let greek : Vec<char> = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ".chars().collect();
    let l_to_i: HashMap<_, _> = greek.iter().cloned().enumerate().map(|(i, c)| (c, i)).collect();

    msg.nfc()
        .flat_map(char::to_uppercase)
        .map(|c| {
            l_to_i.get(&c)
            .map_or(c, |&i| greek[(i + mv as usize) % greek.len()])
        })
        .collect::<String>()
        .to_lowercase()
}

fn main() {
    let input = input(11);
    let lines = input.lines().collect::<Vec<&str>>();

    let odysseus = vec!["Οδυσσευς", "Οδυσσεως", "Οδυσσει", "Οδυσσεα", "Οδυσσευ"]
        .into_iter()
        .map(|o| o.to_lowercase().nfc().collect::<String>())
        .collect::<Vec<String>>();

    let count = lines
        .iter()
        .flat_map(|l| {
            (1..=24).filter_map(|i| {
                let r = rot(l, i);
                odysseus.iter().any(|o| r.contains(o)).then_some(i)
            })
        })
        .sum::<i32>();

    print!("Lines mentioning Odysseus: {}", count);
}

use std::collections::HashMap;

use i18n::util::file::input;
use unicode_normalization::UnicodeNormalization;

fn rot(msg: &str, mv: i32) -> String {
    let chars = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ".chars();
    let l_to_i: HashMap<_, _> = chars.clone().enumerate().map(|(i, c)| (c, i)).collect();
    let i_to_l: HashMap<_, _> = chars.enumerate().collect();

    msg.nfc()
        .collect::<String>()
        .to_uppercase()
        .chars()
        .map(|c| {
            let i = l_to_i.get(&c);

            if i.is_none() {
                c
            } else {
                let i = i.unwrap();
                let j = (i + mv as usize) % 24;
                i_to_l[&j]
            }
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

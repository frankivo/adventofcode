use everybodycodes::util::file;
use std::collections::{BTreeSet, HashSet};

fn part1() -> i16 {
    let crates: HashSet<i16> = file::input(3, 1)
        .lines()
        .next()
        .unwrap()
        .split(',')
        .filter_map(|s| s.parse().ok())
        .collect();

    crates.iter().sum()
}

fn part2() -> i16 {
    let crates: BTreeSet<i16> = file::input(3, 2)
        .lines()
        .next()
        .unwrap()
        .split(',')
        .filter_map(|s| s.parse().ok())
        .collect();

    let crates : Vec<i16> = crates.into_iter().collect();
    crates[0..20].iter().sum()
}

fn main() {
    println!(
        "What is the largest possible set of crates that can be formed from a given list? {}",
        part1()
    );
    println!("Pack the mushroom into exactly 20 crates. What is the smallest possible set of the crates that can be used for this purpose? {}", part2());
}

use everybodycodes::util::file;
use std::collections::BTreeSet;


fn solve(part: i8) -> i16 {
    let crates: BTreeSet<i16> = file::input(3, part)
        .lines()
        .next()
        .unwrap()
        .split(',')
        .filter_map(|s| s.parse().ok())
        .collect();

    let crates: Vec<i16> = crates.into_iter().collect();

    if (part == 1) {
        crates.iter().sum()
    } else {
        crates[0..20].iter().sum()
    }
}

fn main() {
    let part1 = solve(1);
    let part2 = solve(2);

    println!("Largest possible set of crates: {}", part1);
    println!("Smallest possible set of crates: {}", part2);
}

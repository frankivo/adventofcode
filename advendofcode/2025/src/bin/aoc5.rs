use aoc::util::file;
use std::collections::HashSet;

fn main() {
    let binding = file::input(5, 1);
    let (ranges, ingredients) = binding.split_once("\r\n\r\n").unwrap();

    let fresh: HashSet<(u64, u64)> = ranges
        .lines()
        .into_iter()
        .map(|r| {
            let (start, end) = r.split_once("-").unwrap();
            (start.parse().unwrap(), end.parse().unwrap())
        })
        .collect();

    let ingredients: HashSet<_> = ingredients
        .lines()
        .map(|i| i.parse::<u64>().unwrap())
        .collect();

    let count: HashSet<_> = ingredients
        .into_iter()
        .filter(|i| fresh.iter().any(|f| i >= &f.0 && i <= &f.1))
        .collect();
    dbg!(count.len());
}

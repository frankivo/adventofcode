use aoc::util::file;
use regex::Regex;

fn part1() -> u64 {
    let binding = file::input(6, 1);

    let lines: Vec<_> = binding.lines().collect();
    let numbers = &lines[0..lines.len() - 1];
    dbg!(numbers);

    let r_num = Regex::new(r"[\d]+").unwrap();

    let numbers: Vec<Vec<i16>> = numbers
        .iter()
        .map(|line| {
            r_num
                .find_iter(line)
                .map(|r| r.as_str().parse().unwrap())
                .collect()
        })
        .collect();
    dbg!(numbers);

    dbg!(lines.last());
    0
}

fn main() {
    dbg!(part1());
}

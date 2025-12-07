use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u64 {
    let binding = file::input(7, 1);

    let mut beams: HashSet<usize> = HashSet::new();

    for line in binding.lines() {
        dbg!(line);

        if let Some(s) = line.chars().position(|c| c == 'S') {
            beams.insert(s);
        }
    }

    dbg!(beams);
    0
}

fn part2() -> u64 {
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

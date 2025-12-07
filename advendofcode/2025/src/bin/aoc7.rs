use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u64 {
    let binding = file::input(7, 1);

    let mut count = 0;

    for line in binding.lines() {
        dbg!(line);

        if let Some(start) = line.chars().position(|c| c == 'S') {
            count += 1;
        } else {
            let mut beams: HashSet<usize> = HashSet::new();
            let splitters: Vec<usize> = line
                .char_indices()
                .filter_map(|(i, c)| (c == '^').then_some(i))
                .collect();
            for s in splitters {
                beams.insert(s - 1);
                beams.insert(s + 1);
            }
            dbg!(&beams.iter().count());
            count += beams.iter().count();
        }
    }

    dbg!(count);
    0
}

fn part2() -> u64 {
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

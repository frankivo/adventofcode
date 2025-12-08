use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u64 {
    let binding = file::input(7, 1);
    let mut count = 0;
    let mut beams_active: HashSet<usize> = HashSet::new();

    let lines = binding
        .lines()
        .enumerate()
        .filter_map(|(i, l)| (i % 2 == 0).then_some(l));

    for line in lines {
        if let Some(start) = line.chars().position(|c| c == 'S') {
            beams_active = HashSet::from([start]);
            continue;
        }

        let splitters: Vec<_> = line
            .char_indices()
            .filter_map(|(i, c)| (c == '^').then_some(i))
            .collect();

        for s in &splitters {
            if beams_active.contains(&s) {
                if beams_active.contains(&s) {
                    count += 1;
                }

                beams_active.insert(s - 1);
                beams_active.insert(s + 1);
                beams_active.remove(&s);
            }
        }
    }

    count
}

fn part2() -> u64 {
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

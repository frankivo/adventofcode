use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u16 {
    let binding = file::input(7, 1);

    let start = binding
        .lines()
        .next()
        .unwrap()
        .chars()
        .position(|c| c == 'S')
        .unwrap();
    dbg!(start);

    let lines = binding
        .lines()
        .enumerate()
        .filter_map(|(i, l)| (i % 2 == 0).then_some(l));

    let count = lines.fold((HashSet::from([start]), 0), |(beams, count), line| {
        let splitters: Vec<_> = line
            .char_indices()
            .filter_map(|(i, c)| (c == '^').then_some(i))
            .collect();

        let mut beams_active = beams.clone();
        let mut tmp = 0;

        for s in &splitters {
            if beams_active.contains(&s) {
                tmp += 1;
                beams_active.insert(s - 1);
                beams_active.insert(s + 1);
                beams_active.remove(&s);
            }
        }
        dbg!(count);
        (beams_active, count + tmp)
    });
    count.1
}

fn part2() -> u64 {
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

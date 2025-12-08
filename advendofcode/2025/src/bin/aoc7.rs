use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u16 {
    let binding = file::input(7, 1);

    let start = HashSet::from([binding
        .lines()
        .next()
        .unwrap()
        .chars()
        .position(|c| c == 'S')
        .unwrap()]);

    let lines = binding
        .lines()
        .enumerate()
        .filter_map(|(i, l)| (i % 2 == 0).then_some(l));

    lines
        .fold((start, 0), |(beams, split_count), line| {
            let splitters: Vec<_> = line
                .char_indices()
                .filter_map(|(i, c)| (c == '^').then_some(i))
                .filter(|i| beams.contains(&i))
                .collect();
            let add: HashSet<_> = splitters.iter().flat_map(|&i| [i - 1, i + 1]).collect();
            let beams: HashSet<_> = beams
                .iter()
                .filter(|b| !splitters.contains(b))
                .chain(add.iter())
                .copied()
                .collect();
            (beams, split_count + splitters.len() as u16)
        })
        .1
}

fn part2() -> u64 {
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

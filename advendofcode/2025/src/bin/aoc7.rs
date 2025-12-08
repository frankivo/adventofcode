use aoc::util::file;
use std::collections::HashSet;

fn part1(lines: &Vec<&str>, start: usize) -> u16 {
    let start = HashSet::from([start]);
    lines
        .iter()
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

fn part2(lines: &Vec<&str>, start: usize) -> u16 {
    0
}

fn main() {
    let binding = file::input(7, 1);

    let lines: Vec<&str> = binding
        .lines()
        .enumerate()
        .filter_map(|(i, l)| (i % 2 == 0).then_some(l))
        .collect();

    let start = binding
        .lines()
        .next()
        .unwrap()
        .chars()
        .position(|c| c == 'S')
        .unwrap();

    dbg!(part1(&lines, start));
    dbg!(part2(&lines, start));
}

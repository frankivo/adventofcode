use aoc::util::file;
use std::collections::HashSet;

fn part1() -> u64 {
    let binding = file::input(7, 1);

    let mut count = 0;
    let mut beams: HashSet<usize> = HashSet::new();

    for line in binding.lines() {
        dbg!(line);

        if let Some(start) = line.chars().position(|c| c == 'S') {
            count += 1;
            beams.insert(start);
        } else {
            let mut new_beams: HashSet<usize> = HashSet::new();
            let splitters: Vec<usize> = line
                .char_indices()
                .filter_map(|(i, c)| (c == '^').then_some(i))
                .collect();
            for s in splitters {
                let (a,b) = (s-1, s+1);
                if !beams.contains(&a) {
                    new_beams.insert(a);
                }
                if !beams.contains(&b) {
                    new_beams.insert(b);
                }
            }
            count += new_beams.iter().count();
            beams = new_beams;
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

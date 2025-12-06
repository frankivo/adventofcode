use aoc::util::file;
use regex::Regex;

fn part1() -> u64 {
    let binding = file::input(6, 1);

    let lines: Vec<_> = binding.lines().collect();
    let numbers = &lines[0..lines.len() - 1];
    let ops = lines.last().unwrap();

    let r_num = Regex::new(r"[\d]+").unwrap();
    let r_char = Regex::new(r"[\S]").unwrap();

    let numbers: Vec<Vec<i16>> = numbers
        .iter()
        .map(|line| {
            r_num
                .find_iter(line)
                .map(|r| r.as_str().parse().unwrap())
                .collect()
        })
        .collect();

    let ops : Vec<char> = r_char.find_iter(ops).map(|op| op.as_str().chars().next().unwrap()).collect();


    0
}

fn main() {
    dbg!(part1());
}

use aoc::util::file;

fn joltage(bank: &str) -> u32 {
    let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
    let &bat1 = batteries[..batteries.len() - 1].iter().max().unwrap();
    let pos = batteries.iter().position(|i| *i == bat1).unwrap();
    let &bat2 = batteries[pos + 1..].iter().max().unwrap();
    bat1 * 10 + bat2
}

fn part1() -> u32 {
    file::input(3, 1).lines().map(joltage).sum()
}

fn main() {
    dbg!(part1());
}

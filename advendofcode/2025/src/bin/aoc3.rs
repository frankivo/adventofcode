use aoc::util::file;

fn joltage(bank: &str) -> i16 {
    let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
    let bat1 = batteries[..batteries.len() - 1].iter().max().unwrap();
    let pos = batteries.iter().position(|i| i == bat1).unwrap();
    let bat2 = batteries[pos + 1..].iter().max().unwrap();
    let jolt = bat1.to_string() + &bat2.to_string();
    jolt.parse::<i16>().unwrap()
}

fn part1() -> i16 {
    file::input(3, 1).lines().map(joltage).sum()
}

fn main() {
    dbg!(part1());
}

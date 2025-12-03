use aoc::util::file;

fn part1() -> u32 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            let &bat1 = batteries[..batteries.len() - 1].iter().max().unwrap();
            let pos = batteries.iter().position(|i| *i == bat1).unwrap();
            let &bat2 = batteries[pos + 1..].iter().max().unwrap();
            bat1 * 10 + bat2
        })
        .sum()
}

fn part2() -> u32 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            for i in 0..12 {
                dbg!(i);
            }
            1
        })
        .sum()
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

use aoc::util::file;

fn find_max(sub: &[u32]) -> (u32, usize) {
    let &max = sub.iter().max().unwrap();
    let pos = sub.iter().position(|i| *i == max).unwrap();
    (max, pos)
}

fn part1() -> u32 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            let (bat1, pos) = find_max(&batteries[..batteries.len() - 1]);
            let (bat2, _) = find_max(&batteries[pos + 1..]);
            bat1 * 10 + bat2
        })
        .sum()
}

fn part2() -> u64 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            (0..12)
                .rev()
                .fold((0usize, 0u64), |(left, joltage), i| {
                    let right = batteries.len() - i;
                    let (bat, pos) = find_max(&batteries[left..right]);
                    (left + pos + 1, (joltage * 10 + bat as u64))
                })
                .1
        })
        .sum()
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

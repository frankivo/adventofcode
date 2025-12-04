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
            let bat1 = find_max(&batteries[..batteries.len() -1]);
            let bat2 = find_max(&batteries[bat1.1 + 1..]);
            bat1.0 * 10 + bat2.0
        })
        .sum()
}

fn part2() -> u32 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            // let batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            // for i in 0..12 {
            //     dbg!(1);
            // }
            1
        })
        .sum()
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

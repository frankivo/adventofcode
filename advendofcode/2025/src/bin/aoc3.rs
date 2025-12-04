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
            let mut batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            let mut jolt = String::with_capacity(12);

            for i in (0..12).rev() {
                let right = batteries.len() - i;
                let (bat, pos) = find_max(&batteries[..right]);
                jolt.push(char::from_digit(bat, 10).unwrap());
                batteries = batteries[pos + 1..].to_vec();
            }
            jolt.parse::<u64>().unwrap()
        })
        .sum()
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

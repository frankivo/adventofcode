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
            let bat1 = find_max(&batteries[..batteries.len() - 1]);
            let bat2 = find_max(&batteries[bat1.1 + 1..]);
            bat1.0 * 10 + bat2.0
        })
        .sum()
}

fn part2() -> u64 {
    file::input(3, 1)
        .lines()
        .map(|bank| {
            let mut batteries: Vec<u32> = bank.chars().map(|c| c.to_digit(10).unwrap()).collect();
            let mut jolt= String::new();

            for i in (1..=12).rev() {
                // let right: usize = batteries.len() - (batteries.len() - 12);
                let bat = find_max(&batteries);
                jolt += &(bat.0).to_string();
                batteries = batteries[bat.1 + 1..].to_vec();
                dbg!(&jolt, &batteries);
            }
            dbg!(&jolt);
            jolt.parse::<u64>().unwrap()
        })
        .sum()
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

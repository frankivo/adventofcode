use everybodycodes::util::file;

fn part1() -> i16 {
    let multiplier: f32 = file::input(4, 1)
        .lines()
        .filter_map(|s| s.parse().ok())
        .collect::<Vec<f32>>()
        .windows(2)
        .map(|w| w[0] / w[1])
        .product();

    (multiplier * 2025.0) as i16
}

fn part2() -> i128 {
    let multiplier : f64 = file::input(4, 2)
        .lines()
        .filter_map(|s| s.parse().ok())
        .rev()
        .collect::<Vec<f64>>()
        .windows(2)
        .map(|w| w[0] / w[1])
        .product();
    (multiplier * 10_000_000_000_000.0) as i128
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

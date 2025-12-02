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

fn part2() -> i64 {
    let gears: Vec<f64> = file::input(4, 2)
        .lines()
        .filter_map(|s| s.parse().ok())
        .rev()
        .collect();
    (10000000000000.0 * gears[0] / gears[gears.len() - 1]).ceil() as i64
}

fn part3() -> i16 {
    let data = file::input(4, 3);
    let lines: Vec<_> = data.lines().collect();

    let (first, last) = (lines[0], lines[lines.len() - 1]);
    dbg!(first, last);

    for l in lines {
        dbg!(l);
    }
    0
}

fn main() {
    dbg!(part1());
    dbg!(part2());
    dbg!(part3());
}

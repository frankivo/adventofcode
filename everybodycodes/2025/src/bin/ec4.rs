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

fn part3() -> i64 {
    let data = file::input(4, 3);
    let gears: Vec<f64> = data
        .lines()
        .map(|l| {
            match l.split_once("|") {
                Some(v) => {
                    let (x, y) = v;
                    y.parse::<f64>().unwrap() / x.parse::<f64>().unwrap()
                }
                None => l.parse::<f64>().unwrap(),
            }
        })
        .collect();

    let inner_ratio: f64 = gears[1..gears.len() - 1].into_iter().product();
    let outer_ratio = *gears.first().unwrap() / *gears.last().unwrap();
    (100 as f64 * inner_ratio * outer_ratio) as i64
}

fn main() {
    dbg!(part1());
    dbg!(part2());
    dbg!(part3());
}

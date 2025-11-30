use everybodycodes::util::file;

fn main() {
    let gears: Vec<f32> = file::input(4, 1)
        .lines()
        .filter_map(|s| s.parse().ok())
        .collect();

    let mut mul = 1.0;

    for (i, _) in gears.iter().enumerate() {
        dbg!(mul);
        if i > 0 {
            mul = (gears[i-1] / gears[i]) * mul;

        }
    }
    dbg!((mul * 2025.0) as i16);

}

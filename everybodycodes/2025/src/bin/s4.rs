use everybodycodes::util::file;

fn main() {
    let gears: Vec<f32> = file::input(4, 1)
        .lines()
        .filter_map(|s| s.parse().ok())
        .collect();

    let last_mul = gears.iter().enumerate().fold(1.0, |mul, (i, _)| {
        if i > 0 {
            (gears[i - 1] / gears[i]) * mul
        } else {
            mul
        }
    });
    dbg!((last_mul * 2025.0) as i16);
}

use everybodycodes::util::file;

fn main() {
    let last_mul: f32 = file::input(4, 1)
        .lines()
        .filter_map(|s| s.parse().ok())
        .collect::<Vec<f32>>()
        .windows(2)
        .map(|w| w[0] / w[1])
        .product();

    dbg!((last_mul * 2025.0) as i16);
}

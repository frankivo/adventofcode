use everybodycodes::util::file;

fn col2bin(col: &str) -> i8 {
    let fixed: String = col
        .chars()
        .map(|c| match c {
            'G' | 'R' | 'B' => '1',
            _ => '0',
        })
        .collect();
    i8::from_str_radix(&fixed, 2).unwrap()
}

fn main() {
    let lines = file::input(1, 1);

    let mut sum: u16 = 0;

    for line in lines.lines() {
        let id: u16 = line[..4].parse().unwrap();
        let red = col2bin(&line[5..11]);
        let green = col2bin(&line[12..18]);
        let blue = col2bin(&line[19..]);

        if green > red && green > blue {
            sum += id;
        }
    }
    dbg!(sum);
}

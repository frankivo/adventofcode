use everybodycodes::util::file;

fn col2bin(col: &str) -> u16 {
    let fixed: String = col
        .chars()
        .map(|c| match c {
            'G' | 'R' | 'B' | 'S' => '1',
            _ => '0',
        })
        .collect();
    u16::from_str_radix(&fixed, 2).unwrap()
}

fn parse_line(line: &str) -> (u32, u16, u16, u16, u16) {
    let (id, colours) = line.split_once(":").unwrap();
    let red = col2bin(&colours.get(0..6).unwrap());
    let green = col2bin(&colours.get(7..13).unwrap());
    let blue = col2bin(&colours.get(14..20).unwrap());
    let shine = col2bin(&colours.get(21..).unwrap_or("0"));
    (id.parse().unwrap(), red, green, blue, shine)
}

fn part1() -> u32 {
    file::input(1, 1)
        .lines()
        .map(parse_line)
        .flat_map(|(id, r, g, b, _)| (g > r && g > b).then_some(id))
        .sum()
}

fn part2() -> u32 {
    file::input(1, 2)
        .lines()
        .map(parse_line)
        .map(|(id, r, g, b, s)| (id, r + g + b, s))
        .max_by(|a, b| a.2.cmp(&b.2).then(b.1.cmp(&a.1)))
        .unwrap()
        .0
}

fn main() {
    println!(
        "What is the sum of the identifiers of the scales where green is the dominant colour? {}",
        part1()
    );

    println!(
        "What is the identifier of the darkest scale among the most shiny ones? {}",
        part2()
    )
}

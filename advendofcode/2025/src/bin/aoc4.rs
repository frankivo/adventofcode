use aoc::util::file;

fn build_map(raw: &str) -> Vec<(i16, i16)> {
    raw.lines()
        .enumerate()
        .map(|(y, line)| {
            line.chars()
                .into_iter()
                .enumerate()
                .filter_map(|(x, c)| match c {
                    '@' => Some((y as i16, x as i16)),
                    _ => None,
                })
                .collect::<Vec<(i16, i16)>>()
        })
        .collect::<Vec<_>>()
        .iter()
        .flat_map(|inner_vec| inner_vec.iter())
        .cloned()
        .collect()
}

fn adjacent(map: &Vec<(i16, i16)>, point: (i16, i16)) -> Vec<(i16, i16)> {
    let mut adj: Vec<(i16, i16)> = Vec::new();
    let (y, x) = point;
    for yy in -1..=1 {
        for xx in -1..=1 {
            if yy != 0 || xx != 0 {
                let y = y + yy;
                let x = x + xx;

                if map.contains(&(y, x)) {
                    adj.push((y, x));
                }
            }
        }
    }
    adj
}

fn part1() -> i16 {
    let binding = file::input(4, 1);
    let map: Vec<(i16, i16)> = build_map(&binding);
    let size: i16 = binding.lines().count() as i16;

    (0..size).fold(0, |sum, y| {
        sum + (0..size).fold(0, |sum, x| {
            sum + (map.contains(&(y, x)) && adjacent(&map, (y, x)).len() < 4) as i16
        })
    })
}

fn main() {
    dbg!(part1());
}

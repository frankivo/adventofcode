use aoc::util::file;

fn part1() -> i16 {
    let binding = file::input(4, 1);
    let y: Vec<_> = binding
        .lines()
        .enumerate()
        .map(|(y, line)| {
            line.chars()
                .into_iter()
                .enumerate()
                .filter_map(|(x, c)| match c {
                    '@' => Some((y, x)),
                    _ => None,
                })
                .collect::<Vec<(usize, usize)>>()
        })
        .collect();
    let flattened: Vec<(usize,usize)> = y
        .iter()
        .flat_map(|inner_vec| inner_vec.iter())
        .cloned()
        .collect();

    dbg!(flattened.len());
    0
}

fn main() {
    dbg!(part1());
}

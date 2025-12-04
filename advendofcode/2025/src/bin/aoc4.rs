use aoc::util::file;

fn part1() -> i16 {
    let binding = file::input(4,1);
    let first = binding.lines().next().unwrap();
    let x :Vec<_>= first.chars().into_iter().enumerate().filter_map(|(i,c)| {
        match c {
            '@' => Some(i),
            _ => None
        }
    } ).collect();
    dbg!(first, x);
    0
}

fn main() {
    dbg!(part1());
}

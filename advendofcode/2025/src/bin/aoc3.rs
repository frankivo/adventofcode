use aoc::util::file;

fn find(battery: &str) -> i16 {

}

fn part1() -> i16 {
    let binding = file::input(3, 1);
    let first: Vec<u32> = binding
        .lines()
        .collect::<Vec<_>>()
        .first()
        .unwrap()
        .chars()
        .map(|c| c.to_digit(10).unwrap())
        .collect();
    dbg!(&first);
    let max = first.iter().max().unwrap();
    let pos = first.iter().position(|i| i == max).unwrap();
    let max2 = first[pos+1..].iter().max().unwrap();
    dbg!(max, max2);
    0
}

fn main() {
    dbg!(part1());
}

use aoc::util::file;

fn split_range(range: &str) -> (i64, i64) {
    let (start, end) = range.split_once("-").unwrap();
    (start.parse::<i64>().unwrap(), end.parse::<i64>().unwrap())
}

fn valid(id: i64) -> bool {
    let str = id.to_string();
    let mid = str.len() / 2;
    &str[0..mid] != &str[mid..]
}

fn part1() -> i64 {
    let binding = file::input(2, 1).replace("\r\n", "");
    let pairs: Vec<_> = binding.split(",").map(split_range).collect();
    pairs.into_iter().fold(0, |sum, pair| {
        sum + (pair.0..=pair.1).fold(0, |sum, i| if valid(i) { sum } else { sum + i })
    })
}

fn main() {
    dbg!(part1());
}

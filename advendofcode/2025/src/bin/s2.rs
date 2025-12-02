use aoc::util::file;

fn split_range(range: &str) -> (i64, i64) {
    let (start, end) = range.split_once("-").unwrap();
    (start.parse::<i64>().unwrap(), end.parse::<i64>().unwrap())
}

fn valid(part: i8, id: i64) -> bool {
    if part == 1 {
        valid1(id)
    } else {
        valid2(id)
    }
}

fn valid1(id: i64) -> bool {
    let str = id.to_string();
    let mid = str.len() / 2;
    &str[0..mid] != &str[mid..]
}

fn valid2(id: i64) -> bool {
    let str = id.to_string();
    let mid = str.len() / 2;

    for i in 0..mid {
        let sub = &str[0..=i];
        let v: Vec<_> = str.match_indices(sub).collect();
        if v.len() * sub.len() == str.len() {
            return false;
        };
    }

    true
}

fn solve(part: i8) -> i64 {
    let binding = file::input(2, 1).replace("\r\n", "");
    let pairs: Vec<_> = binding.split(",").map(split_range).collect();
    pairs.into_iter().fold(0, |sum, pair| {
        sum + (pair.0..=pair.1).fold(0, |sum, i| if valid(part, i) { sum } else { sum + i })
    })
}

fn main() {
    dbg!(solve(1));
    dbg!(solve(2));
}

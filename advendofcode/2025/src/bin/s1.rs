use aoc::util::file;

fn instructions() -> Vec<i16> {
    let data = file::input(1, 1);
    data.lines()
        .map(|line| {
            let num: i16 = line[1..].parse().unwrap();
            if line.starts_with('L') { -num } else { num }
        })
        .collect()
}

fn solve(part: i8) -> i16 {
    let (_, zeroes) = instructions().iter().fold((50, 0), |(pos, zeroes), instr| {
        let next = pos + instr;
        let clamped = ((next % 100) + 100) % 100;

        let wraps = if part == 1 {
            (clamped == 0) as i16
        } else {
            next.div_euclid(100).abs()
        };
        (clamped, zeroes + wraps)
    });

    zeroes
}

fn main() {
    dbg!(solve(1));
    dbg!(solve(2));
}

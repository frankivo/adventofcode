use aoc::util::file;

fn part1() -> i16 {
    let data = file::input(1, 1);
    let instructions: Vec<i16> = data
        .lines()
        .map(|line| {
            let left = line.starts_with('L');
            let num: i16 = line[1..].parse().unwrap();

            if left {
                -1 * num
            } else {
                num
            }
        })
        .collect();

    let (_, zeroes) = instructions.iter().fold((50, 0), |(pos, zeroes), instr| {
        let next = pos + instr;
        let clamped = ((next % 100) + 100) % 100;
        (clamped, if clamped == 0 { zeroes + 1 } else { zeroes })
    });

    zeroes
}

fn main() {
    dbg!(part1());
}

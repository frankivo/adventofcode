use aoc::util::file;

fn instructions() -> Vec<i16> {
    let data = file::input(1, 1);
    data.lines()
        .map(|line| {
            let left = line.starts_with('L');
            let num: i16 = line[1..].parse().unwrap();

            if left {
                -num
            } else {
                num
            }
        })
        .collect()
}

fn part1() -> i16 {
    let (_, zeroes) = instructions().iter().fold((50, 0), |(pos, zeroes), instr| {
        let next = pos + instr;
        let clamped = ((next % 100) + 100) % 100;
        (clamped, if clamped == 0 { zeroes + 1 } else { zeroes })
    });

    zeroes
}

fn part2() -> i16 {
    let (_, zeroes) = instructions().iter().fold((50, 0), |(pos, zeroes), instr| {
        let next = pos + instr;
        let clamped = ((next % 100) + 100) % 100;

        let tmp = pos > 0 && (next <= 0 || next >= 99);
        (clamped, if tmp { zeroes + 1 } else { zeroes })
    });

    zeroes
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

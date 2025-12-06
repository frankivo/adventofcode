use aoc::util::file;

fn part1() -> u64 {
    let binding = file::input(6, 1);

    let lines: Vec<_> = binding.lines().collect();
    let numbers = &lines[0..lines.len() - 1];
    let ops = lines.last().unwrap();

    let numbers: Vec<Vec<u64>> = numbers
        .iter()
        .map(|line| {
            line.split_whitespace()
                .map(|n| n.parse().unwrap())
                .collect()
        })
        .collect();

    let ops: Vec<char> = ops
        .split_whitespace()
        .map(|op| op.chars().next().unwrap())
        .collect();

    let mut sums: Vec<u64> = numbers.first().unwrap().to_vec();
    for num in &numbers[1..] {
        for (i, n) in num.iter().enumerate() {
            match ops[i] {
                '+' => sums[i] = sums[i] + n,
                '*' => sums[i] = sums[i] * n,
                _ => sums[i] = 0,
            }
        }
    }
    sums.iter().sum()
}

fn main() {
    dbg!(part1());
}

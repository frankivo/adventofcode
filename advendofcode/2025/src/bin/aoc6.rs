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

fn part2() -> u64 {
    let binding = file::input(6, 1);
    let lines: Vec<Vec<char>> = binding.lines().map(|l| l.chars().collect()).rev().collect();

    let mut sum = 0u64;
    let mut op = ' ';
    let mut op_sum = 0u64;

    let len = lines.iter().map(|l| l.len()).max().unwrap();
    for i in 0..len {
        if let Some(&c) = lines[0].get(i) {
            if c != ' ' {
                op = c;
            }
        }

        let mut num_str = String::with_capacity(len);
        for l in &lines[1..] {
            if let Some(&c) = l.get(i) {
                if c != ' ' {
                    num_str.push(c);
                }
            }
        }
        num_str = num_str.chars().rev().collect();

        match num_str.parse::<u64>() {
            Ok(num) => match op {
                '+' => op_sum = op_sum + num,
                '*' => {
                    if op_sum == 0 {
                        op_sum = op_sum + num;
                    } else {
                        op_sum = op_sum * num;
                    }
                }
                _ => {},
            },
            Err(_) => {
                sum = sum + op_sum;
                op_sum = 0;
            }
        }
    }

    sum + op_sum
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

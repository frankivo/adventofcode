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
    let lines: Vec<_> = binding
        .lines()
        .collect::<Vec<_>>()
        .into_iter()
        .rev()
        .collect();

    let mut sum = 0u64;
    let mut op = 'a';
    let mut op_sum = 0u64;

    let len = lines.iter().map(|l| l.len()).max().unwrap();
    for i in 0..len {
        let fl = lines.first().unwrap();
        let c = fl.chars().nth(i).unwrap_or(' ');
        if c != ' ' {
            op = c;
        }

        let num: Vec<char> = lines[1..]
            .iter()
            .filter_map(|l| {
                let c = l.chars().nth(i).unwrap_or(' ');
                (c != ' ').then_some(c)
            })
            .rev()
            .collect();
        let num: String = num.into_iter().collect();
        let num = num.parse::<u64>();

        match num {
            Ok(num) => match op {
                '+' => op_sum = op_sum + num,
                '*' => {
                    if op_sum == 0 {
                        op_sum = op_sum + num;
                    }
                    else {
                        op_sum = op_sum* num;
                    }
                },
                _ => op_sum = op_sum,
            },
            Err(_) => {
                sum = sum + op_sum;
                op_sum = 0;
            },
        }
    }

    sum + op_sum
}

fn main() {
    dbg!(part1());
    dbg!(part2());
}

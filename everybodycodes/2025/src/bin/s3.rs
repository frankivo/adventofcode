use everybodycodes::util::file;
use std::collections::BTreeSet;

fn solve(part: i8) -> i16 {
    let crates: BTreeSet<i16> = file::input(3, part)
        .lines()
        .next()
        .unwrap()
        .split(',')
        .filter_map(|s| s.parse().ok())
        .collect();

    let crates: Vec<i16> = crates.into_iter().collect();

    if part == 1 {
        crates.iter().sum()
    } else {
        crates[0..20].iter().sum()
    }
}

fn part3() -> i16 {
    let mut crates: Vec<i16> = file::input(3, 3)
        .lines()
        .next()
        .unwrap()
        .split(',')
        .filter_map(|s| s.parse().ok())
        .collect();
    crates.sort();
    crates.reverse(); // Wrong I think

    let mut sets = 0;
    let mut cur =usize::MAX ;

    while crates.len() > 0 {
        sets += 1;
        for (i,c) in crates.clone().into_iter().enumerate() {
            dbg!( i, c);
            // if c < cur {
            //     cur = c;
                crates.remove(i as usize) ;
            // }
        }
        break;
    }

    // dbg!(crates);
    sets
}

fn main() {
    let part1 = solve(1);
    let part2 = solve(2);

    println!("Largest possible set of crates: {}", part1);
    println!("Smallest possible set of crates: {}", part2);
    println!("The given list can be packed into {} sets", part3());
}

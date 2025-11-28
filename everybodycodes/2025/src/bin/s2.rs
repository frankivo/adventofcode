use everybodycodes::util::file;
use std::fmt::{Display, Formatter, Result};
use std::ops::{Add, Div, Mul};

#[derive(Copy, Clone, Debug)]
struct Complex(i64, i64);

impl Display for Complex {
    fn fmt(&self, f: &mut Formatter<'_>) -> Result {
        write!(f, "[{},{}]", self.0, self.1)
    }
}

impl Add for Complex {
    type Output = Complex;
    // [X1,Y1] * [X2,Y2] = [X1 * X2 - Y1 * Y2, X1 * Y2 + Y1 * X2]
    fn add(self, other: Self) -> Self::Output {
        Complex(self.0 + other.0, self.1 + other.1)
    }
}

impl Div for Complex {
    type Output = Complex;
    // [X1,Y1] / [X2,Y2] = [X1 / X2, Y1 / Y2]
    fn div(self, other: Self) -> Self::Output {
        Complex(self.0 / other.0, self.1 / other.1)
    }
}

impl Mul for Complex {
    type Output = Complex;
    // [X1,Y1] * [X2,Y2] = [X1 * X2 - Y1 * Y2, X1 * Y2 + Y1 * X2]
    fn mul(self, other: Self) -> Self::Output {
        Complex(
            self.0 * other.0 - self.1 * other.1,
            self.0 * other.1 + self.1 * other.0,
        )
    }
}

fn cycle(a: Complex, r: Complex) -> Complex {
    let r = r * r;
    let r = r / Complex(10, 10);
    let r = r + a;
    r
}

fn get_a(part: i8) -> Complex {
    let data = file::input(2, part).lines().collect::<Vec<_>>()[0].to_string();
    let numbers_str = data.trim_start_matches("A=[").trim_end_matches(']');
    let nums: Vec<i64> = numbers_str
        .split(',')
        .filter_map(|s| s.parse::<i64>().ok())
        .collect();
    Complex(nums[0], nums[1])
}

fn part1() -> Complex {
    let a = get_a(1);
    let r = cycle(a, Complex(0, 0));
    let r = cycle(a, r);
    cycle(a, r)
}

fn engrave(point: Complex) -> bool {
    let mut check = Complex(0, 0);
    for _ in 0..100 {
        check = check * check;
        check = check / Complex(100_000, 100_000);
        check = check + point;
        if check.0.abs() > 1_000_000 || check.1.abs() > 1_000_000 {
            return false;
        }
        // dbg!(check);
    }
    true
}

fn part2() {
    let a = get_a(2);
    let mut sum = 0;
    for x in 0..=100 {
        for y in 0..=100 {
            let g = a + Complex(x * 10, y * 10);
            let e = engrave(g);
            if e {
                sum += 1;
            }
        }
    }

    dbg!(sum);

    // let x = Complex(35630, -64880);
    // dbg!(engrave(x));
}

fn main() {
    println!("Part 1: {}", part1());
    part2();
}

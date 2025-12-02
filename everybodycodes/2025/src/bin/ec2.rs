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

fn engrave_point(point: Complex) -> bool {
    (0..100)
        .try_fold(Complex(0, 0), |current, _| {
            let next = current * current / Complex(100_000, 100_000) + point;
            (next.0.abs() <= 1_000_000 && next.1.abs() <= 1_000_000).then_some(next)
        })
        .is_some()
}

fn engrave(part: i8) -> i64 {
    let a = get_a(part);
    let (steps, precision) = if part == 2 { (100, 10) } else { (1000, 1) };

    (0..=steps).fold(0, |sum_x, x| {
        let sum_y = (0..=steps).fold(0, |sum_y, y| {
            let g = a + Complex(x * precision, y * precision);
            sum_y + engrave_point(g) as i64
        });
        sum_x + sum_y
    })
}

fn main() {
    println!("Part 1: {}", part1());
    println!("Part 2: {}", engrave(2));
    println!("Part 3: {}", engrave(3));
}

use everybodycodes::util::file;
use std::ops::{Add, Div, Mul};

#[derive(Debug, Clone)]
struct Complex(i32, i32);

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
    let r = r.clone() * r.clone();
    let r = r.clone() / Complex(10, 10);
    let r = r.clone() + a;
    r
}

fn solve(a: Complex) -> Complex {
    let r = Complex(0,0);
    let r = cycle(a.clone(), r.clone());
    let r = cycle(a.clone(), r.clone());
    cycle(a.clone(), r.clone())
}

fn main() {
    let a = Complex(143, 53);
    let r = solve(a);

    println!("[{},{}]", r.0, r.1);
}

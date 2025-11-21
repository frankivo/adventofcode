use everybodycodes::util::file;
use std::ops::Add;

#[derive(Debug, Clone)]
struct Complex(i32, i32);

impl Add for Complex {
    type Output = Complex;
    fn add(self, other: Self) -> Self::Output {
        Complex(self.0 + other.0, self.1 + other.1)
    }
}

fn cycle(a: Complex, r: Complex) -> Complex {
    let r = a.clone() + a.clone();
    r
}

fn main() {
    let r = Complex(0, 0);
    let a = Complex(25, 9);
    let r = cycle(a, r);
    dbg!(r);
}

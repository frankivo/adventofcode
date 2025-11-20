use std::{env, fs};

pub fn input(day: i32, part: i32) -> String {
    let demo = env::args().any(|a| a == "test");
    let input = if demo {
        format!("test-input/{}.txt", day)
    } else {
        format!("input/{}_{}.txt", day, part)
    };

    return fs::read_to_string(&input).expect(&format!("read failed! {}", &input));
}

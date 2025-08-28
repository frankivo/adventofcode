use chrono::{DateTime, Utc};
use std::env;
use std::fs;

fn main() {
    let demo = env::args().any(|a| a == "test");
    let input = if demo {
        "test-input/2.txt"
    } else {
        "input/2.txt"
    };
    let input = fs::read_to_string(input).expect("Read failed!");

    for line in input.lines() {
        let dt = DateTime::parse_from_rfc3339(line).unwrap();
        let utc = dt.with_timezone(&Utc);
        println!("{:?} -- {:?}", dt, utc);
    }

}

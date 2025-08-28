use chrono::{DateTime, Utc};
use itertools::Itertools;
use std::env;
use std::fs;

fn main() {
    let demo = env::args().any(|a| a == "test");
    let input = if demo { "test-input/2.txt" } else { "input/2.txt" };

    let data = fs::read_to_string(input)
        .expect("Read failed!");

    let converted = data
        .lines()
        .map(|line| DateTime::parse_from_rfc3339(line).unwrap())
        .map(|dt| dt.with_timezone(&Utc));

    let counts = converted.into_iter().counts();
    let max = counts.iter().max_by_key(|e| e.1).unwrap();

    println!("{}", max.0.to_rfc3339());
}

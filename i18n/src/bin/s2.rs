use chrono::{DateTime, Utc};
use i18n::util::file;
use itertools::Itertools;

fn main() {
    let input = file::input(2);

    let converted = input
        .lines()
        .map(|line| DateTime::parse_from_rfc3339(line).unwrap())
        .map(|dt| dt.with_timezone(&Utc));

    let counts = converted.into_iter().counts();
    let max = counts.iter().max_by_key(|e| e.1).unwrap();

    println!("{}", max.0.to_rfc3339());
}

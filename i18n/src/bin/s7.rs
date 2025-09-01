use chrono::{DateTime, Duration, Timelike};
use chrono_tz::America::{Halifax, Santiago};
use i18n::util::file::input;
use itertools::Itertools;

fn main() {
    let input = input(7);
    let rows = input.lines().filter_map(|l| {
        l.split("\t")
            .next_tuple::<(&str, &str, &str)>()
            .and_then(|(a, b, c)| {
                Some((
                    DateTime::parse_from_rfc3339(a).unwrap(),
                    b.parse::<i64>().unwrap(),
                    c.parse::<i64>().unwrap(),
                ))
            })
    });

    let hours = rows.map(|(dt, correct, wrong)| {
        let hal = dt.with_timezone(&Halifax).naive_local().and_utc();
        let utc = dt.naive_local().and_utc();
        let off = dt - Duration::minutes(wrong) + Duration::minutes(correct);
        return match utc == hal {
            true => off.with_timezone(&Halifax),
            _ => off.with_timezone(&Santiago)
        }.hour();
    });

    let sum : usize = hours.enumerate().map(|(ln, hours)| (ln + 1) * hours as usize).sum();
    println!("Sum of all hours: {}", sum);
}

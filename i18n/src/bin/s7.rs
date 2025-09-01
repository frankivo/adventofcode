use chrono_tz::America::{Halifax, Santiago};
use chrono::{DateTime, Duration, FixedOffset, Timelike};
use i18n::util::file::input;
use itertools::Itertools;

fn is_maybe_halifax(dt: DateTime<FixedOffset>) -> bool {
    let hal = dt.with_timezone(&Halifax).naive_local().and_utc();
    let utc = dt.naive_local().and_utc();
    hal == utc
}

fn main() {
    let input = input(7);
    let rows = input.lines().filter_map(|l| {
        l.split("\t")
            .next_tuple::<(&str, &str, &str)>()
            .and_then(|(a, b, c)| {
                Some((
                    DateTime::parse_from_rfc3339(a).ok()?,
                    b.parse::<i64>().ok()? - c.parse::<i64>().ok()?
                ))
            })
    });

    let hours = rows.map(|(dt, offset)| {
        let timezoned = match is_maybe_halifax(dt) {
            true => dt.with_timezone(&Halifax),
            _ => dt.with_timezone(&Santiago)
        };
        (timezoned + Duration::minutes(offset)).hour()
    });

    let sum : usize = hours.enumerate().map(|(ln, hours)| (ln + 1) * hours as usize).sum();
    println!("Sum of all hours: {}", sum);
}

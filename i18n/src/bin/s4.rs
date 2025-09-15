use chrono_tz::Tz;
use chrono::{DateTime, NaiveDateTime, TimeZone, Utc};
use i18n::util::file;
use itertools::Itertools;

fn parse_dt(dt_str: &str, tz: &str) -> DateTime<Utc> {
    let dt = NaiveDateTime::parse_from_str(dt_str, "%b %d, %Y, %H:%M").unwrap();
    let timezone: Tz = tz.parse().unwrap();
    timezone
        .from_local_datetime(&dt)
        .unwrap()
        .with_timezone(&Utc)
}

fn main() {
    let mins = file::input(4)
        .lines()
        .filter(|l| !l.is_empty())
        .map(|l| {
            let tz = l[11..40].trim();
            let dt = l[42..61].trim();
            return parse_dt(dt, tz);
        })
        .tuples::<(DateTime<Utc>, DateTime<Utc>)>()
        .map(|(dep, arr)| (arr - dep).num_minutes())
        .sum::<i64>();

    println!("Amount of minutes: {}", mins);
}

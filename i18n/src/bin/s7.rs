use chrono::{DateTime, Duration};
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

    for (ln, (dt, correct, wrong)) in rows.enumerate() {

        let hal = dt.with_timezone(&Halifax).naive_local().and_utc();
        let utc = dt.naive_local().and_utc();
        let off = dt - Duration::minutes(wrong) + Duration::minutes(correct) + Duration::milliseconds(0);
        let fixed = match utc == hal {
            true => off.with_timezone(&Halifax).to_rfc3339_opts(chrono::SecondsFormat::Millis, true),
            _ => off.with_timezone(&Santiago).to_rfc3339_opts(chrono::SecondsFormat::Millis, true)
        };

        // dbg!(ln + 1, dt, hal, san, dt == hal, fixed);
        // println!("");
        println!("{}", fixed);
    }

}

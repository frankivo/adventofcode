use chrono::DateTime;
use chrono::NaiveDateTime;
use chrono::TimeZone;
use chrono::Utc;
use chrono_tz::Tz;

fn parse_dt(dt_str: &str, tz: &str) -> DateTime<Utc> {
    let dt = NaiveDateTime::parse_from_str(dt_str, "%b %d, %Y, %H:%M").unwrap();
    let timezone: Tz = tz.parse().unwrap();
    timezone
        .from_local_datetime(&dt)
        .unwrap()
        .with_timezone(&Utc)
}

fn main() {
    let l = parse_dt("Mar 04, 2020, 10:00", "Europe/London");
    let p = parse_dt("Mar 04, 2020, 11:59", "Europe/Paris");
    dbg!(l);
    dbg!(p);

    let d = p -l;
    dbg!(d.num_minutes());
}

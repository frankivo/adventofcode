use chrono::DateTime;
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
                    b.parse::<i16>().unwrap(),
                    c.parse::<i16>().unwrap(),
                ))
            })
    });

    for l in rows {
        dbg!(l);
    }

}

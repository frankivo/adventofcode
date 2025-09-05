use chrono::NaiveDate;
use i18n::util::file;
use itertools::Itertools;
use std::collections::HashMap;

fn wrote_on_911(dates: Vec<&str>, fmts: Vec<&str>) -> bool {
    let nine11 = NaiveDate::parse_from_str("01-09-11", "%y-%m-%d").unwrap();
    fmts.iter()
        .find_map(|f| {
            if dates
                .iter()
                .all(|d| NaiveDate::parse_from_str(d, f).is_ok())
            {
                Some(
                    dates
                        .iter()
                        .map(|d| NaiveDate::parse_from_str(d, f).unwrap())
                        .any(|d| d == nine11),
                )
            } else {
                None
            }
        })
        .is_some_and(|b| b)
}

fn main() {
    let input = file::input(9);

    let mut pd = HashMap::<&str, Vec<&str>>::new();

    for line in input.lines() {
        let (date, names) = line.split_once(":").unwrap();
        names.trim().split(", ").for_each(|name| {
            pd.entry(name).or_insert(Vec::new()).insert(0, date);
        });
    }

    let fmts = vec!["%d-%m-%y", "%m-%d-%y", "%y-%m-%d", "%y-%d-%m"];

    let names = pd
        .iter()
        .filter(|(_, dates)| wrote_on_911(dates.to_vec(), fmts.to_vec()))
        .map(|f| f.0)
        .sorted();

    print!("{}", names.cloned().collect::<Vec<&str>>().join(" "));
}

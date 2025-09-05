use chrono::NaiveDate;
use i18n::util::file;
use itertools::Itertools;
use std::collections::HashMap;

fn wrote_on_911(dates: &[&str], fmts: &[&str]) -> bool {
    let nine11 = NaiveDate::parse_from_str("01-09-11", "%y-%m-%d").unwrap();

    fmts.iter().any(|f| {
        let parsed: Option<Vec<_>> = dates
            .iter()
            .map(|d| NaiveDate::parse_from_str(d, f).ok())
            .collect();
        match parsed {
            Some(ds) => ds.iter().any(|d| *d == nine11),
            None => false,
        }
    })
}

fn main() {
    let input = file::input(9);

    let mut pd = HashMap::<&str, Vec<&str>>::new();

    for line in input.lines() {
        if let Some((date, names)) = line.split_once(':') {
            for name in names.trim().split(", ") {
                pd.entry(name).or_default().push(date);
            }
        }
    }

    let fmts = vec!["%d-%m-%y", "%m-%d-%y", "%y-%m-%d", "%y-%d-%m"];

    let names = pd
        .iter()
        .filter(|(_, dates)| wrote_on_911(dates, &fmts))
        .map(|(name, _)| *name)
        .sorted()
        .collect::<Vec<&str>>();

    println!("{}", names.join(" "));
}

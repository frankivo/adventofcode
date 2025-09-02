use std::collections::HashMap;
use chrono::NaiveDate;
use i18n::util::file;

fn valid(date: &str, fmt: &str) -> bool {
    NaiveDate::parse_from_str(date, fmt).is_ok()
}

fn main() {
    let input = file::input(9);
    println!("{}", input);

    let mut pd = HashMap::<&str, Vec<&str>>::new();

    for line in input.lines() {
        let (date, names) = line.split_once(":").unwrap();
        names.trim().split(", ").for_each(|name| {
            pd.entry(name).or_insert(Vec::new()).insert(0, date);
        });
    }

    let fmts = vec!["%d-%m-%y", "%m-%d-%y", "%y-%m-%d", "%y-%d-%m"];

    for fmt in fmts {
        let x = pd.get("Frank").unwrap().iter().all(|d| valid(d, fmt));
        dbg!(fmt, x);
    }
}

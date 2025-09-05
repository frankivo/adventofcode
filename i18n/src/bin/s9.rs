use chrono::NaiveDate;
use i18n::util::file;
use std::collections::HashMap;

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
    let dates = pd.get("Peter").unwrap();
    let nine11 = NaiveDate::parse_from_str("01-09-11", "%y-%m-%d").unwrap();

    if let Some(is_911) = fmts.iter().find_map(|f| {
        if dates
            .iter()
            .all(|d| NaiveDate::parse_from_str(d, f).is_ok())
        {
            Some(
                dates
                    .iter()
                    .map(|d| NaiveDate::parse_from_str(d, f).unwrap())
                    .any(|d| d == nine11)
            )
        } else {
            None
        }
    }) {
        dbg!(is_911);
    } else {
        eprintln!("No format matched all dates");
    }
}

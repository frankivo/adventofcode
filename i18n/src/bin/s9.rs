use std::collections::HashMap;
use chrono::NaiveDate;
use i18n::util::file;

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


    let dates  = pd.get("Frank").unwrap();
    dbg!(dates);

    let validator = fmts.iter().filter_map(|f| {
        let x = dates.iter().all(|d| {
            NaiveDate::parse_from_str(d, f).is_ok()
        });

        if x {
            return Some(dates.iter().map(|d| NaiveDate::parse_from_str(d, f).unwrap()));
        }
        else {
            return None;
        }
    });

    for v in validator {
        for d in v{
            dbg!(d);
        }
    }

}

use std::collections::HashMap;
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

    for i in pd {
        dbg!(i.0, i.1);
    }
}

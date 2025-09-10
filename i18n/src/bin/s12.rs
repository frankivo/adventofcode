use i18n::util::file::input;
use unidecode::unidecode;

fn split_line(line: &str) -> (&str, &str, u32) {
    let (last_name, rest) = line.split_once(",").unwrap();
    let (first_name, phone) = rest.split_once(":").unwrap();
    let phone = phone.trim().parse::<u32>().expect("can't parse phone");
    (last_name.trim(), first_name.trim(), phone)
}

fn fix(name: &str) -> String {
    unidecode(&name.to_lowercase())
        .chars()
        .filter(|c| c.is_alphabetic())
        .collect::<String>()
}

fn main() {
    let input = input(12);

    let mut names: Vec<(_, _, _)> = input.lines().map(split_line).collect();

    dbg!(&names);

    names.sort_by(|a, b| fix(a.0).cmp(&fix(b.0)));
    dbg!(names);
}

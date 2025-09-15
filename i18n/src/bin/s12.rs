use i18n::util::file::input;
use regex::Regex;
use unidecode::unidecode;

struct Person {
    lastname: String,
    phone: u32,
}

fn split_line(line: &str) -> Person {
    let (last_name, rest) = line.split_once(",").unwrap();
    let (_, phone) = rest.split_once(":").unwrap();
    let phone = phone.trim().parse::<u32>().expect("can't parse phone");
    Person {
        lastname: last_name.trim().to_string(),
        phone: phone,
    }
}

fn clean(name: &str) -> String {
    name
        .chars()
        .filter(|c| c.is_alphabetic())
        .collect::<String>()
}

fn swedish_sort(s: &str) -> Vec<u32> {
    s.to_lowercase().chars()
        .map(|c| match c {
            'å' => 27,
            'ä' => 28,
            'æ' => 28,
            'ö' => 29,
            'ø' => 29,
            _ => {
                unidecode(&c.to_string()).chars().collect::<Vec<char>>()[0] as u32 - ('a' as u32)
                    + 1
            }
        })
        .collect()
}

fn middle(names: &Vec<Person>) -> u32 {
    names.iter().nth((names.len() / 2) + 0).unwrap().phone
}

fn strip_last_name(name: &str, matcher: &Regex) -> String {
    let res: Vec<_> = matcher.captures_iter(&name).collect();
    let idx = res[0].get(0).unwrap().start();
    name[idx..].to_string()
}

fn main() {
    let input = input(12);

    let mut names: Vec<Person> = input.lines().map(split_line).collect();

    // English list sort
    names.sort_by(|a, b| clean(&unidecode(&a.lastname.to_lowercase())).cmp(&clean(&unidecode(&b.lastname.to_lowercase()))));
    let english = middle(&names);

    // Swedish list sort
    names.sort_by_key(|a| swedish_sort(&clean(&a.lastname)));
    let swedish = middle(&names);

    // Dutch list sort
    let matcher = Regex::new(r"[A-Z]").unwrap();
    names.sort_by_key(|a| strip_last_name(&clean(&unidecode(&a.lastname)), &matcher).to_lowercase());

    let dutch = middle(&names);

    let res = english as u64 * swedish as u64 * dutch as u64;
    println!("Result: {}", res);
}

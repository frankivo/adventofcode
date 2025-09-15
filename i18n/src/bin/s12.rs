use i18n::util::file::input;
use std::fmt;
use unidecode::unidecode;

struct Person {
    firstname: String,
    lastname: String,
    phone: u32,
}

impl fmt::Debug for Person {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.debug_struct("Person")
            .field("lastname", &self.lastname)
            .field("firstname", &self.firstname)
            .finish()
    }
}

impl fmt::Display for Person {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}, {}: {}", self.lastname, self.firstname, self.phone)
    }
}

fn split_line(line: &str) -> Person {
    let (last_name, rest) = line.split_once(",").unwrap();
    let (first_name, phone) = rest.split_once(":").unwrap();
    let phone = phone.trim().parse::<u32>().expect("can't parse phone");
    Person {
        firstname: first_name.trim().to_string(),
        lastname: last_name.trim().to_string(),
        phone: phone,
    }
}

fn clean(name: &str) -> String {
    name.to_lowercase()
        .chars()
        .filter(|c| c.is_alphabetic())
        .collect::<String>()
}

fn swedish_sort(s: &str) -> Vec<u32> {
    s.chars().map(|c| {
        match c {
            'å' => 27,
            'ä' => 28,
            'æ' => 28,
            'ö' => 29,
            'ø' => 29,
            _ => {
                unidecode(&c.to_string()).chars().collect::<Vec<char>>()[0] as u32 - ('a' as u32) + 1
            }
        }
    }).collect()
}

fn middle(names: &Vec<Person>) -> u32 {
    let mid = (names.len() / 2) + 0;
    let x = names.iter().nth(mid).unwrap();
    x.phone
}

fn main() {
    let input = input(12);

    let mut names: Vec<Person> = input.lines().map(split_line).collect();

    // First list
    names.sort_by(|a, b| clean(&a.lastname).cmp(&clean(&b.lastname)));
    for n in &names {
        println!("{}", &n);
    }
    let first = middle(&names);
    println!("");

    // Second list
    names.sort_by_key(|a| swedish_sort(&clean(&a.lastname)));
    for n in &names {
        println!("{}", &n);
    }
    let second = middle(&names);

    println!("");

    dbg!(first, second);
}

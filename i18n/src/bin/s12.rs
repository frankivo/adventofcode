use encoding_rs::ISO_8859_10;
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

fn clean(name: &str, fix_nordic: bool) -> String {
    let name = name.to_uppercase();

    // Strip spaces and specials characters
    let stripped =  name.chars().filter(|c| c.is_alphabetic()).collect::<String>();
    if !fix_nordic || !is_nordic(&stripped) {
        return unidecode(&stripped);
    }
    return stripped.replace("Æ", "Į").replace("Ø", "Ö").replace("Ä", "Į"); // This is buggy
}

fn is_nordic(name: &str) -> bool{
    let (_, _, had_errors) = ISO_8859_10.encode(name);
    return !had_errors;
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
    names.sort_by(|a,b| clean(&a.lastname, false).cmp(&clean(&b.lastname, false)));
    for n in &names {
        println!("{}", &n);
    }
    let first = middle(&names);
    println!("");

    // Second list
    names.sort_by(|a,b| clean(&a.lastname, true).cmp(&clean(&b.lastname, true)));
    for n in &names {
        println!("{}", &n);
    }
    let second = middle(&names);

    println!("");

    dbg!(first, second);
}

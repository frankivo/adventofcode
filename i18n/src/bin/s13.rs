use itertools::Itertools;

fn main() {
    let x = "616e77c3a4686c65";
    let pairs = x.chars().tuples::<(char, char)>();
    let word :Vec<u8> = pairs.map(|p| {
        let m= format!("{}{}", p.0, p.1);
        u8::from_str_radix(&m, 16).unwrap()
    }).collect();

    dbg!(String::from_utf8(word).unwrap());
}

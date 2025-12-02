use everybodycodes::util::file;

fn main() {
    let binding = file::input(5, 1);
    let (_, numbers) = &binding.split_once(":").unwrap();
    let numbers: Vec<i16> = numbers
        .trim()
        .split(",")
        .map(|s| s.parse::<i16>().unwrap())
        .collect();
    let bone = numbers.into_iter().fold("".to_owned(), |bone, num| {
        let prev = bone.chars().last();
        match prev {
            Some(p) => {
                bone
            }
            None => bone + &num.to_string(),
        }
    });
    dbg!(bone);
}

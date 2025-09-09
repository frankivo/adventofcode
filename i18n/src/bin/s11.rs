fn rot(msg: &str, mv: i32) -> String {
    msg.chars()
        .map(|c|char::from_u32((c as i32 + mv) as u32).unwrap())
        .collect::<String>()
}

fn main() {
    dbg!("day11");

    let a = rot("abc", 13);
    let b = rot(&a, -13);
    dbg!(a, b);
}

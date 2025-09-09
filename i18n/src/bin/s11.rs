fn rot(msg: &str, mv: i32) -> String {
    msg.chars()
        .map(|c|char::from_u32((c as i32 + mv) as u32).unwrap())
        .collect::<String>()
}

fn main() {
    let uppers = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ".chars();
    let downers = "αβγδεζηθικλμνξοπρστυφχψω".chars();

    let a = rot("σζμ γ' ωοωλδθαξλδμξρ οπξρδυζ οξκτλζσθρ Ξγτρρδτρ.", 13);

    dbg!(a);
}

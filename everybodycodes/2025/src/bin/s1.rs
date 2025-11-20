use everybodycodes::util::file;

fn get_num(instr: &str) -> i8 {
    instr.chars().nth(1).unwrap().to_digit(10).unwrap() as i8
}

fn main() {
    let data = file::input(1);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").collect();
    let instructions = lines[2].split(",");
    let max_idx = names.len() as i8 - 1;

    let index = instructions.fold(0, |i, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr);
        let next = if go_right { i + steps } else { i - steps };
        next.clamp(0, max_idx)
    }) as usize;

    print!("My name is: {}", names.get(index).unwrap());
}

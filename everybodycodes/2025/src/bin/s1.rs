use everybodycodes::util::file;

fn get_num(instr: &str) -> i8 {
    instr.chars().nth(1).unwrap().to_digit(10).unwrap() as i8
}

fn part1(names: Vec<&str>, instructions: Vec<&str>) -> String {
    let max_idx = names.len() as i8 - 1;

    let index = instructions.into_iter().fold(0, |i, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr);
        let next = if go_right { i + steps } else { i - steps };
        next.clamp(0, max_idx)
    }) as usize;

    names.get(index).unwrap().to_string()
}

fn main() {
    let data = file::input(1);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").collect();
    let instructions: Vec<_> = lines[2].split(",").collect();

    print!("Part1: {}", part1(names, instructions));
}

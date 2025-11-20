use everybodycodes::util::file;

fn get_num(instr: &str) -> i8 {
    instr
        .chars()
        .filter(|c| c.is_digit(10))
        .collect::<String>()
        .parse()
        .unwrap()
}

fn parse(part: i32) -> (Vec<String>, Vec<String>) {
    let data = file::input(1, part);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").map(str::to_owned).collect();
    let instructions: Vec<_> = lines[2].split(",").map(str::to_owned).collect();
    (names, instructions)
}

fn part1() -> String {
    let (names, instructions) = parse(1);

    let index = instructions.iter().fold(0, |i, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr);
        let next = if go_right { i + steps } else { i - steps };
        next.clamp(0, names.len() as i8 - 1)
    });

    names.get(index as usize).unwrap().to_string()
}

fn part2() -> String {
    let (names, instructions) = parse(2);
    let n = names.len() as i32;

    let index: i32 = instructions.iter().fold(0, |i, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr) as i32;
        let next = if go_right { i + steps } else { i - steps };
        ((next % n) + n) % n
    });

    names.get(index as usize).unwrap().to_string()
}

fn main() {
    println!("My name is: {}", part1());
    println!("My parent is: {}", part2());
}

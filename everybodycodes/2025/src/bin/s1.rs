use everybodycodes::util::file;

fn get_num(instr: &str) -> i8 {
    instr[1..].parse().unwrap()
}

fn parse(part: i8) -> (Vec<String>, Vec<String>) {
    let data = file::input(1, part);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").map(str::to_owned).collect();
    let instructions: Vec<_> = lines[2].split(",").map(str::to_owned).collect();
    (names, instructions)
}

fn solve(part: i8) -> String {
    let (names, instructions) = parse(part);
    let num_names = names.len() as i8;

    let index = instructions.iter().fold(0, |i, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr);
        let next = if go_right { i + steps } else { i - steps };

        if part == 1 {
            next.clamp(0, num_names as i8 - 1)
        } else {
            ((next % num_names) + num_names) % num_names
        }
    });

    names[index as usize].to_string()
}

fn part3() -> String {
    let (names, instructions) = parse(3);
    let num_names = names.len() as i8;

    let shuffled = instructions.iter().fold(names, |names, instr| {
        let go_right = instr.starts_with('R');
        let steps = get_num(&instr);
        let next = if go_right { steps } else { -steps };
        let idx = ((next % num_names) + num_names) % num_names;

        let p1 = &names[1..idx as usize];
        let p2 = &names[idx as usize + 1..];

        let swapped: Vec<String> = std::iter::once(names[idx as usize].clone())
            .chain(p1.iter().cloned())
            .chain(std::iter::once(names[0].to_string()))
            .chain(p2.iter().cloned())
            .collect();

        swapped
    });

    shuffled[0].clone()
}

fn main() {
    println!("My name is: {}", solve(1));
    println!("My first parent is: {}", solve(2));
    println!("My second parent is: {}", part3());
}

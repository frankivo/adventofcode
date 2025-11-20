use everybodycodes::util::file;

fn get_num(instr: &str) -> u8 {
    instr.chars().nth(1).unwrap().to_digit(10).unwrap() as u8
}

fn main() {
    let data = file::input(1);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").collect();
    let instructions = lines[2].split(",");

    let mut i: i8 = 0;
    let max_idx: u8 = (names.len() - 1) as u8;

    for instr in instructions {
        let go_right = instr.chars().nth(0).unwrap() == 'R';
        let steps = get_num(&instr) as i8;
        dbg!(go_right);

        if go_right {
            i += steps;
        } else {
            i -= steps;
        }

        if i < 0 {
            i = 0;
        }
        if i > max_idx as i8 {
            i = max_idx as i8;
        }
    }

    dbg!(names.get(i as usize));
}

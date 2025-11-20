use everybodycodes::util::file;

fn get_num(instr: &str) -> i32 {
    instr.chars().nth(1).unwrap().to_digit(10).unwrap() as i32
}

fn main() {
    let data = file::input(1);
    let lines: Vec<_> = data.lines().collect();
    let names: Vec<_> = lines[0].split(",").collect();
    let instructions = lines[2].split(",");

    let mut i = 0;

    for instr in instructions {
        let go_right = instr.chars().nth(0).unwrap() == 'R';
        let steps = get_num(&instr);
        dbg!(go_right);

        if go_right {
            i += steps;
        } else {
            i -= steps;
        }

        if i < 0 {
            i = 0;
        }
        if i >= (names.len() -1) as i32 {
            i = (names.len() -1) as i32 ;
        }

    }

    dbg!(names.get(i as usize));
}

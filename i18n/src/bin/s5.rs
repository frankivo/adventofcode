use i18n::util::file;

fn main() {
    let data = file::input(5);
    let lines: Vec<&str> = data.lines().collect();
    let width = lines[0].chars().count();

    let mut cnt = 0;

    for (line_num, line) in lines.iter().enumerate() {
        let idx = (line_num * 2) % width; // position based on line n
        if line.chars().nth(idx).unwrap() == '💩' {
            cnt += 1;
        }
    }

    print!("Stepped {} times in 💩", cnt);
}

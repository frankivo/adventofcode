use i18n::util::file;

fn main() {
    let data = file::input(5);
    let lines: Vec<&str> = data.lines().collect();
    let width = lines[0].chars().count();

    let mut idx = 0;
    let mut cnt = 0;

    for l in lines {
        if l.chars().nth(idx).unwrap() == '💩' {
            cnt += 1;
        }
        idx+=2;
        if idx >= width {
            idx -= width;
        }
    }

    print!("Stepped {} times in 💩", cnt);
}

use i18n::util::file;

fn main() {
    let counts = file::input(5)
        .lines()
        .enumerate()
        .map(|(ln, line)| {
            let idx = (ln * 2) % line.chars().count();
            return (line.chars().nth(idx).unwrap() == '💩') as i8;
        })
        .sum::<i8>();

    print!("Stepped {} times in 💩", counts);
}

use i18n::util::file::input;

fn main() {
    let input = input(14);
    let input = input
        .lines()
        .map(|l| l.split_once(" × ").unwrap());

    for l in input {
        dbg!(&l);
    }
}

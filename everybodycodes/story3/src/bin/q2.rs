use everybodycodes::util::file;

fn find_pos(needle: char) -> Option<(usize, usize)> {
    file::input(2, 1)
        .lines()
        .enumerate()
        .flat_map(|(y, line)| {
            line.chars()
                .position(|c| c == needle)
                .map(|x| (y, x))
        })
        .nth(0)
}

fn part1() -> u32 {
    let (source, bone) = (find_pos('@'), find_pos('#'));
    dbg!(source, bone);

    0
}

fn main() {
    println!(
        "After how many steps will the sound reach the vocal bone in the template? {}",
        part1()
    );
}

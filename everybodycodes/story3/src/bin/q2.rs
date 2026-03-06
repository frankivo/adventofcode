use everybodycodes::util::file;

fn part1() -> u32 {
    let binding = file::input(2, 1);
    dbg!(binding.lines());

    0
}

fn main() {
    println!(
        "After how many steps will the sound reach the vocal bone in the template? {}",
        part1()
    );
}

use everybodycodes::util::file;

fn part1() -> u32 {
    let binding = file::input(2, 1);

    let start: Vec<_> = binding
        .lines()
        .enumerate()
        .flat_map(|(y, line)| {
            let source = line.chars().position(|c| c == '@').map(|x| ('@', y, x));
            let bone = line.chars().position(|c| c == '#').map(|x| ('#', y, x));
            vec![source, bone].into_iter().flatten()
        })
        .collect();

    dbg!(start);

    0
}

fn main() {
    println!(
        "After how many steps will the sound reach the vocal bone in the template? {}",
        part1()
    );
}

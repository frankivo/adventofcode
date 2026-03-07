use everybodycodes::util::file;
use std::collections::HashSet;

fn find_pos(needle: char) -> Option<(usize, usize)> {
    file::input(2, 1)
        .lines()
        .enumerate()
        .flat_map(|(y, line)| line.chars().position(|c| c == needle).map(|x| (y, x)))
        .nth(0)
}

fn part1() -> usize {
    let (source, bone) = (find_pos('@').unwrap(), find_pos('#').unwrap());

    let mut pos = source;
    let mut direction = 'U';

    let mut matrix: HashSet<(usize, usize)> = HashSet::from([source]);

    loop {
        let tmp_pos = match direction {
            'U' => (pos.0 -1, pos.1),
            'R' => (pos.0 , pos.1 + 1),
            'D' => (pos.0 +1, pos.1),
            'L' => (pos.0, pos.1 -1),
            _ => unimplemented!(),
        };

         if tmp_pos == bone {
            break;
        }

        if !matrix.contains(&tmp_pos) {
            matrix.insert(tmp_pos);
            pos = tmp_pos;
        }

        direction = match direction {
            'U' => 'R',
            'R' => 'D',
            'D' => 'L',
            'L' => 'U',
            _ => unimplemented!(),
        };
    }

    matrix.len()
}

fn main() {
    println!(
        "After how many steps will the sound reach the vocal bone in the template? {}",
        part1()
    );
}

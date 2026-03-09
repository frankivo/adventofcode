use everybodycodes::util::file;
use std::collections::HashSet;
use std::iter::successors;

fn find_pos(needle: char) -> Option<(usize, usize)> {
    file::input(2, 1)
        .lines()
        .enumerate()
        .flat_map(|(y, line)| line.chars().position(|c| c == needle).map(|x| (y, x)))
        .nth(0)
}

fn part1() -> usize {
    let (source, bone) = (find_pos('@').unwrap(), find_pos('#').unwrap());
    let res = successors(
        Some((HashSet::from([source]), source, 'U')),
        |(map, pos, direction)| {
            let tmp_pos = match direction {
                'U' => (pos.0 - 1, pos.1),
                'R' => (pos.0, pos.1 + 1),
                'D' => (pos.0 + 1, pos.1),
                'L' => (pos.0, pos.1 - 1),
                _ => unreachable!(),
            };

            let mut map = (*map).clone();

            let direction = match direction {
                'U' => 'R',
                'R' => 'D',
                'D' => 'L',
                'L' => 'U',
                _ => unreachable!(),
            };

            let pos = {
                if !map.contains(&tmp_pos) {
                    map.insert(tmp_pos);
                    tmp_pos
                } else {
                    *pos
                }
            };

            (tmp_pos != bone).then_some((map, pos, direction))
        },
    );

    res.last().unwrap().0.len()
}

fn main() {
    println!(
        "After how many steps will the sound reach the vocal bone in the template? {}",
        part1()
    );
}

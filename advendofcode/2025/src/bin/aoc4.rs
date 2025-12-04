use aoc::util::file;

use std::collections::HashSet;

type Point = (i16, i16);
type Map = HashSet<Point>;

fn map_create(raw: &str) -> Map {
    raw.lines()
        .enumerate()
        .flat_map(|(y, line)| {
            line.chars()
                .into_iter()
                .enumerate()
                .filter_map(move |(x, c)| match c {
                    '@' => Some((y as i16, x as i16)),
                    _ => None,
                })
        })
        .collect()
}

fn adjacent_count(map: &Map, (y, x): (i16, i16)) -> usize {
    (-1..=1)
        .flat_map(|yy| (-1..=1).map(move |xx| (yy, xx)))
        .filter(|&(yy, xx)| !(yy == 0 && xx == 0))
        .filter(|&(yy, xx)| map.contains(&(y + yy, x + xx)))
        .count()
}

fn map_remove(map: &Map) -> Map {
    map.iter()
        .copied()
        .filter(|&(y, x)| adjacent_count(map, (y, x)) >= 4)
        .collect()
}

fn part1(map: &Map, size: i16) -> i16 {
    (0..size).fold(0, |sum, y| {
        sum + (0..size).fold(0, |sum, x| {
            sum + (map.contains(&(y, x)) && adjacent_count(&map, (y, x)) < 4) as i16
        })
    })
}

fn part2(map: &Map) -> i16 {
    let papers = map.len();
    let mut map = map.clone();

    loop {
        let prev = map.len();
        map = map_remove(&map);
        if map.len() == prev {
            return (papers - prev) as i16;
        }
    }
}

fn main() {
    let binding = file::input(4, 1);
    let map: Map = map_create(&binding);
    let size: i16 = binding.lines().count() as i16;
    dbg!(part1(&map, size));
    dbg!(part2(&map));
}

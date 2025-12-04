use aoc::util::file;

type Point = (i16, i16);
type Map = Vec<Point>;

fn build_map(raw: &str) -> Map {
    raw.lines()
        .enumerate()
        .map(|(y, line)| {
            line.chars()
                .into_iter()
                .enumerate()
                .filter_map(|(x, c)| match c {
                    '@' => Some((y as i16, x as i16)),
                    _ => None,
                })
                .collect::<Map>()
        })
        .collect::<Vec<_>>()
        .iter()
        .flat_map(|inner_vec| inner_vec.iter())
        .cloned()
        .collect()
}
fn adjacent(map: &Map, point: Point) -> Map {
    let mut adj: Map = Vec::new();
    let (y, x) = point;
    for yy in -1..=1 {
        for xx in -1..=1 {
            if yy != 0 || xx != 0 {
                let y = y + yy;
                let x = x + xx;
                if map.contains(&(y, x)) {
                    adj.push((y, x));
                }
            }
        }
    }
    adj
}
fn part1(map: &Map, size: i16) -> i16 {
    (0..size).fold(0, |sum, y| {
        sum + (0..size).fold(0, |sum, x| {
            sum + (map.contains(&(y, x)) && adjacent(&map, (y, x)).len() < 4) as i16
        })
    })
}
fn remove(map: &Map, size: i16) -> Map {
    let mut clone = map.clone();
    for y in 0..size {
        for x in 0..size {
            if clone.contains(&(y, x)) && adjacent(&clone, (y, x)).len() < 4 {
                clone.retain(|&i| i != (y, x));
            }
        }
    }
    clone
}
fn part2(map: &Map, size: i16) -> i16 {
    let items = map.len();
    let mut removed = usize::MAX;
    let mut map = map.clone();
    while removed > 0 {
        let items = map.len();
        map = remove(&map, size);
        removed = items - map.len();
    }
    (items - map.len()) as i16
}
fn main() {
    let binding = file::input(4, 1);
    let map: Map = build_map(&binding);
    let size: i16 = binding.lines().count() as i16;
    dbg!(part1(&map, size));
    dbg!(part2(&map, size));
}

use everybodycodes::util::file;

struct Row {
    base: i16,
    left: Option<i16>,
    right: Option<i16>,
}

struct Sword {
    id: i16,
    strength: Vec<Row>,
}

impl Row {
    fn try_insert(&mut self, n: i16) -> bool {
        match n.cmp(&self.base) {
            std::cmp::Ordering::Less if self.left.is_none() => {
                self.left = Some(n);
                true
            }
            std::cmp::Ordering::Greater if self.right.is_none() => {
                self.right = Some(n);
                true
            }
            _ => false,
        }
    }
}

fn build_sword(input: &str) -> Sword {
    let (id, numbers) = input.split_once(":").unwrap();
    let id = id.parse::<i16>().unwrap();
    let numbers = numbers.trim().split(",").map(|s| s.parse::<i16>().unwrap());

    let mut sword: Vec<Row> = Vec::with_capacity(10);
    for n in numbers {
        if !sword.iter_mut().any(|row| row.try_insert(n)) {
            sword.push(Row { base: n, left: None, right: None, });
        }
    }

    Sword{id: id, strength: sword}
}

fn part1() -> String {
    let binding = file::input(5, 1);
    let sword = build_sword(&binding);

    sword.strength.into_iter().map(|r| r.base.to_string()).collect::<Vec<_>>().join("")
}

fn main() {
    dbg!(part1());
}

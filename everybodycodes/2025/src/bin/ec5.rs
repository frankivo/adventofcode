use everybodycodes::util::file;

struct Row {
    id: i16,
    left: Option<i16>,
    right: Option<i16>,
}

impl Row {
    fn try_insert(&mut self, n: i16) -> bool {
        match n.cmp(&self.id) {
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

fn part1() -> String {
    let binding = file::input(5, 1);
    let (_, numbers) = &binding.split_once(":").unwrap();
    let numbers = numbers.trim().split(",").map(|s| s.parse::<i16>().unwrap());

    let mut sword: Vec<Row> = Vec::with_capacity(10);
    for n in numbers {
        if !sword.iter_mut().any(|row| row.try_insert(n)) {
            sword.push(Row { id: n, left: None, right: None, });
        }
    }

    sword.into_iter().map(|r| r.id.to_string()).collect::<Vec<_>>().join("")
}

fn main() {
    dbg!(part1());
}

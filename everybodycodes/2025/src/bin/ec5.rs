use everybodycodes::util::file;

#[derive(Debug, Clone, Copy)]
struct Row {
    id: i16,
    left: Option<i16>,
    right: Option<i16>,
}

fn main() {
    let binding = file::input(5, 1);
    let (_, numbers) = &binding.split_once(":").unwrap();
    let numbers: Vec<i16> = numbers
        .trim()
        .split(",")
        .map(|s| s.parse::<i16>().unwrap())
        .collect();

    let mut sword: Vec<Row> = Vec::with_capacity(10);
    for n in numbers {
        let mut pushed = false;

        for (i, r) in sword.clone().into_iter().enumerate() {
            if n < r.id && r.left == None {
                let tmp = Row {
                    id: r.id,
                    left: Some(n),
                    right: r.right,
                };
                sword[i] = tmp;
                pushed = true;
                break;
            } else if n > r.id && r.right == None {
                let tmp = Row {
                    id: r.id,
                    left: r.left,
                    right: Some(n),
                };
                sword[i] = tmp;
                pushed = true;
                break;
            }
        }

        if !pushed {
            sword.push(Row {
                id: n,
                left: None,
                right: None,
            });
        }
    }

    let quality: Vec<_> = sword.into_iter().map(|r| r.id).collect();
    for q in quality {
        print!("{}", q);
    }
}

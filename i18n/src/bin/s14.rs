use std::collections::HashMap;

use i18n::util::file::input;

fn main() {
    let input = input(14);
    let input = input
        .lines()
        .map(|l| l.split_once(" × ").unwrap());



    let char_map : HashMap<char, i32> = HashMap::from([
        ('一', 1),
        ('二', 2),
        ('三', 3),
        ('四', 4),
        ('五', 5),
        ('六', 6),
        ('七', 7),
        ('八', 8),
        ('九', 9),
        ('十', 10),
        ('百', 100),
        ('千', 1000),
        ('万', 10_000),
        ]);

    // for l in input {
    //     dbg!(&l);
    // }

    for (c, n) in char_map {
        dbg!(n, c.escape_unicode() as u32);
    }
}

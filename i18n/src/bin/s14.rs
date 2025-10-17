use std::collections::HashMap;

use i18n::util::file::input;


fn to_int(s: &str, numbers: &HashMap<char, i32>, mutators: &HashMap<char, i32>) -> i32 {
    let mut sum = 0;
    let mut tmp = 0;

    for (i, c) in s.chars().enumerate() {
        if numbers.contains_key(&c) {
            tmp = numbers.get(&c).unwrap().to_owned();
        }
        if mutators.contains_key(&c) {
            sum += mutators.get(&c).unwrap() * tmp;
        } else if i + 1 == s.chars().count() {
            sum += tmp;
        }
    }

    return sum;
}

fn main() {
    let input = input(14);
    let input = input.lines().map(|l| l.split_once(" × ").unwrap());

    let char_map: HashMap<char, i32> = HashMap::from([
        ('一', 1),
        ('二', 2),
        ('三', 3),
        ('四', 4),
        ('五', 5),
        ('六', 6),
        ('七', 7),
        ('八', 8),
        ('九', 9),
    ]);

    let myriads: HashMap<char, i32> = HashMap::from([
        ('十', 10),
        ('百', 100),
        ('千', 1000),
        ('万', 10_000),
        ('億', 100_000_000),
    ]);

    let tests: Vec<_> = vec!["三百", "三百二十一", "四千", "五万", "九万九千九百九十九", "四十二万四十二", "九億八千七百六十五万四千三百二十一"];
    for t in tests {
        dbg!(t, to_int(t, &char_map, &myriads));
    }
}

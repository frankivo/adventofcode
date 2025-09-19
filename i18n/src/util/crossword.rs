use regex::Regex;

pub fn solve(puzzle: &str, words: &Vec<String> ) -> usize {
    puzzle
        .lines()
        .map(|q| {
            let rx = Regex::new(&format!("^{}$", q.trim())).unwrap();
            words
                .iter()
                .position(|word| rx.is_match(&word))
                .map_or(0, |idx: usize| idx + 1)
        })
        .sum()
}

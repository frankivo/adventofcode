use std::env;
use std::fs;

fn valid_sms(msg: &str) -> bool {
    return msg.len() <= 160;
}

fn valid_tweet(msg: &str) -> bool {
    return msg.chars().count() <= 140;
}

fn cost(msg: &str) -> i32 {
    if valid_sms(msg) && valid_tweet(msg) { return 13; }
    if valid_sms(msg) { return 11; }
    if valid_tweet(msg) { return 7; }
    return 0;
}

fn main() {
    let demo = env::args().any(|a| a == "test");
    let input = if demo { "test-input/1.txt" } else { "input/1.txt" } ;

    let lines = fs::read_to_string(input).expect("Read failed!");

    let costs = lines.split("\n").map(|l| cost(l)).sum::<i32>();
    println!("Total costs: {}", costs);
}

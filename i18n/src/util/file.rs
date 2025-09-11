use dotenv::dotenv;
use std::{env, fs};
use std::fs::File;
use std::io::Write;

pub fn input(day: i32) -> String {
    let demo = env::args().any(|a| a == "test");
    let input = if demo {
        format!("test-input/{}.txt", day)
    } else {
        format!("input/{}.txt", day)
    };

    if !fs::exists(&input).unwrap() {
        download(day, demo, &input);
    }

    return fs::read_to_string(&input).expect(&format!("read failed! {}", &input));
}

fn get_cookie() -> String {
    dotenv().expect("Could net get env-file");
    env::var("COOKIE").expect("COOKIE var not found")
}

fn download(day: i32, demo: bool, target: &str) {
    let url = format!(
        "https://i18n-puzzles.com/puzzle/{}/{}input",
        day,
        demo.then_some("test-").unwrap_or("")
    );
    dbg!(&url);

    let rt = tokio::runtime::Runtime::new().unwrap();
    let resp = rt.block_on(async {
        let client = reqwest::Client::new();
        client
            .get(&url)
            .header("Cookie", get_cookie())
            .send()
            .await
    }).expect("Failed to download").error_for_status().expect("Download error");

    let body = rt.block_on(resp.bytes()).expect("Failed to get body");

    let mut f = File::create(target).expect("Cannot open target");
    f.write_all(&body).expect("Write failed");
}

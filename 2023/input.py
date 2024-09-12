from dotenv import dotenv_values
import requests
import os.path as op

class input:
    def __init__(self, day: int, demo: bool) -> None:
        self.day = day
        self.demo = demo
    
    def get(self) -> list[str]:
        if not self.exists():
            if self.demo:
                raise FileNotFoundError("Demo file missing")
            self.download()
        return self.read()

    def filename(self) -> str:
        dir = "input" if not self.demo else "demo"
        return f"{dir}/day{self.day}.txt"

    def download(self) -> None:
        headers = { "User-Agent": "https://github.com/frankivo/adventofcode frank+github@scriptzone.nl" }
        cookies = { "session": dotenv_values(".env")["API_KEY"] }

        url = f"https://adventofcode.com/2023/day/{self.day}/input"
        with requests.get(url, headers=headers, cookies=cookies) as response:
            response.raise_for_status()
            with open(self.filename(), "w+") as file:
                file.write(response.text)

    def exists(self) -> bool:
        return op.exists(self.filename())
    
    def read(self) -> list[str]:
        with open(self.filename()) as file:
            return file.readlines()

from dotenv import dotenv_values
from os.path import exists as file_exists
import requests

class input:
    def __init__(self, day: int, demo: bool) -> None:
        self.day = day
        self.demo = demo
    
    def get(self, part: int) -> list[str]:
        filename = self.filename(part)

        if not file_exists(filename) and not self.demo:
            self.download(filename)
        
        with open(filename) as file:
            return file.readlines()

    def filename(self, part: int) -> str:
        dir = "input" if not self.demo else "demo"
        if self.demo:
            files = [f"day{self.day}", f"day{self.day}_p{part}"]
            for f in files:
                file = f"{dir}/{f}.txt"
                if file_exists(file):
                    return file
            raise FileNotFoundError("Demo file missing")

        return f"{dir}/day{self.day}.txt"

    def download(self, filename: str) -> None:
        print("Download")

        headers = { "User-Agent": "https://github.com/frankivo/adventofcode frank+github@scriptzone.nl" }
        cookies = { "session": dotenv_values(".env")["API_KEY"] }

        url = f"https://adventofcode.com/2023/day/{self.day}/input"
        with requests.get(url, headers=headers, cookies=cookies) as response:
            response.raise_for_status()
            with open(filename, "w+") as file:
                file.write(response.text)

from dotenv import dotenv_values
import requests
import os.path as op

class input:
    def __init__(self, day: int) -> None:
        self.day = day
    
    def get(self) -> str:
        if not self.exists():
            self.download()

    def filename(self) -> str:
        return f"input/day{self.day}.txt"

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

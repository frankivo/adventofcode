# --- Day 2: Cube Conundrum ---
# https://adventofcode.com/2023/day/2

from data import data
import re


def part1(data: data) -> None:
    games = [game(g) for g in data.getlines(part=1)]
    valid = [g.id for g in games if g.valid()]
    print(sum(valid))


def part2(data: data) -> None:
    games = [game(g) for g in data.getlines(part=2)]
    powers = [x.pow() for x in games]
    print(sum(powers))


class game:
    def __init__(self, raw: str) -> None:
        self.set_counts = game.game_count(raw)
        self.id = game.game_id(raw)

    @staticmethod
    def game_id(raw: str) -> int:
        return int(re.search(r"(Game\s(\d+):)", raw).group(2))

    @staticmethod
    def game_count(raw: str) -> dict:
        return [game.set_count(set) for set in raw.split(";")]

    @staticmethod
    def set_count(raw: str) -> dict:
        result = {}
        for m in re.finditer(r"(\d+)\s*(\w+)", raw):
            colour = m.group(2)
            result.update({colour: int(m.group(1)) + result.get(colour, 0)})
        return result

    def valid(self) -> bool:
        return all(
            [s.get("blue", 0) <= 14 and s.get("green", 0) <= 13 and s.get("red", 0) <= 12 for s in self.set_counts]
        )

    def pow(self) -> int:
        return self.get_minimal("blue") * self.get_minimal("green") * self.get_minimal("red")

    def get_minimal(self, colour: str) -> int:
        return max([s.get(colour, 0) for s in self.set_counts])

# --- Day 2: Cube Conundrum ---
# https://adventofcode.com/2023/day/2

import re

def part1(input: list[str]) -> None:
    games = [ game(g) for g in input ]
    valid = [ g.id for g in games if g.valid() ]
    print(sum(valid))

class game:
    def __init__(self, raw: str) -> None:
        self.marbles = game.game_count(raw)
        self.id = game.game_id(raw)

    @staticmethod
    def game_id(raw: str) -> None:
        return int(re.search(r'(Game\s(\d+):)', raw).group(2))

    @staticmethod
    def game_count(raw: str) -> None:
        result = {}

        for m in re.finditer(r'(\d+)\s*(\w+)', raw):
            colour = m.group(2)
            count = int(m.group(1))

            old = result.get(colour, 0)
            result.update ( { colour : count + old } )

        return result

    def valid(self) -> bool:
        return self.marbles["blue"] <= 14 and self.marbles["green"] <=13 and self.marbles["red"] <=12

def part2(input: list[str]) -> None:
    pass
# --- Day 4: Scratchcards ---
# https://adventofcode.com/2023/day/4

import re
from math import pow

def part1(data: list[str]) -> None:
    class card:
        def __init__(self, raw: str) -> None:
            self.winning, self.numbers = card.parse(raw)
            self.winners = self.calc_wins()

        @staticmethod
        def parse(raw: str):
            w = raw.split(":")[1].split("|")
            return re.findall(r"\d+", w[0]), re.findall(r"\d+", w[1])
        
        def calc_wins(self) -> list[int]:
            return list(set(self.winning) & set(self.numbers))

    def score(w: int) -> int:
        return 0 if w == 0 else int(pow(2, w - 1))

    cards = [ card(c) for c in data ]
    wins = [ len(c.calc_wins()) for c in cards ]
    scores = [ score(w) for w in wins ]

    print (wins)
    print(scores)

def part2(data: list[str]) -> None:

    pass


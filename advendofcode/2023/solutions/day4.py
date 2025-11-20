# --- Day 4: Scratchcards ---
# https://adventofcode.com/2023/day/4

from data import data
from math import pow
import re


def part1(data: data) -> None:
    def score(w: int) -> int:
        return 0 if w == 0 else int(pow(2, w - 1))

    cards = [card(c) for c in data.getlines(part=1)]
    wins = [len(c.calc_wins()) for c in cards]
    print(sum([score(w) for w in wins]))


def part2(data: data) -> None:
    cards = {c.id: c for c in [card(c) for c in data.getlines(part=2)]}
    last = max(cards.keys())
    scores = {c: 1 for c in cards}

    for c in sorted(cards.keys()):
        wins = len(cards.get(c).winners)
        incs = [c + i + 1 for i in range(wins)]
        scores.update({n: scores.get(n) + scores.get(c) for n in incs if n <= last})

    print(sum(scores.values()))


class card:
    def __init__(self, raw: str) -> None:
        self.id = card.game_id(raw)
        self.winning, self.numbers = card.parse(raw)
        self.winners = self.calc_wins()

    @staticmethod
    def game_id(raw: str) -> int:
        return int(re.search(r"(Card\s+(\d+):)", raw).group(2))

    @staticmethod
    def parse(raw: str):
        w = raw.split(":")[1].split("|")
        return re.findall(r"\d+", w[0]), re.findall(r"\d+", w[1])

    def calc_wins(self) -> list[int]:
        return list(set(self.winning) & set(self.numbers))

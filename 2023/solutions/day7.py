# --- Day 7: Camel Cards ---
# https://adventofcode.com/2023/day/7

from data import data

CARDS = {c: i for i, c in enumerate("AKQJT98765432")}


def part1(data: data) -> None:
    hands = [hand(sub.split(" ")[0]) for sub in data.getlines()]
    for h in hands:
        print(f"{h} -> {h.rank()}")
        # h.score()


def part2(data: data) -> None:
    pass


class card:
    def __init__(self, char: str) -> None:
        self.char = char

    def __gt__(self, other):
        return CARDS[self.char] < CARDS[other.char]

    def __str__(self) -> str:
        return f"Card: {self.char}"


class hand:
    def __init__(self, raw: str) -> None:
        self.raw = raw
        self.cards = reversed(sorted([card(c) for c in raw]))

    def __str__(self) -> str:
        return "".join([c.char for c in self.cards])

    def rank(self) -> int:
        c = sorted([self.raw.count(c) for c in set(self.raw)])

        if 5 in c:  # Five of a kind
            return 0
        if 4 in c:  # Four of a kind
            return 1
        if 2 in c and 3 in c:  # Full house
            return 3
        if 3 in c:  # Three of a kind
            return 4
        if c[0] == 1 and c[1] == 2 and c[2] == 2:  # Two pair
            return 5
        if c[-1] == 2:  # One pair
            return 6
        return 7

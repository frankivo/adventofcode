# --- Day 7: Camel Cards ---
# https://adventofcode.com/2023/day/7

from data import data

CARDS = {c: i for i, c in enumerate("AKQJT98765432")}


def part1(data: data) -> None:
    hands = [hand(sub) for sub in data.getlines()]
    scores = [h.bid * (r) for r, h in enumerate(sorted(hands), 1)]
    print(sum(scores))


def part2(data: data) -> None:
    pass


class hand:
    def __init__(self, raw: str) -> None:
        tmp = raw.split(" ")
        self.cards = tmp[0]
        self.bid = int(tmp[1])

    def __str__(self) -> str:
        return self.cards

    def rank(self) -> int:
        c = sorted([self.cards.count(c) for c in set(self.cards)])

        if 5 in c:  # Five of a kind
            return 1
        if 4 in c:  # Four of a kind
            return 2
        if 2 in c and 3 in c:  # Full house
            return 3
        if 3 in c:  # Three of a kind
            return 4
        if c[0] == 1 and c[1] == 2 and c[2] == 2:  # Two pair
            return 5
        if c[-1] == 2:  # One pair
            return 6
        return 7

    def __gt__(self, other):
        r1, r2 = self.rank(), other.rank()
        if r1 != r2:
            return r1 < r2

        for i in range(5):
            r1, r2 = CARDS[self.cards[i]], CARDS[other.cards[i]]
            if r1 != r2:
                return r1 < r2

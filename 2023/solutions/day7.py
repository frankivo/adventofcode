# --- Day 7: Camel Cards ---
# https://adventofcode.com/2023/day/7

from data import data

CARDS = {c: i for i, c in enumerate(reversed("AKQJT98765432"))}


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
            return 7
        if 4 in c:  # Four of a kind
            return 6
        if 2 in c and 3 in c:  # Full house
            return 5
        if 3 in c:  # Three of a kind
            return 4
        if c[0] == 1 and c[1] == 2 and c[2] == 2:  # Two pair
            return 3
        if c[-1] == 2:  # One pair
            return 2
        return 1

    def card_score(self, pos: int) -> int:
        return CARDS[self.cards[pos]]

    def __gt__(self, other):
        if self.rank() != other.rank():
            return self.rank() > other.rank()

        for i in range(5):
            if not self.card_score(i) == other.card_score(i):
                return self.card_score(i) > other.card_score(i)

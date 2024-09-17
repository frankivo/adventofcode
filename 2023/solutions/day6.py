# --- Day 6: Wait For It ---
# https://adventofcode.com/2023/day/6

from data import data
from functools import reduce
import re


def part1(data: data) -> None:
    game_data = data.getlines()
    t = list(map(int, re.findall(r"\d+", game_data[0])))
    d = list(map(int, re.findall(r"\d+", game_data[1])))

    wins = [get_wins(t[i], d[i]) for i in range(len(t))]
    print(reduce((lambda x, y: x * y), wins))


def part2(data: data) -> None:
    game_data = data.getlines()
    t = int("".join(re.findall(r"\d+", game_data[0])))
    d = int("".join(re.findall(r"\d+", game_data[1])))

    print(get_wins(t, d))


def get_distances(lower, upper) -> iter:
    for speed in range(lower, upper + 1):
        yield (upper - speed) * speed


def get_wins(limit: int, dist: int) -> int:
    return len([d for d in get_distances(0, limit) if d > dist])

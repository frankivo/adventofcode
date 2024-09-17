# --- --- Day 6: Wait For It ---
# https://adventofcode.com/2023/day/6

from data import data
import re
from functools import reduce


def part1(data: data) -> None:
    game_data = data.getlines(part=1)

    def get_distances(limit: int) -> iter:
        for speed in range(limit + 1):
            yield (limit - speed) * speed

    def get_wins(limit: int, dist: int) -> int:
        return len([d for d in get_distances(limit) if d > dist])

    t = list(map(int, re.findall(r"\d+", game_data[0])))
    d = list(map(int, re.findall(r"\d+", game_data[1])))

    records = [{"time": t[i], "record": d[i]} for i, _ in enumerate(t)]
    wins = [ get_wins(r["time"], r["record"]) for r in records ]
    print(reduce((lambda x, y: x *y), wins))


def part2(data: data) -> None:
    pass

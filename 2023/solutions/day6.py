# --- --- Day 6: Wait For It ---
# https://adventofcode.com/2023/day/6

from data import data
import re


def part1(data: data) -> None:
    game_data = data.getlines(part=1)

    def get_distances(limit: int) -> iter:
        for speed in range(limit + 1):
            yield limit, (limit - speed) * speed

    def count_wins(limit, dist) -> int:
        return len([d for t, d in dists if d > dist and t <= limit])

    t = list(map(int, re.findall(r"\d+", game_data[0])))
    d = list(map(int, re.findall(r"\d+", game_data[1])))

    records = [{"time": t[i], "record": d[i]} for i, _ in enumerate(t)]
    dists = list(get_distances(max(t)))

    w = [count_wins(r["time"], r["record"]) for r in records]
    print(w)


def part2(data: data) -> None:
    pass

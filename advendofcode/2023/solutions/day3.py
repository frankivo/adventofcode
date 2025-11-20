# --- Day 3: Gear Ratios ---
# https://adventofcode.com/2023/day/3

from data import data
from itertools import chain
import re


def part1(data: data) -> None:
    gear_data = data.getlines(part=1)
    field_width, field_height, total = len(gear_data[0]), len(gear_data), 0

    def guard(num: int) -> int:
        return max(0, min(field_width - 1, num))

    def has_symbol(s: str) -> bool:
        return len(re.findall(r"[^\d.]", s)) > 0

    for i, line in enumerate(gear_data):
        for ni in re.finditer(r"\d+", line):
            n, pos_left, pos_right = (
                ni.group(),
                ni.start(),
                ni.start() + len(ni.group()),
            )
            checks = [
                line[guard(pos_left - 1) : pos_left],
                line[pos_right : guard(pos_right + 1)],
            ]
            if i > 0:
                checks.append(gear_data[i - 1][guard(pos_left - 1) : guard(pos_right + 1)])
            if i < field_height - 1:
                checks.append(gear_data[i + 1][guard(pos_left - 1) : guard(pos_right + 1)])
            if any(has_symbol(c) for c in checks):
                total += int(n)
    print(total)


def part2(data: data) -> None:
    gear_data = data.getlines(part=2)
    total = 0
    for i, line in enumerate(gear_data):
        for ni in re.finditer(r"\*", line):
            pos = ni.start()
            lines = [
                gear_data[i - 1] if i > 0 else "",
                line,
                gear_data[i + 1] if i < len(gear_data) else "",
            ]
            numbers = chain(*[list(re.finditer(r"\d+", line)) for line in lines])
            adj = [int(n.group()) for n in numbers if pos - len(n.group()) <= n.start() <= pos + 1]
            if len(adj) == 2:
                total += adj[0] * adj[1]
    print(total)

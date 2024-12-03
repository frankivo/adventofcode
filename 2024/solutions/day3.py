# --- Day 3: Mull It Over ---
# https://adventofcode.com/2024/day/3

from data import data
import re


def part1(data: data) -> None:
    matches = re.findall(r"(mul\((\d+),(\d+)\))", data.get(part=1))
    muls = [int(m[1]) * int(m[2]) for m in matches]
    print(sum(muls))


def part2(data: data) -> None:
    matches = re.match(r"(mul\((\d+),(\d+)\))", data.get(part=2))
    print(matches)

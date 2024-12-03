# --- Day 3: Mull It Over ---
# https://adventofcode.com/2024/day/3

from data import data
import re


def part1(data: data) -> None:
    matches = re.findall(r"(mul\((\d+),(\d+)\))", data.get())
    muls = [int(m[1]) * int(m[2]) for m in matches]
    print(sum(muls))

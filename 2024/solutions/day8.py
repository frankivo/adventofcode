# --- Day 8: Resonant Collinearity ---
# https://adventofcode.com/2024/day/

from data import data
import re


def part1(data: data) -> None:
    print(parse(data.getlines()))


def part2(data: data) -> None:
    print(2)


def parse(input: list[str]) -> dict:
    antennas = {}
    for y, line in enumerate(input):
        ants = list(re.finditer(r"[\da-zA-Z]", line))
        for a in ants:
            antennas.update({a[0]: [*antennas.get(a[0], []), (y, a.start())]})
    return antennas

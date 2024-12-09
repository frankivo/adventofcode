# --- Day 8: Resonant Collinearity ---
# https://adventofcode.com/2024/day/

from data import data
import itertools
import re


def part1(data: data) -> None:
    antennas, width, height = parse(data.getlines())

    def antinode(a, b):
        dx = a[0] - b[0]
        dy = a[1] - b[1]
        return (b[0] - dx, b[1] - dy)

    def valid(an):
        return 0 <= an[0] <= width and 0 <= an[1] <= height

    anti_nodes = []
    for _, antennas in antennas.items():
        for a, b in list(itertools.combinations(antennas, 2)):
            anti_nodes = anti_nodes + [an for an in [antinode(a, b), antinode(b, a)] if valid(an)]

    print(len(set(anti_nodes)))


def part2(data: data) -> None:
    print(2)


def parse(input: list[str]) -> dict:
    antennas = {}
    width = len(input[0]) - 1
    height = len(input) - 1

    for y, line in enumerate(input):
        ants = list(re.finditer(r"[\da-zA-Z]", line))
        for a in ants:
            antennas.update({a[0]: [*antennas.get(a[0], []), (a.start(), y)]})
    return antennas, width, height

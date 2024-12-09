# --- Day 8: Resonant Collinearity ---
# https://adventofcode.com/2024/day/

from data import data
import itertools
import re


def part1(data: data) -> None:
    print(solve(data, t_freq=False))


def part2(data: data) -> None:
    print(solve(data, t_freq=True))


def solve(data: data, t_freq: bool = False) -> int:
    antennas, width, height = parse(data.getlines())

    def is_valid(an):
        return 0 <= an[0] <= width and 0 <= an[1] <= height

    def calc_antinodes(a, b):
        dx = a[0] - b[0]
        dy = a[1] - b[1]

        valid = True
        i = 0 if t_freq else 1
        while valid:
            an = (b[0] - (dx * i), b[1] - (dy * i))
            i += 1
            valid = is_valid(an)
            if valid:
                yield an
            if not t_freq:
                break

    anti_nodes = set()
    for _, antennas in antennas.items():
        for a, b in list(itertools.combinations(antennas, 2)):
            anti_nodes |= set(calc_antinodes(a, b)) | set(calc_antinodes(b, a))

    return len(anti_nodes)


def parse(input: list[str]) -> dict:
    antennas = {}
    width = len(input[0]) - 1
    height = len(input) - 1

    for y, line in enumerate(input):
        ants = list(re.finditer(r"[\da-zA-Z]", line))
        for a in ants:
            antennas.update({a[0]: [*antennas.get(a[0], []), (a.start(), y)]})
    return antennas, width, height

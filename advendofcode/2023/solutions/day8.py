# --- Day 8: Haunted Wasteland ---
# https://adventofcode.com/2023/day/8

from data import data
from math import lcm
from typing import Tuple
import re


def part1(data: data) -> None:
    instructions, maze = parse(data.getlines())
    print(solve(instructions, maze, "AAA"))


def part2(data: data) -> None:
    instructions, maze = parse(data.getlines())
    locs = [m for m in maze.keys() if m[-1] == "A"]
    solved = [solve(instructions, maze, loc) for loc in locs]
    print(lcm(*solved))


def solve(instructions: str, maze: dict, start: str) -> int:
    step, found, loc = 0, False, start
    while not found:
        inst = instructions[step % len(instructions)]
        loc = maze[loc]["left"] if inst == "L" else maze[loc]["right"]
        step += 1
        if loc[-1] == "Z":
            return step


def parse(data: list[str]) -> Tuple[str, dict]:
    instructions, *elements = data
    combos = [re.search(r"(.+) = \((.+), (.+)\)", e).groups() for e in elements]
    maze = {c[0]: {"left": c[1], "right": c[2]} for c in combos}
    return instructions, maze

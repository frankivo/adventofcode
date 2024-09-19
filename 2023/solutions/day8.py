# --- Day 8: Haunted Wasteland ---
# https://adventofcode.com/2023/day/8

from data import data
import re


def part1(data: data) -> None:
    instructions, *elements = data.getlines(part=1)

    combos = [re.search(r"(.+) = \((.+), (.+)\)", e).groups() for e in elements]
    maze = {c[0]: {"left": c[1], "right": c[2]} for c in combos}

    step, found, loc = 0, False, "AAA"
    while not found:
        inst = instructions[step % len(instructions)]
        loc = maze[loc]["left"] if inst == "L" else maze[loc]["right"]
        step += 1
        if loc == "ZZZ":
            break
    print(step)


def part2(data: data) -> None:
    pass

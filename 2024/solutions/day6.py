# --- Day 6: Guard Gallivant ---
# https://adventofcode.com/2024/day/6

from data import data
import re


directions = ["up", "right", "down", "left"]


def part1(data: data) -> None:
    position, blocks, width, height = parse(data)
    direction = directions[0]
    in_grid = True
    visited = set()

    while in_grid:
        visited.add(position)
        next = None
        while not next:
            tmp = next_pos(position, direction)
            if tmp in blocks:
                direction = next_direction(direction)
            else:
                next = tmp
        position = next
        in_grid = 0 <= position[0] <= width - 1 and 0 <= position[1] <= height - 1

    print(len(visited))


def parse(data: data):
    input = data.getlines()
    start, blocks, width, height = None, [], len(input[0]), len(input)

    for y, line in enumerate(input):
        for item in re.finditer(r"#|\^", line):
            pos = (item.start(), y)
            if item[0] == "#":
                blocks.append(pos)
            else:
                start = pos

    return start, blocks, width, height


def next_pos(pos, direction):
    if direction == "up":
        return (pos[0], pos[1] - 1)
    if direction == "down":
        return (pos[0], pos[1] + 1)
    if direction == "left":
        return (pos[0] - 1, pos[1])
    if direction == "right":
        return (pos[0] + 1, pos[1])


def next_direction(direction: str) -> str:
    next_index = (directions.index(direction) + 1) % len(directions)
    return directions[next_index]

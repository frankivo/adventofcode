# --- Day 6: Guard Gallivant ---
# https://adventofcode.com/2024/day/6

from data import data
import re


directions = ["up", "right", "down", "left"]


def part1(data: data) -> None:
    print(len(walk(data.getlines())[0]))


def part2(data: data) -> None:
    input = data.getlines()

    original_path, _ = walk(input)
    walks = [walk(input, pos)[1] for pos in original_path]
    print(sum(walks))


def parse(input: list[str]):
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


def walk(input: list[str], extra_block=None):
    position, blocks, width, height = parse(input)
    blocks = [*blocks, extra_block]

    direction = directions[0]
    in_grid, is_loop = True, False
    visited = set()

    while in_grid and not is_loop:
        node = (position, direction)

        if node in visited:
            is_loop = True
            continue

        visited.add(node)
        next = None
        while not next:
            tmp = next_pos(position, direction)
            if tmp in blocks:
                direction = next_direction(direction)
            else:
                next = tmp
        position = next
        in_grid = 0 <= position[0] <= width - 1 and 0 <= position[1] <= height - 1

    visited_pos = set([v[0] for v in visited])
    return visited_pos, is_loop

# --- Day 4: Ceres Search ---
# https://adventofcode.com/2024/day/4

from data import data


def part1(data: data) -> None:
    starting_points = []  # Starting points (X,Y)
    grid = {}  # (X,Y) -> Char

    for row, line in enumerate(data.getlines()):
        for col, char in enumerate(line):
            grid.update({(col, row): char})
            if char == "X":
                starting_points.append((col, row))

    def get(x, y):
        try:
            return grid[(x, y)]
        except KeyError:
            return "."

    directions = [
        (1, 0),  # Right
        (-1, 0),  # Left
        (0, -1),  # Up
        (0, 1),  # Down
        (1, 1),  # Downright
        (1, -1),  # Upright
        (-1, -1),  # Upleft
        (-1, 1),  # Downleft
    ]

    xmas_count = 0
    for sp in starting_points:
        x, y = sp
        for dx, dy in directions:
            if get(x + dx, y + dy) == "M" and get(x + 2 * dx, y + 2 * dy) == "A" and get(x + 3 * dx, y + 3 * dy) == "S":
                xmas_count += 1

    print(xmas_count)


def part2(data: data) -> None:
    print(2)

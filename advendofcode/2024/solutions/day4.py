# --- Day 4: Ceres Search ---
# https://adventofcode.com/2024/day/4

from data import data


def part1(data: data) -> None:
    grid, starting_points = parse(data, "X")
    directions = [(x, y) for x in range(-1, 2) for y in range(-1, 2)]

    xmas_count = 0
    for sp in starting_points:
        x, y = sp
        for dx, dy in directions:
            if (
                get(grid, x + dx, y + dy) == "M"
                and get(grid, x + 2 * dx, y + 2 * dy) == "A"
                and get(grid, x + 3 * dx, y + 3 * dy) == "S"
            ):
                xmas_count += 1
    print(xmas_count)


def part2(data: data) -> None:
    grid, starting_points = parse(data, "A")

    def g(x, y):
        return get(grid, x, y)

    xmas_count = 0
    for sp in starting_points:
        x, y = sp
        # Both M on top
        if g(x - 1, y - 1) == "M" == g(x + 1, y - 1) and g(x - 1, y + 1) == "S" == g(x + 1, y + 1):
            xmas_count += 1
        # Both M on bottom
        if g(x - 1, y + 1) == "M" == g(x + 1, y + 1) and g(x - 1, y - 1) == "S" == g(x + 1, y - 1):
            xmas_count += 1
        # Both M on left
        if g(x - 1, y - 1) == "M" == g(x - 1, y + 1) and g(x + 1, y - 1) == "S" == g(x + 1, y + 1):
            xmas_count += 1
        # Both M on right
        if g(x + 1, y - 1) == "M" == g(x + 1, y + 1) and g(x - 1, y - 1) == "S" == g(x - 1, y + 1):
            xmas_count += 1
    print(xmas_count)


def parse(data: data, start_char: str):
    starting_points = []  # Starting points (X,Y)
    grid = {}  # (X,Y) -> Char

    for row, line in enumerate(data.getlines()):
        for col, char in enumerate(line):
            grid.update({(col, row): char})
            if char == start_char:
                starting_points.append((col, row))

    return grid, starting_points


def get(grid, x, y):
    try:
        return grid[(x, y)]
    except KeyError:
        return "."

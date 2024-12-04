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

    xmas_count = 0
    for sp in starting_points:
        x, y = sp
        if get(x + 1, y) == "M" and get(x + 2, y) == "A" and get(x + 3, y) == "S":  # Right
            print(sp, "right")
            xmas_count += 1
        if get(x - 1, y) == "M" and get(x - 2, y) == "A" and get(x - 3, y) == "S":  # Left
            print(sp, "left")
            xmas_count += 1
        if get(x, y - 1) == "M" and get(x, y - 2) == "A" and get(x, y - 3) == "S":  # Up
            print(sp, "up")
            xmas_count += 1
        if get(x, y + 1) == "M" and get(x, y + 2) == "A" and get(x, y + 3) == "S":  # Down
            print(sp, "down")
            xmas_count += 1
        if get(x + 1, y + 1) == "M" and get(x + 2, y + 2) == "A" and get(x + 3, y + 3) == "S":  # Downright
            print(sp, "downright")
            xmas_count += 1
        if get(x + 1, y - 1) == "M" and get(x + 2, y - 2) == "A" and get(x + 3, y - 3) == "S":  # Upright
            print(sp, "upright")
            xmas_count += 1
        if get(x - 1, y - 1) == "M" and get(x - 2, y - 2) == "A" and get(x - 3, y - 3) == "S":  # Upleft
            print(sp, "upleft")
            xmas_count += 1
        if get(x - 1, y + 1) == "M" and get(x - 2, y + 2) == "A" and get(x - 3, y + 3) == "S":  # Downleft
            print(sp, "downleft")
            xmas_count += 1

    print(xmas_count)


def part2(data: data) -> None:
    print(2)

# --- Day 5: Print Queue ---
# https://adventofcode.com/2024/day/5

from data import data


def part1(data: data) -> None:
    x, y = parse(data)
    print(x)
    print(y)


def part2(data: data) -> None:
    print(2)


def parse(data: data):
    page_order, updates = {}, []

    for line in data.getlines():
        if "|" in line:
            source, target = list(map(int, line.split("|")))
            old = page_order.get(source, [])
            page_order.update({source: [*old, target]})
        elif "," in line:
            updates.append(list(map(int, line.split(","))))

    return page_order, updates

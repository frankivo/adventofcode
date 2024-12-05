# --- Day 5: Print Queue ---
# https://adventofcode.com/2024/day/5

from data import data


def part1(data: data) -> None:
    page_order, updates = parse(data)
    middle_sum = 0

    for u in updates:
        ok = True
        for i, p in enumerate(u):
            if set(u[0:i]) & set(page_order.get(p, [])):
                ok = False
        if ok:
            middle_sum += u[int(len(u) / 2)]
    print(middle_sum)


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

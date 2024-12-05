# --- Day 5: Print Queue ---
# https://adventofcode.com/2024/day/5

from data import data


def part1(data: data) -> None:
    page_order, updates = parse(data)

    valid_updates = [u for u in updates if is_valid(u, page_order)]
    middle_pages = [u[int(len(u) / 2)] for u in valid_updates]

    print(sum(middle_pages))


def part2(data: data) -> None:
    page_order, updates = parse(data)

    invalid_updates = [u for u in updates if not is_valid(u, page_order)]
    print(invalid_updates)


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


def is_valid(update: list[int], page_order: dict) -> bool:
    for i, p in enumerate(update):
        if len(set(update[0:i]) & set(page_order.get(p, []))):
            return False
    return True

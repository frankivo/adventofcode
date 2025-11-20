# --- Day 5: Print Queue ---
# https://adventofcode.com/2024/day/5

from data import data
import functools


def part1(data: data) -> None:
    page_order, updates = parse(data)
    valid_updates = [u for u in updates if is_valid(u, page_order)]
    print(sum_middles(valid_updates))


def part2(data: data) -> None:
    page_order, updates = parse(data)

    def compare(x, y):
        return -1 if y in page_order.get(x, []) else 1

    invalid_updates = [u for u in updates if not is_valid(u, page_order)]
    fixed_updates = [sorted(u, key=functools.cmp_to_key(compare)) for u in invalid_updates]
    print(sum_middles(fixed_updates))


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


def sum_middles(updates: list[list[int]]) -> int:
    return sum([u[int(len(u) / 2)] for u in updates])

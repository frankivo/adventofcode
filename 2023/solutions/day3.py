# --- Day 3: Gear Ratios ---
# https://adventofcode.com/2023/day/3

import re

def part1(input: list[str]) -> None:
    field_width, field_height, found = len(input[0]), len(input), []

    def guard(num: int, limit: int) -> int:
        return max(0, min(limit - 1, num))

    def has_symbol(s: str) -> bool:
        return len(re.findall(r"[^\d.]", s)) > 0

    for i, line in enumerate(input):
        for ni in re.finditer(r"\d+", line):
            n, pos_left, pos_right = ni.group(), ni.start(), ni.start() + len(ni.group())
            checks = [
                line[guard(pos_left - 1, field_width): pos_left],
                line[pos_right: guard(pos_right + 1, field_width)],
                input[i - 1][guard(pos_left - 1, field_width): guard(pos_right + 1, field_width)] if i > 0 else "",
                input[i + 1][guard(pos_left - 1, field_width): guard(pos_right + 1, field_width)] if i < field_height - 1 else ""
            ]
            if any(has_symbol(c) for c in checks):
                found.append(int(n))

    print(sum(found))

def part2(input: list[str]) -> None:
    pass
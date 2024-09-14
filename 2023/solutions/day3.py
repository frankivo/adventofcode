# --- Day 3: Gear Ratios ---
# https://adventofcode.com/2023/day/3

import re

def part1(input: list[str]) -> None:
    field_width = len(input[0])
    field_height = len(input)

    found = []

    for i in range(field_height):
        line = input[i]
        numbers = re.finditer(r"\d+", line)

        for ni in numbers:
            n = ni.group()
            pos_left = ni.start()
            pos_right = pos_left + len(n)
            found_symbol = False

            # Left
            if has_symbol(line[guard_left(pos_left - 1) : pos_left]): 
                found_symbol = True
            # Right
            if has_symbol(line[pos_right : guard_right(pos_right + 1, field_width)]):
                found_symbol = True
            # Above
            if i > 0:
                prev_line = input[i - 1]
                if has_symbol(prev_line[guard_left(pos_left - 1) : guard_right(pos_right + 1, field_width)]):
                    found_symbol = True
            # Under
            if i < field_height - 1:
                next_line = input[i + 1]
                if has_symbol(next_line[guard_left(pos_left - 1) : guard_right(pos_right + 1, field_width)]):
                    found_symbol = True
            if found_symbol:
                found.append(int(n))
    print(sum(found))

def guard_left(num: int) -> int:
    return max(0, num)

def guard_right(num: int, width: int) -> int:
    return min(width - 1, num)

def has_symbol(input: str) -> bool:
    return len(re.findall(r"[^\d.]", input)) > 0

def part2(input: list[str]) -> None:
    pass
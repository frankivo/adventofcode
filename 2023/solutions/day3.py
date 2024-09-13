# --- Day 3: Gear Ratios ---
# https://adventofcode.com/2023/day/3

import re

def part1(input: list[str]) -> None:
    field_width = len(input[0])
    field_height = len(input)

    found = []

    for i in range(field_height):
        line = input[i]
        numbers = re.findall(r"\d+", line)

        for n in numbers:
            pos_left = line.index(n)
            pos_right = pos_left + len(n)

            found_symbol = False

            # Left
            if pos_left > 0 and has_symbol(line[pos_left - 1 : pos_left]): 
                found_symbol = True
            # Right
            if pos_right < field_width and has_symbol(line[pos_right : pos_right + 1]):
                found_symbol = True
            # Above
            if i > 0:
                prev_line = input[i - 1]
                if has_symbol(prev_line[pos_left - 1 : pos_right + 1]):
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
    return min(width, num)

def has_symbol(data: str) -> bool:
    return len(re.findall(r"[^\d.]", data)) > 0

def part2(input: list[str]) -> None:
    pass
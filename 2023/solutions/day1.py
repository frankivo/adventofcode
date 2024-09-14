# --- Day 1: Trebuchet?! ---
# https://adventofcode.com/2023/day/1

import re

def part1(data: list[str]) -> None:
    digits = [re.findall("\d", i) for i in input]
    print(sum([int(d[0] + d[-1]) for d in digits]))

def part2(data: list[str]) -> None:
    search = "(?=(\d|" + "|".join(one_to_nine) + "))"
    digits = [re.findall(search, i) for i in input]
    numbers = [[to_number(j) for j in i] for i in digits]
    print(sum([int(str(d[0]) + str(d[-1])) for d in numbers]))

def to_number(digit: str) -> int:
    return digit if digit.isdigit() else one_to_nine.index(digit) + 1

one_to_nine = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]
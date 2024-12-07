# --- Day 7: Bridge Repair ---
# https://adventofcode.com/2024/day/7

from data import data
import re


def part1(data: data) -> None:
    cali_res = 0
    for test_value, operators in parse(data.getlines()):
        res = solve(operators, target=test_value)
        if res:
            cali_res += test_value
    print(cali_res)


def part2(data: data) -> None:
    cali_res = 0
    for test_value, operators in parse(data.getlines()):
        res = solve(operators, target=test_value, concat=True)
        if res:
            cali_res += test_value
    print(cali_res)


def parse(input: list[str]) -> iter:
    for line in input:
        nums = list(map(int, re.findall(r"\d+", line)))
        yield nums[0], nums[1:]


def solve(numbers: list[int], target: int, res: int = 0, depth: int = 0, concat=False) -> bool:
    if depth == len(numbers):
        return res == target

    if depth == 0:
        return solve(numbers, target, numbers[depth], depth + 1, concat)
    elif depth < len(numbers):
        tmp = solve(numbers, target, res + numbers[depth], depth + 1, concat)
        if not tmp:
            tmp = solve(numbers, target, res * numbers[depth], depth + 1, concat)
        if concat and not tmp:
            tmp = solve(numbers, target, int(f"{res}{numbers[depth]}"), depth + 1, concat)
        return tmp

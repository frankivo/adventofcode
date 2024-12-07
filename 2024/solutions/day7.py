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
        res = solve(operators, target=test_value)
        if res:
            cali_res += test_value
    print(cali_res)


def parse(input: list[str]) -> iter:
    for line in input:
        test_value, operators_raw = line.split(":")
        yield int(test_value), list(map(int, operators_raw.strip().split(" ")))


def solve(numbers: list[int], target: int, res: int = 0, depth: int = 0, results=None) -> bool:
    # print(target, depth, res)
    if results is None:
        results = []
    calc = True
    if res > target or depth >= len(numbers):
        results.append(res == target)
        calc = False

    if calc and depth == 0:
        solve(numbers, target, numbers[depth], depth + 1, results)
    elif calc and depth < len(numbers):
        solve(numbers, target, res + numbers[depth], depth + 1, results)
        solve(numbers, target, res * numbers[depth], depth + 1, results)

    return sum(results)

# --- Day 7: Bridge Repair ---
# https://adventofcode.com/2024/day/7

from data import data
import re


def part1(data: data) -> None:
    cali_res = 0
    for test_value, operators in parse(data.getlines()):
        for op in options(operators):
            res = left_to_right_solver(op, target=test_value)
            if res == test_value:
                cali_res += test_value
                break
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
    if results is None:
        results = []
    if res > target or depth > len(numbers):
        results.append(res == target)
    print(results)
    return sum(results)


def options(operators: list[int], depth=0, cur="", result=None, concat=False) -> list[str]:
    if result is None:
        result = []

    if depth == len(operators):
        result.append(cur)
        return result

    if depth == 0:
        options(operators, depth + 1, str(operators[depth]), result, concat)
    else:
        nd = depth + 1
        options(operators, nd, f"{cur}*{operators[depth]}", result, concat)
        options(operators, nd, f"{cur}+{operators[depth]}", result, concat)
        if concat:
            options(operators, nd, f"{cur}{operators[depth]}", result, concat)
    return result


def left_to_right_solver(expr: str, target: int) -> int:
    sum = 0
    add = True
    for m in re.findall(r"(\d+|[\+\*])", expr):
        if m == "+":
            add = True
        elif m == "*":
            add = False
        elif not add:
            sum = sum * int(m)
        else:
            sum += int(m)

        if sum > target:
            return -1

    return sum

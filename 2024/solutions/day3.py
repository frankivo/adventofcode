# --- Day 3: Mull It Over ---
# https://adventofcode.com/2024/day/3

from data import data
import re


def part1(data: data) -> None:
    matches = re.findall(r"(mul\((\d+),(\d+)\))", data.get(part=1))
    muls = [int(m[1]) * int(m[2]) for m in matches]
    print(sum(muls))


def part2(data: data) -> None:
    instructions = list(re.findall(r"(do\(\)|don't\(\)|(mul\((\d+),(\d+)\)))", data.get(part=2)))
    mul_enabled = True
    muls = 0

    for inst in instructions:
        if inst[0] == "don't()":
            mul_enabled = False
        elif inst[0] == "do()":
            mul_enabled = True
        elif mul_enabled:
            muls += int(inst[2]) * int(inst[3])

    print(muls)

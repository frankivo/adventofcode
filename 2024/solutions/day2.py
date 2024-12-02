# --- Day 2: Red-Nosed Reports ---
# https://adventofcode.com/2024/day/2

from data import data
import re


def part1(data: data) -> None:
    safeCount = 0
    for l in data.getlines():
        report = re.findall(r"\d+", l)
        if is_safe(report):
            safeCount += 1
    print(safeCount)


def is_safe(report: list) -> bool:
    safe = True
    increasing = None

    for i in range(len(report) + 1):
        slice = list(map(int, report[i : i + 2]))

        if len(slice) != 2:  # Ignore edge
            continue
        if increasing == None:  # Determine direction
            increasing = slice[1] > slice[0]
        else:  # Check direction
            if increasing and slice[1] < slice[0]:
                safe = False
            if not increasing and slice[1] > slice[0]:
                safe = False
        if not 1 <= abs(slice[0] - slice[1]) <= 3:  # Check steps
            safe = False
    return safe

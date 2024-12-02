# --- Day 2: Red-Nosed Reports ---
# https://adventofcode.com/2024/day/2

from data import data
import re


def part1(data: data) -> None:
    safeCount = 0
    for l in data.getlines():
        report = re.findall(r"\d+", l)
        safe = True
        increasing = None

        for i in range(len(report) + 1):
            slice = report[i : i + 2]

            if len(slice) != 2:  # Ignore edge
                continue
            if increasing == None:  # Determine direction
                increasing = slice[1] > slice[0]
            elif increasing and slice[1] < slice[0]:  # Check direction
                safe = False
            if not 1 <= abs(int(slice[0]) - int(slice[1])) <= 3:  # Check steps
                safe = False

        if safe:
            safeCount += 1

    print(safeCount)

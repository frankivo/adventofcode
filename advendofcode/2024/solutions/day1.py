# --- Day 1: Historian Hysteria ---
# https://adventofcode.com/2024/day/1

from collections import Counter
from data import data
from typing import Tuple
import re


def part1(data: data) -> None:
    dist = 0
    left, right = get_locations(data)

    for row in zip(sorted(left), sorted(right)):
        dist += abs(row[1] - row[0])
    print(dist)


def part2(data: data) -> None:
    left, right = get_locations(data)
    countL, countR = Counter(left), Counter(right)

    similarity = 0
    for n, c in countL.items():
        similarity += n * c * countR.get(n, 0)
    print(similarity)


def get_locations(data: data) -> Tuple[list, list]:
    nums = re.findall(r"\d+", data.get())
    left, right = [], []
    for i, n in enumerate(nums):
        lst = left if i % 2 == 0 else right
        lst.append(int(n))
    return (left, right)

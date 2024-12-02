# --- Day 1: Historian Hysteria ---
# https://adventofcode.com/2024/day/1

from data import data
import re


def part1(data: data) -> None:
    nums = re.findall(r"\d+", data.get())
    left, right = [], []
    for i, n in enumerate(nums):
        lst = left if i % 2 == 0 else right
        lst.append(int(n))
    
    loc = list(zip(sorted(left), sorted(right)))
    dist = 0
    for row in loc:
        dist += abs(row[1] - row[0])
    
    print(dist)

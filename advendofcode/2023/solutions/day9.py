# --- Day 9: Mirage Maintenance ---
# https://adventofcode.com/2023/day/9

from data import data


def part1(data: data) -> None:
    res = 0
    for line in data.getlines():
        nums = [int(n) for n in line.split()]
        while sum(nums):
            res += nums[-1]
            nums = [nums[i + 1] - nums[i] for i in range(len(nums) - 1)]
    print(res)


def part2(data: data) -> None:
    res = 0
    for line in data.getlines():
        nums = [int(n) for n in line.split()]
        i = 0
        while sum(nums):
            res += nums[0] * (1 if i % 2 == 0 else -1)
            nums = [nums[i + 1] - nums[i] for i in range(len(nums) - 1)]
            i += 1

    print(res)

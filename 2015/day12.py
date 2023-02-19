from helper import getInput
import re

input = getInput(12)[0]

def part1() -> int:
    nums = map(int,re.findall('[\d-]+', input))
    return sum(nums)

print(part1())
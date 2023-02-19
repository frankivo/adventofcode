from helper import getInput
import re

input = getInput(12)[0]

def part1() -> int:
    nums = list(map(int,re.findall('[\d-]+', input)))
    print(sum(nums))
    return 0

print(part1())
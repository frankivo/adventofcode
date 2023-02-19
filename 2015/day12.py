from helper import getInput
import json
import re

input = getInput(12)[0]

def part1() -> int:
    nums = map(int,re.findall('[\d-]+', input))
    return sum(nums)

def part2() -> int:
    data = json.loads(input)
    if 'red' in data.values() and type(data) == dict:
        print('yes')


print(part1())
print(part2())
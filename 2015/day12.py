from helper import getInput
import json
import re

input = getInput(12)[0]

def part1() -> int:
    nums = map(int,re.findall('[\d-]+', input))
    return sum(nums)

def count(obj) -> int:
    sum = 0
    
    if type(obj) == dict and 'red' not in obj.values():
        for _, v in obj.items():
            if type(v) in (dict, list):
                for o in v:
                    sum += count(o)
            elif type(v) == int:
                sum += v
    if type(obj) == list:
        for v in obj:
            if type(v) == int:
                sum += v
    elif type(obj) == int:
        sum += obj

    return sum

def part2() -> int:
    data = json.loads(input)
    return count(data)

print(part1())
print(part2())
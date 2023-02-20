from helper import getInput
from json import loads
from re import findall

input = getInput(12)[0]

def part1() -> int:
    nums = map(int,findall('[\d-]+', input))
    return sum(nums)

def count(obj) -> int:
    total = 0

    if type(obj) == dict and 'red' not in obj.values():
            for v in obj.values():
                if type(v) in (dict, list):
                    total += sum(count(o) for o in v)
                else:
                    total += count(v)
    elif type(obj) == list:
        total += sum(count(o) for o in obj)
    elif type(obj) == int:
        total += obj
    
    return total

def part2() -> int:
    data = loads(input)
    return count(data)

print(part1())
print(part2()) # 57225 too low # 93092 too high
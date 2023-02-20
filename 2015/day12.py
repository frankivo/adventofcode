from helper import getInput
from json import loads
from re import findall

input = getInput(12)[0]

def part1() -> int:
    nums = map(int,findall('[\d-]+', input))
    return sum(nums)

def count(obj) -> int:
    if type(obj) == dict and 'red' not in obj.values():
        return sum(count(o) for o in obj.values())
    elif type(obj) == list:
        return sum(count(o) for o in obj)
    elif type(obj) == int:
        return obj    
    else:
        return 0

def part2() -> int:
    data = loads(input)
    return count(data)

print(part1())
print(part2()) # 57225 too low # 93092 too high
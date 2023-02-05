from helper import getInput
import re

def literalLength() -> int:
    return sum(map(len, input))

def evalLength() -> int:
    return sum(map(len,map(eval, input)))

def part1() -> int:
    return literalLength() - evalLength()

def part2() -> int:
    total = 0
    for i in input:
        short = i[1:-1]
        special = len( re.findall('(\\\\x\d{2})', short) )
        total += 4 + short.count('\\') + special + (short.count('\"') - special)
    return total

input = getInput(8)
print(part1())
print(part2())

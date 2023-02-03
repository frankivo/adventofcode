from helper import *


def part1() -> int:
    return input.count('(') - input.count(')')

def part2() -> int:
    sum = 0
    for i, c in enumerate(input):
        sum += 1  if c == '(' else -1 
        if sum < 0:
            return i + 1
    
input = getInput(1)[0]
print("Santa floor: {0}".format(part1()))
print("First basement index: {0}".format(part2()))
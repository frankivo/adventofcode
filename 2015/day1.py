from helper import *


def part1() -> int:
    return input.count('(') - input.count(')')
    
input = getInput(1)[0]
print("Santa floor: {0}".format(part1()))
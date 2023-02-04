from helper import getInput

def literalLength() -> int:
    return sum(map(len, input))

def evalLength() -> int:
    return sum(map(len,map(eval, input)))

def part1() -> int:
    return literalLength() - evalLength()

input = getInput(8)
print(part1())
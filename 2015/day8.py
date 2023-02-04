def literalLength() -> int:
    return sum(map(len, input))

def evalLength() -> int:
    return sum(map(len,map(eval, input)))

def part1() -> int: return literalLength() - evalLength()

input = [
    '""',
    '"abc"',
    '"aaa\\"aaa"',
    '"\\x27"',
]

print(part1())
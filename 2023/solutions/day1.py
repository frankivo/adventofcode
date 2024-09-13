import re

def run(input: list[str]) -> None:
    part1(input)

def part1(input: list[str]) -> None:
    digits = [re.findall("\d", i) for i in input]
    print(sum([int(d[0] + d[-1]) for d in digits]))

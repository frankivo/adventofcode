import input
import re

def part1(input: list[str]) -> None:
    digits = [re.findall("\d", i) for i in input]
    print(sum([int(d[0] + d[-1]) for d in digits]))

def part2(input: list[str]) -> None:
    digits = [re.findall("(?=(\d|one|two|three|four|five|six|seven|eight|nine))", i) for i in input]
    numbers = [[to_number(j) for j in i] for i in digits]
    print(sum([int(str(d[0]) + str(d[-1])) for d in numbers]))

def to_number(digit: str) -> int:
    if digit.isdigit():
        return digit
    else:
        return ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"].index(digit) + 1
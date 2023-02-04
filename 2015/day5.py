
import re
from helper import getInput, slice

input = getInput(5)
badStrs = ['ab', 'cd', 'pq', 'xy']

def vowelCount3OrMore(s: str) -> int:
    return len(re.findall('([aeiou])', s)) >= 3

def charRepeat(s: str) -> bool:
    for i, c in enumerate(s):
        if i > 0 and s[i-1] == c:
            return True
    return False

def notBad(s: str) -> bool:
    for b in badStrs:
        if b in s:
            return False
    return True

def part1() -> int:
    count = 0
    for i in input:
        count += 1 if vowelCount3OrMore(i) and charRepeat(i) and notBad(i) else 0
    return count

def repeatPair(s: str) -> bool:
    slices = slice(s.strip())
    for i, sa in enumerate(slices):
        for j, sb in enumerate(slices):
            if i == j or i == j + 1 or i + 1 == j:
                continue
            if sa == sb:
                return True

    return False

def charRepeatSplit(s: str) -> bool:
    for i, c in enumerate(s):
        if i > 1 and s[i-2] == c:
            return True
    return False

def part2() -> int:
    count = 0
    for i in input:
        count += 1 if repeatPair(i) and charRepeatSplit(i) else 0
    return count

print('Amount of nice strings: {0}'.format(part1()))
print('Amount of nice strings: {0}'.format(part2()))

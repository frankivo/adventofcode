
import re
from helper import getInput

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

print('Amount of nice strings: {0}'.format(part1()))


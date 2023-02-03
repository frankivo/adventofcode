
import re

input = ['ugknbfddgicrmopn', 'aaa', 'jchzalrnumimnmhp', 'haegwjzuvuyypxyu', 'dvszwmarrgswjxmb']

def vowelCount(s: str) -> int:
    return len(re.findall('([aeiou])', s))

for i in input:
    print(i)
    print(vowelCount(i))


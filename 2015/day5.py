
import re

input = ['ugknbfddgicrmopn', 'aaa', 'jchzalrnumimnmhp', 'haegwjzuvuyypxyu', 'dvszwmarrgswjxmb']

def vowelCount(s: str) -> int:
    return len(re.findall('([aeiou])', s))

def charRepeat(s: str) -> bool:
    for i, c in enumerate(s):
        if i > 0 and s[i-1] == c:
            return True
    return False

for i in input:
    print(i)
    print(vowelCount(i))
    print(charRepeat(i))
    print("-----")


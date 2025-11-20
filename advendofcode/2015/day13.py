from helper import getInput
from itertools import permutations 

input = getInput(13)

data = []
names = set()

for i in input:
    split = i[:-1].split()
    data.append({'name1': split[0], 'state': split[2], 'score': int(split[3]), 'name2': split[-1]})
    names.add(split[0])

def getScore(n1: str, n2: str) -> int:
    for d in data:
        if d['name1'] == n1 and d['name2'] == n2:
            return d['score'] * (1 if d['state'] == 'gain' else -1)

def totalscore(seating: list) -> int:
    return \
        sum([getScore(n1, seating[i-1]) for i, n1 in enumerate(seating)]) + \
        sum([getScore(n1, seating[(i + 1) % len(seating)]) for i, n1 in enumerate(seating)])

def part1() -> int:
    return max([totalscore(list(n)) for n in permutations(names, len(names))])

def part2() -> int:
    names.add('me')

    for name in names:
        data.append({'name1': 'me', 'state': 'gain', 'score': 0, 'name2': name})
        data.append({'name1': name, 'state': 'gain', 'score': 0, 'name2': 'me'})
    
    return max([totalscore(list(n)) for n in permutations(names, len(names))])

print(part1())
print(part2())

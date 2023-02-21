from helper import getInput
from itertools import permutations 

input = getInput(13)

data = []
names = set()

for i in input:
    split = i[:-1].split()
    data.append({'name1': split[0], 'state': split[2], 'score': int(split[3]), 'name2': split[-1]})
    names.add(split[0])

def getScore(dd: list, n1: str, n2: str) -> int:
    for d in dd:
        if d['name1'] == n1 and d['name2'] == n2:
            return d['score'] * (1 if d['state'] == 'gain' else -1)

def totalscore(dd: list, seating: list) -> int:
    return \
        sum([getScore(dd, n1, seating[i-1]) for i, n1 in enumerate(seating)]) + \
        sum([getScore(dd, n1, seating[(i + 1) % len(seating)]) for i, n1 in enumerate(seating)])

def part1() -> int:
    return max([totalscore(data, list(n)) for n in permutations(names, len(names))])

def part2() -> int:
    dataWithMe = data.copy()
    namesWithMe = names.copy()
    namesWithMe.add('me')

    for name in names:
        dataWithMe.append({'name1': 'me', 'state': 'gain', 'score': 0, 'name2': name})
        dataWithMe.append({'name1': name, 'state': 'gain', 'score': 0, 'name2': 'me'})
    
    best = 0
    bestNames = []
    for n in permutations(namesWithMe, len(namesWithMe)):
        score = totalscore(dataWithMe, list(n))
        if score > best:
            best = score
            bestNames = n
    print(best)
    print(type(bestNames))
    bestNames = list(bestNames)
    bestNames.remove('me')
    print(bestNames)
    print(totalscore(data, bestNames))
    return 'x'

print(part1())
print(part2())
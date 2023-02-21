from helper import getInput
from itertools import permutations 

input = getInput(13)

data = []
names = set()

for i in input:
    split = i[:-1].split()
    data.append({
        'name1': split[0],
        'state': split[2],
        'score': int(split[3]),
        'name2': split[-1]
    })
    names.add(split[0])

def getScore(n1: str, n2: str) -> int:
    for d in data:
        if d['name1'] == n1 and d['name2'] == n2:
            return d['score'] * 1 if d['state'] == 'gain' else -1

def totalscore(seating: list) -> int:
    return sum([getScore(n1, list(seating)[i-1]) for i, n1 in enumerate(seating)])

def bestScore() -> int:
    return max([totalscore(n) for n in permutations(names,len(names))])

print(bestScore())
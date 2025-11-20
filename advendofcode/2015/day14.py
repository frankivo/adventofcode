from helper import getInput
from re import findall

maxSeconds = 2503

reindeer = {}
for r in getInput(14):
    name = r.split()[0]
    nums = list(map(int, findall('\d+', r)))
    reindeer.update({ name: {'speed': nums[0], 'duration': nums[1], 'rest': nums[2] }})

def distance(name: str) -> int:
    dist, seconds, modeSeconds, mode = 0, 0, 0, 'move'
    data = reindeer[name]

    while seconds < maxSeconds:
        if mode == 'move' and modeSeconds == data['duration']:
            modeSeconds = 0
            mode = 'rest'
        if mode == 'rest' and modeSeconds == data['rest']:
            modeSeconds = 0
            mode = 'move'
        if mode == 'move':
            dist += data['speed']
        seconds += 1
        modeSeconds += 1
    return dist

def part1() -> int:
    return max([distance(r) for r in reindeer])

def bestScoring(status: dict) -> list:
    bestDist = 0
    bestNames = []

    for name, state in status.items():
        if state['dist'] > bestDist:
            bestNames = []
            bestDist = state['dist']

        if state['dist'] == bestDist:
            bestNames.append(name)
    return bestNames

def alldistance() -> dict:
    empty = {'modeSeconds': 0, 'mode': 'move', 'dist': 0, 'score': 0}
    status = {}
    seconds = 0

    while seconds < maxSeconds:
        seconds += 1

        for r in reindeer:
            data = reindeer[r]
            rs = status[r] if r in status else empty.copy()

            rs.update({'modeSeconds': rs['modeSeconds'] + 1 })
            if rs['mode'] == 'move':
                rs.update({'dist': rs['dist'] + data['speed'] })
            if rs['mode'] == 'move' and rs['modeSeconds'] == data['duration']:
                rs.update({'modeSeconds': 0 })
                rs.update({'mode': 'rest' })
            if rs['mode'] == 'rest' and rs['modeSeconds'] == data['rest']:
                rs.update({'modeSeconds': 0 })
                rs.update({'mode': 'move' })
            status.update({r : rs})

        for name in bestScoring(status):            
            rs = status[name]
            rs.update({'score': rs['score'] + 1})

    return status
    
def part2() -> int:
    res = alldistance()
    return max([r['score'] for r in res.values()])

print(part1())
print(part2()) 

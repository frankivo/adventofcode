from functools import reduce
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

    while seconds <= maxSeconds:
        if mode == 'move':
            dist += data['speed']

        seconds += 1
        modeSeconds += 1

        if mode == 'move' and modeSeconds == data['duration']:
            modeSeconds = 0
            mode = 'rest'
        if mode == 'rest' and modeSeconds == data['rest']:
            modeSeconds = 0
            mode = 'move'        
    return dist

def part1() -> int:
    return max([distance(r) for r in reindeer])

def bestScoring(status: dict) -> str:
    return reduce(lambda x, y: x if status[x]['dist'] > status[y]['dist'] else y, status)

def alldistance() -> dict:
    empty = {'modeSeconds': 0, 'mode': 'move', 'dist': 0, 'score': 0}
    status = {}
    seconds = 0

    while seconds <= maxSeconds:
        
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
        rs = status[bestScoring(status)]
        rs.update({'score': rs['score'] + 1})
        seconds += 1

    return status
    
# 1074 too low
# 1168 too high
def part2() -> int:
    res = alldistance()
    print([r['score'] for r in res.values()])

    return 0

print(part1())
print(part2()) 

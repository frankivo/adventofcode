from helper import getInput
from re import findall

reindeer = {}
for r in getInput(14):
    name = r.split()[0]
    nums = list(map(int, findall('\d+', r)))
    reindeer.update({ name: {'speed': nums[0], 'duration': nums[1], 'rest': nums[2] }})

def distance(name: str) -> int:
    dist, seconds, modeSeconds, mode = 0, 0, 0, 'move'
    data = reindeer[name]

    while seconds < 1000:
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

def alldistance():
    empty = {'modeSeconds': 0, 'mode': 'move', 'dist': 0}
    status = {}
    seconds = 0

    while seconds < 140:
        seconds += 1
        for r in reindeer:
            data = reindeer[name]
            rs = status[r] if r in status else empty.copy()
            status.update({r : rs})

    print(status)

def part2() -> int:
    alldistance()
    return 0

print(part1())
print(part2())

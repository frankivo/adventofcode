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

    while True:
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
        
        if seconds == 2503:
            break
    return dist

top = max([distance(r) for r in reindeer])
print(top)

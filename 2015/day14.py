from helper import getInput
from re import findall

reindeers = {}
for r in getInput(14):
    name = r.split()[0]
    nums = list(map(int, findall('\d+', r)))
    reindeers.update({ name: {'speed': nums[0], 'duration': nums[1], 'rest': nums[2] }})

print(reindeers)

def distance(name: str) -> int:
    dist, seconds, modeSeconds, mode = 0, 0, 0, 'move'
    data = reindeers[name]

    while True:
        if mode == 'move':
            dist += data['speed']


        seconds += 1
        modeSeconds += 1
        if seconds == 1000:
            break
    return dist

print(distance('Comet'))

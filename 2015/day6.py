from helper import getInput
import re

def part1():
    lights = set()
    for l in parse():
        mode = l[0]
        for x in range(l[1][0], l[1][2] + 1):
            for y in range(l[1][1], l[1][3] + 1):
                p = (x,y)
                if mode == 'on':
                    lights.add(p)
                elif mode == 'off':
                    if p in lights:
                        lights.remove(p)
                elif mode == 'toggle':
                    if p in lights:
                        lights.remove(p)
                    else:
                        lights.add(p)
    return len(lights)

def part2():
    lights = dict()
    for l in parse():
        mode = l[0]
        for x in range(l[1][0], l[1][2] + 1):
            for y in range(l[1][1], l[1][3] + 1):
                p = (x, y)
                cur = lights.get(p) if p in lights else 0
                if mode == 'off':
                    lights.update({p : cur - 1 if not cur - 1 < 0 else 0}) 
                else:
                    lights.update({p : cur + (1 if mode == 'on' else 2)})
    return sum(lights.values())

def parse():
    data = []
    for i in input:
        nums = list(map(int,re.findall('\d+', i)))
        if ('turn on' in i):
            mode = 'on'
        elif ('turn off' in i):
            mode = 'off'
        else:
            mode = 'toggle'
        data.append((mode, nums))
    return data

input = getInput(6)

print(part1())
print(part2())

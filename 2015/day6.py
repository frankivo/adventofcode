from helper import getInput
import re

def part1():
    lights = set()
    for l in parse():
        for x in range(l[1][0], l[1][2] + 1):
            for y in range(l[1][1], l[1][3] + 1):
                if (l[0] == 'on'):
                    lights.add((x, y))
                elif (l[0] == 'off'):
                    lights.remove((x, y))
    return len(lights)

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

input = ['turn on 0,0 through 999,999', 'turn off 499,499 through 500,500']
print(part1())

from helper import getInput
import re

def part1():
    for l in input:
        print(l)

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

input = ['turn on 0,0 through 999,999', 'toggle 0,0 through 999,0', 'turn off 499,499 through 500,500']
print(parse())
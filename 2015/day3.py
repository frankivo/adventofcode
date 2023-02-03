from helper import getInput

def part1() -> int:
    visited = {(0, 0)}
    x = y = 0

    for i in input:
        if i == '^':
            x += 1
        elif i == 'v':
            x -= 1
        elif i == '<':
            y -= 1
        elif i == '>':
            y += 1
        visited.add((x, y))

    return len(visited)

input = getInput(3)[0]
print('Houses with at least one present: {0}'.format(part1()))

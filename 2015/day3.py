from helper import getInput

def part1() -> int:
    visited = {(0, 0)}
    x = y = 0

    for c in input:
        if c == '^':
            x += 1
        elif c == 'v':
            x -= 1
        elif c == '<':
            y -= 1
        elif c == '>':
            y += 1
        visited.add((x, y))

    return len(visited)

def part2() -> int:
    visited = {(0, 0)}
    x1 = x2 = y1 = y2 = 0

    for i, c in enumerate(input):
        if i % 2 == 0:
            if c == '^':
                x1 += 1
            elif c == 'v':
                x1 -= 1
            elif c == '<':
                y1 -= 1
            elif c == '>':
                y1 += 1
            visited.add((x1, y1))
        else:
            if c == '^':
                x2 += 1
            elif c == 'v':
                x2 -= 1
            elif c == '<':
                y2 -= 1
            elif c == '>':
                y2 += 1
            visited.add((x2, y2))

    return len(visited)

input = getInput(3)[0]
print('Houses with at least one present: {0}'.format(part1()))
print('Houses with robo-santa: {0}'.format(part2()))

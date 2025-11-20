from helper import getInput

def solve(robo) -> int:
    visited = {(0, 0)}
    x1 = x2 = y1 = y2 = 0

    for i, c in enumerate(input):
        if not robo or i % 2 == 0:
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
print('Houses with at least one present: {0}'.format(solve(False)))
print('Houses with robo-santa: {0}'.format(solve(True)))

from helper import getInput

def part1() -> int:
    total = 0
    for i in input():
        total += (2 * i[0] * i[1]) + (2 * i[0] * i[2]) + (2 * i[1] * i[2]) + (i[0] * i[1])
    return total

def part2() -> int:
    total = 0
    for i in input():
        total += (2 * i[0]) + (2 * i[1]) + (i[0] * i[1] * i[2])
    return total

def input():
    data = []
    for r in getInput(2):
        data += r.strip().split("x")
    data = list(map(int, data)) # Cast
    data = [data[i:i+3] for i in range(0, len(data), 3)] # Grouped
    data = list(map(sorted, data)) # Sort
    return data

print("Wrapping pare square feet: {0}".format(part1()))
print("Feet of ribbon: {0}".format(part2()))
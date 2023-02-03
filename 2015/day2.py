from helper import getInput

def part1() -> int:
    return 0

def input():
    data = []
    for r in getInput(2):
        data += r.strip().split("x")
    data = list(map(int, data))
    return [data[i:i+3] for i in range(0, len(data), 3)]

print("Wrapping pare square feet: {0}".format(part1()))
print(input())
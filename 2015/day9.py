from helper import getInput

input = getInput(9)
cities = dict()

def addCity(city1, city2, dist):
    target = cities[city1] if city1 in cities else dict()
    target.update({city2: int(dist)})
    cities.update({city1: target})

for i in input:
    split = i.split(' ')
    source, to, dist = split[0], split[2], split[4]

    addCity(source, to, dist)
    addCity(to, source, dist)

def scan(city: str, visited: list, dist: int) -> int:
    visited.append(city)

    options = []
    for c, d in cities[city].items():
        if c not in visited:
            options.append( scan(c, visited.copy(), dist + d))
    return min(options) if options else dist

def part1() -> int:
    return min([scan(c, [], 0) for c in cities])

print(part1())
    
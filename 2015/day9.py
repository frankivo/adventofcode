from helper import getInput

input = getInput(9)
cities = dict()
for i in input:
    split = i.split(' ')
    source, to, dist = split[0], split[2], split[4]

    target = cities[source] if source in cities else dict()
    target.update({to: int(dist)})
    cities.update({source: target})

def bfs(start: str) -> int:
    explorable = [start]
    distances = {}

    while explorable:
        current = explorable[0]
        print(explorable[0])
        explorable.remove(current)
        if current in cities:
            for adj in cities[current]:
                dist = cities[current][adj]

                if adj not in distances or distances[adj] > dist:
                    distances.update({adj: dist})
                explorable.append(adj)
    return sum(distances.values())

x = [bfs(c) for c in cities]
print(x)

print(cities)
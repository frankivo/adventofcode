from helper import getInput

input = getInput(9)
cities = dict()
for i in input:
    split = i.split(' ')
    source, to, dist = split[0], split[2], split[4]

    target = cities[source] if source in cities else dict()
    target.update({to: dist})
    cities.update({source: target})

def bfs(start: str) -> int:
    explorable = [start]
    distances = []

    while explorable:
        current = explorable[0]
        print(explorable[0])
        explorable.remove(current)
        if current in cities:
            for adj in cities[current]:
                if adj not in distances:
                    dist= cities[current][adj]
                    distances.append({adj: dist})
                    explorable.append(adj)
    print(distances)

bfs('London')

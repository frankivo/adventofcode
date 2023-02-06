from helper import getInput

input = getInput(9)
cities = dict()
for i in input:
    split = i.split(' ')
    source, to, dist = split[0] , split[2] , split[4]

    target = cities[source] if source in cities else []
    target.append({to: dist})
    cities.update({source: target})

print(cities)
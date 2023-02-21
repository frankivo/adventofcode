from helper import getInput

input = getInput(13)

data = []
names = set()

for i in input:
    split = i[:-1].split()
    data.append({
        'name1': split[0],
        'state': split[2],
        'amount': split[3],
        'name2': split[-1]
    })
    names.add(split[0])

print(data)
print(names)
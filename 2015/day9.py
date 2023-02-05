from helper import getInput

input = getInput(9)
data = []
for i in input:
    s = i.split(' ')
    data.append({'from': s[0], 'to': s[2], 'distance': int(s[4])})
print(data)
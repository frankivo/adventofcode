import re

input = '''123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i'''.split("\n")

signals = dict()
for i in input:
    match = re.findall('[A-Z]+', i)
    cmd = match[0] if len(match) else 'PROVIDE'
    target = i.split()[-1]

    if cmd == 'PROVIDE':
        signals.update({target: i.split()[0]})

print(signals)


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
    values = i.split()
    target = values[-1]

    if cmd == 'PROVIDE':
        value = int(values[0])
    elif cmd == 'NOT':
        source = values[1]
        value = signals.get(source) ^ 65535
    else:
        value = -1

    signals.update({target: value})

print(signals)

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
    value = -1
    values = i.split()
    target = values[-1]

    if cmd == 'PROVIDE':
        value = int(values[0])
    elif cmd == 'NOT':
        value = signals.get(values[1]) ^ 65535
    elif cmd == 'AND':
        value = signals.get(values[0]) & signals.get(values[2])
    elif cmd == 'OR':
        value = signals.get(values[0]) | signals.get(values[2])
    elif cmd == 'LSHIFT':
        value = signals.get(values[0]) << 2
    elif cmd == 'RSHIFT':
        value = signals.get(values[0]) >> 2

    signals.update({target: value})

print(signals)
import re
from helper import getInput

input = getInput(7)

signals = dict()
for i in input:
    match = re.findall('[A-Z]+', i)
    cmd = match[0] if len(match) else 'PROVIDE'
    value = -1
    values = i.split()
    target = values[-1]

    print(target)

    if cmd == 'PROVIDE':
        value = int(values[0])
    elif cmd == 'NOT':
        value = signals.get(values[1]) ^ 65535
    elif cmd == 'AND':
        value = signals.get(values[0]) & signals.get(values[2])
    elif cmd == 'OR':
        value = signals.get(values[0]) | signals.get(values[2])
    elif cmd == 'LSHIFT':
        value = signals.get(values[0]) << int(values[2])
    elif cmd == 'RSHIFT':
        value = signals.get(values[0]) >> int(values[2])

    signals.update({target: value})

print(signals)
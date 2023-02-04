import re
from helper import getInput

def solve(signals: dict) -> int:
    while 'a' not in signals:
        for i in input:
            match = re.findall('[A-Z]+', i)
            cmd = match[0] if len(match) else 'PROVIDE'
            value = None
            values = i.split()
            target = values[-1]

            try:
                if cmd == 'PROVIDE':
                    if values[0].isdigit():
                        value = int(values[0])
                    elif values[0] in signals:
                        value = signals.get(values[0]) 
                elif cmd == 'NOT':
                    value = signals.get(values[1]) ^ 65535
                elif cmd == 'AND':
                    source = values[0]
                    left = int(source) if source.isdigit() else signals.get(source)
                    value = left & signals.get(values[2])
                elif cmd == 'OR':
                    value = signals.get(values[0]) | signals.get(values[2])
                elif cmd == 'LSHIFT':
                    value = signals.get(values[0]) << int(values[2])
                elif cmd == 'RSHIFT':
                    value = signals.get(values[0]) >> int(values[2])
            except: continue

            if value is not None and target not in signals:
                signals.update({target: value})
    return signals.get('a')

input = getInput(7)
part1A = solve(dict())
print(part1A)
part2A = solve(dict({'b': part1A}))
print(part2A)

import re
from helper import getInput

input = getInput(7)

signals = dict()

while 'a' not in signals:
    for i in input:
        match = re.findall('[A-Z]+', i)
        cmd = match[0] if len(match) else 'PROVIDE'
        value = -1
        values = i.split()
        target = values[-1]

        try:
            if cmd == 'PROVIDE':
                try: value = int(values[0])
                except: 
                    try:
                        if values[0] in signals:
                            value = signals.get(values[0]) 
                        else: raise
                    except: raise
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

            signals.update({target: value})
        except: continue

print(signals.get('a'))

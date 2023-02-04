import time
import re
from helper import getInput

input = getInput(7)

signals = dict()

while 'a' not in signals:
    # print(signals)
    print(len(signals))
    # time.sleep(2)

    for i in input:
        match = re.findall('[A-Z]+', i)
        cmd = match[0] if len(match) else 'PROVIDE'
        value = -1
        values = i.split()
        target = values[-1]

        try:
            if cmd == 'PROVIDE':
                value = int(values[0])
            elif cmd == 'NOT':
                value = signals.get(values[1]) ^ 65535
            elif cmd == 'AND':
                try: left = int(values[0])
                except: left = signals.get(values[0])
                value = left & signals.get(values[2])
            elif cmd == 'OR':
                value = signals.get(values[0]) | signals.get(values[2])
            elif cmd == 'LSHIFT':
                value = signals.get(values[0]) << int(values[2])
            elif cmd == 'RSHIFT':
                value = signals.get(values[0]) >> int(values[2])

            signals.update({target: value})
        except Exception as e: 
            print(e)
            continue

print(signals)
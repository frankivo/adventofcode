from hashlib import md5

def part1() -> int:
    i = 0
    while True:
        hash = md5((input + str(i)).encode()).hexdigest()
        if hash[0:5] == '00000':
            return i
        i += 1

input = 'ckczppom'
print('Hash: {0}'.format(part1()))
from hashlib import md5

def solve(zeroes: int) -> int:
    i = 0
    while True:
        hash = md5((input + str(i)).encode()).hexdigest()
        if hash[0:zeroes] == ('0' * zeroes):
            return i
        i += 1

input = 'ckczppom'
print('Hash: {0}'.format(solve(5)))
print('Hash: {0}'.format(solve(6)))

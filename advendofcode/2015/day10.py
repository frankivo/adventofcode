from helper import getInput

input = getInput(10)[0]

def lookAndSay(oldStr: str) -> str:
    newStr = ''
    curChar = ''
    curCount = 1

    for c in oldStr:
        if c == curChar:
            curCount += 1
        else:
            if curChar != '':
                newStr += '{0}{1}'.format(curCount, curChar)
            curChar = c
            curCount = 1
    newStr += '{0}{1}'.format(curCount, curChar)

    return newStr

def solve(repeat: int) -> int:
    x = input
    for _ in range(repeat):
        x = lookAndSay(x)
    return len(x)

print(solve(40))
print(solve(50))

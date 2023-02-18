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

x = input
for i in range(40):
    print(x)
    x = lookAndSay(x)
print(len(x))

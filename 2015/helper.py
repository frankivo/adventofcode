import os
import requests

def getInput(day: int):
    filename = 'input/day{0}'.format(day)

    if (not os.path.exists(filename)):
        download(day, filename)
    
    return open(filename).readlines()

def download(day: int, target: str):
    if not os.path.exists('input'):
        os.mkdir('input')

    url = 'https://adventofcode.com/2015/day/{0}/input'.format(day)
    print("Download: " + url)
    headers = { 'User-Agent': 'https://github.com/frankivo/adventofcode frank+github@scriptzone.nl' }
    cookies = { 'session': os.environ["AOC_COOKIE"] }

    response = requests.get(url, headers=headers, cookies = cookies)
    if (response.status_code != 200):
        raise Exception("Download failed: " + response.status_code)
    open(target, 'wb').write(response.content)

def slice(str: str) -> list:
    return [str[i:i+2] for i in range(0, len(str), 1)]

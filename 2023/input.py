from dotenv import dotenv_values
import duckdb
import requests

def createTable() -> None: 
    with duckdb.connect('aoc2023.db') as con:
        con.sql('create table if not exists input_data ( day int, input text, demo text)')
        con.table('input_data').show()

def downloadDay(day: int) -> str:
    headers = { 'User-Agent': 'https://github.com/frankivo/adventofcode frank+github@scriptzone.nl' }
    cookies = { 'session': dotenv_values('.env')['API_KEY'] }

    url = 'https://adventofcode.com/2023/day/{0}/input'.format(day)
    with requests.get(url, headers=headers, cookies = cookies) as response:
        if (response.status_code != 200):
            raise Exception('Download failed: ' + str(response.status_code) + ' ' + url)
        return response.text
    
def storeDay(day: int, data: str) -> None:
    with duckdb.connect('aoc2023.db') as con:
        query = "insert into input_data (day, input) values({d}, '{i}')".format(d=day, i=data)
        con.sql(query)


createTable()
for day in range(1, 25+1):
    print('Download day: {d}'.format(d=day))
    storeDay(day, downloadDay(day))

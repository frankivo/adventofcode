from dotenv import dotenv_values
import duckdb
import requests

def createTable() -> None: 
    with duckdb.connect('aoc2020.db') as con:
        with open('create_table.sql') as f:
            con.sql(f.read())
            con.table('input_data').show()

def downloadDay(day: int) -> str:
    headers = { 'User-Agent': 'https://github.com/frankivo/adventofcode frank+github@scriptzone.nl' }
    cookies = { 'session': dotenv_values('.env')['API_KEY'] }

    url = 'https://adventofcode.com/2020/day/{0}/input'.format(day)
    with requests.get(url, headers=headers, cookies = cookies) as response:
        if (response.status_code != 200):
            raise Exception('Download failed: ' + response.status_code)
        return response.text
    
def storeDay(day: int, data: str) -> None:
    with duckdb.connect('aoc2020.db') as con:
        query = "insert into input_data (day, input) values({d}, '{i}')".format(d=day, i=data)
        con.sql(query)


createTable()
for day in range(1, 25+1):
    print('Download day: {d}'.format(d=day))
    storeDay(day, downloadDay(day))

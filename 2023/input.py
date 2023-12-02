from dotenv import dotenv_values
import duckdb
import requests

def createTable() -> None: 
    with duckdb.connect('aoc2023.db') as con:
        con.sql('create table if not exists input_data ( day int, input text, demo text)')

def downloadDay(day: int) -> None:
    headers = { 'User-Agent': 'https://github.com/frankivo/adventofcode frank+github@scriptzone.nl' }
    cookies = { 'session': dotenv_values('.env')['API_KEY'] }

    url = 'https://adventofcode.com/2023/day/{0}/input'.format(day)
    with requests.get(url, headers=headers, cookies = cookies) as response:
        if (response.status_code != 200):
            raise Exception('Download failed: ' + str(response.status_code) + ' ' + url)
        storeDay(day, response.text)

def hasDay(day: int) -> bool:
    createTable()

    with duckdb.connect('aoc2023.db') as con:
        query = "select 1 from input_data where day = {d}".format(d=day)
        res = con.sql(query)
        return not res.fetchone() == None
    
def storeDay(day: int, data: str) -> None:
    with duckdb.connect('aoc2023.db') as con:
        query = "insert into input_data (day, input) values({d}, '{i}')".format(d=day, i=data)
        con.sql(query)

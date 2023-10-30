import argparse
import duckdb
from os import path

parser = argparse.ArgumentParser(description='Run Advent of Code solutions')
parser.add_argument('-d', '--day', type=int, help='Day to run')
parser.add_argument('--demo', action=argparse.BooleanOptionalAction, help='Use demo data', default=False)

args = parser.parse_args()

if args.day is None:
    parser.print_help()
    exit()

con = duckdb.connect('aoc2020.db')

def update_demo() -> None:
    if args.demo:
        with open(path.join('demo', 'day{d}.txt'.format(d=args.day))) as f:
            query = "update input_data set demo = '{i}' where day = {d}".format(d=args.day, i=f.read())
            con.sql(query)

def run() -> None:
    with open(path.join('solutions', 'day{d}.sql'.format(d=args.day))) as f:
        source_column = 'input' if not args.demo else 'demo'
        query = f.read().replace('source_column', source_column)
        con.sql(query).show()

update_demo()
run()
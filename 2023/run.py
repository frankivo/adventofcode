from input import hasDay, downloadDay
from os import path
import argparse
import duckdb

parser = argparse.ArgumentParser(description='Run Advent of Code solutions')
parser.add_argument('-d', '--day', type=int, help='Day to run')
parser.add_argument('--demo', action=argparse.BooleanOptionalAction, help='Use demo data', default=False)

args = parser.parse_args()

if args.day is None:
    parser.print_help()
    exit()

con = duckdb.connect('aoc2023.db')

def prepare() -> None:
    if not hasDay(args.day):
        downloadDay(args.day)

def update_demo() -> None:
    if args.demo:
        with open(path.join('demo', 'day{d}.txt'.format(d=args.day))) as f:
            query = "update input_data set demo = '{i}' where day = {d}".format(d=args.day, i=f.read())
            con.sql(query)

def run() -> None:
    with open(path.join('solutions', 'day{d}.sql'.format(d=args.day))) as reader:
        source_column = 'input' if not args.demo else 'demo'
        query = reader.read().replace('source_column', source_column)
        con.sql(query).show()

def main():
    prepare()
    update_demo()
    run()

if __name__ == '__main__':
    main()

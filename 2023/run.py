from input import input
from solutions import *
import argparse

parser = argparse.ArgumentParser(description='Run Advent of Code solutions')
parser.add_argument('-d', '--day', type=int, help='Day to run', required=True)
parser.add_argument('--demo', action=argparse.BooleanOptionalAction, help='Use demo data', default=False)

args = parser.parse_args()

def run(data: list[str]) -> None:
    globals()[f"day{args.day}"].run()

def main():
    data = input(1, args.demo).get()
    run(data)

if __name__ == '__main__':
    main()

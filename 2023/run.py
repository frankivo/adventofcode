from data import data
from solutions import *
import argparse

parser = argparse.ArgumentParser(description="Run Advent of Code solutions")
parser.add_argument("-d", "--day", type=int, help="Day to run", required=True)
parser.add_argument("--demo", action=argparse.BooleanOptionalAction, help="Use demo data", default=False)
parser.add_argument("--p1", "-1", action=argparse.BooleanOptionalAction, help="Run only part 1", default=False)
parser.add_argument("--p2", "-2", action=argparse.BooleanOptionalAction, help="Run only part 2", default=False)

args = parser.parse_args()


def main():
    game_data = data(day=args.day, demo=args.demo)

    if not args.p2:
        globals()[f"day{args.day}"].part1(game_data)
    if not args.p1:
        globals()[f"day{args.day}"].part2(game_data)


if __name__ == "__main__":
    main()

# --- Day 5: If You Give A Seed A Fertilizer ---
# https://adventofcode.com/2023/day/5

from data import data
import re


def part1(data: data) -> None:
    seed_data = data.getlines(part=1, with_empty=True)

    seeds = get_seeds(seed_data[0])
    print(get_minimum_location(seed_data, seeds))


def part2(data: data) -> None:
    seed_data = data.getlines(part=2, with_empty=True)

    def generate_seeds(ranges: list[(int, int)]) -> iter:
        for start, count in ranges:
            for i in range(count):
                yield start + i

    raw = get_seeds(seed_data[0])
    ranges = [(raw[s], raw[s + 1]) for s in range(0, len(raw), 2)]
    seeds = generate_seeds(ranges)

    print(get_minimum_location(seed_data, seeds))


def get_minimum_location(data: list[str], seeds: iter) -> int:
    maps = [
        list(get_data(data, "seed-to-soil")),
        list(get_data(data, "soil-to-fertilizer")),
        list(get_data(data, "fertilizer-to-water")),
        list(get_data(data, "water-to-light")),
        list(get_data(data, "light-to-temperature")),
        list(get_data(data, "temperature-to-humidity")),
        list(get_data(data, "humidity-to-location")),
    ]

    location = None
    for nr in seeds:
        for m in maps:
            nr = get(m, nr)
        if not location or nr < location:
            location = nr
    return location


def get_seeds(data: str) -> list[int]:
    return [int(i) for i in re.findall(r"\d+", data)]


def get_data_from_file(data: list[str], header: str) -> iter:
    found = False
    for line in data:
        if header in line:
            found = True
            continue
        if found and not len(line.strip()):
            break
        if found:
            yield [int(i) for i in re.findall(r"\d+", line)]


def get_data(data: list[str], header: str) -> iter:
    for d in get_data_from_file(data, header):
        dest_range_start, source_range_start, range_length = d

        yield {
            "dest_range_start": dest_range_start,
            "source_range_start": source_range_start,
            "range_length": range_length,
        }


def get(data: list, search: int) -> int:
    for d in data:
        if d["source_range_start"] <= search <= d["source_range_start"] + d["range_length"]:
            offset = search - d["source_range_start"]
            return d["dest_range_start"] + offset

    return search

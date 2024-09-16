# --- Day 5: If You Give A Seed A Fertilizer ---
# https://adventofcode.com/2023/day/5

from sys import maxsize as int_maxsize
import re


def part1(data: list[str]) -> None:
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

    def run():
        seed_to_soil = list(get_data(data, "seed-to-soil"))
        soil_to_fertilizer = list(get_data(data, "soil-to-fertilizer"))
        fertilizer_to_water = list(get_data(data, "fertilizer-to-water"))
        water_to_light = list(get_data(data, "water-to-light"))
        light_to_temperature = list(get_data(data, "light-to-temperature"))
        temperature_to_humidity = list(get_data(data, "temperature-to-humidity"))
        humidity_to_location = list(get_data(data, "humidity-to-location"))

        location = int_maxsize

        for seed in get_seeds(data[0]):
            soil = get(seed_to_soil, seed)
            fert = get(soil_to_fertilizer, soil)
            water = get(fertilizer_to_water, fert)
            light = get(water_to_light, water)
            temp = get(light_to_temperature, light)
            hum = get(temperature_to_humidity, temp)
            loc = get(humidity_to_location, hum)
            if loc < location:
                location = loc
        return location

    print(run())


def part2(data: list[str]) -> None:
    pass

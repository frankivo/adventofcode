# --- Day 5: If You Give A Seed A Fertilizer ---
# https://adventofcode.com/2023/day/5

import re


def part1(data: list[str]) -> None:
    seeds = get_seeds(data[0])
    seed_to_soil = dict(get_data(data, "seed-to-soil"))
    soils = [get(seed_to_soil, i) for i in seeds]
    soil_to_fertilizer = dict(get_data(data, "soil-to-fertilizer"))
    fertilizers = [get(soil_to_fertilizer, i) for i in soils]
    fertilizer_to_water = dict(get_data(data, "fertilizer-to-water"))
    waters = [get(fertilizer_to_water, i) for i in fertilizers]
    water_to_light = dict(get_data(data, "water-to-light"))
    lights = [get(water_to_light, i) for i in waters]
    light_to_temperature = dict(get_data(data, "light-to-temperature"))
    temperatures = [get(light_to_temperature, i) for i in lights]
    temperature_to_humidity = dict(get_data(data, "temperature-to-humidity"))
    humidities = [get(temperature_to_humidity, i) for i in temperatures]
    humidity_to_location = dict(get_data(data, "humidity-to-location"))
    locations = [get(humidity_to_location, i) for i in humidities]
    print(min(locations))


def part2(data: list[str]) -> None:
    pass


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
        for i in range(range_length):
            yield source_range_start + i, dest_range_start + i


def get(m: list, k: int) -> int:
    return m.get(k, k)

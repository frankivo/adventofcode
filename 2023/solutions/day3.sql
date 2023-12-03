-- AOC2023 - Day 3: Cube Conundrum
-- https://adventofcode.com/2023/day/3

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 3
),

parser as (
    select
        row_number() over (order by 1) as row,
        src,
        regexp_extract_all(src, '\d+') as numbers,
        unnest(numbers)                as number,
        strpos(src, number)            as pos_left,
        pos_left + length(number) - 1  as pos_right
    from src_data
)

-- Part 1: What is the sum of all of the part numbers in the engine schematic?
select * from parser

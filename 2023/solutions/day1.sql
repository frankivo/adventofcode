-- AOC2023 - Day 1: Trebuchet?!
-- https://adventofcode.com/2023/day/1

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 1
),

calibration_values as (
    select
        regexp_extract_all(src, '\d')                                as numbers,
        cast(concat(list_first(numbers), list_last(numbers)) as int) as cv
    from src_data
)

select
    1       as part,
    sum(cv) as result
from calibration_values

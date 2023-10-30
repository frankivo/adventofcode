-- AOC2020 - Day 8: Handheld Halting
-- https://adventofcode.com/2020/day/8

-- Part 2: What is the value of the accumulator after the program terminates?
-- Inspiration:
--   https://gitlab.com/autra/adventofcode/-/tree/master/year_2020/day8
--   https://github.com/xocolatl/advent-of-code/blob/master/2020/dec08.sql

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 8
),

instructions as (
    select
        row_number() over (order by 1) as rn,
        split(src, ' ')                as exp,
        exp[1]                         as operation,
        cast(exp[2] as int)            as argument
    from src_data
)

select
    operation,
    count(*) as count
from instructions
group by operation

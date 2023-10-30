-- AOC2020 - Day 8: Handheld Halting
-- https://adventofcode.com/2020/day/8

-- Part 1: Run your copy of the boot code. Immediately before any instruction is executed a second time, what value is in the accumulator?

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 8
),

instructions as (
    select
        split(src, ' ')     as exp,
        exp[1]              as operation,
        cast(exp[2] as int) as argument
    from src_data
)

select * from instructions

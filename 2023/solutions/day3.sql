-- AOC2023 - Day 3: Cube Conundrum
-- https://adventofcode.com/2023/day/3

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 3
),

parser as (
    select
        lag(src) over (order by 1)                       as l1,
        src                                              as l2,
        lead(src) over (order by 1)                      as l3,
        regexp_extract_all(src, '\d+')                   as numbers,
        cast(unnest(numbers) as int)                     as number,
        strpos(src, number)                              as pos_left,
        pos_left + length(number) - 1                    as pos_right,
        pos_left - 1                                     as search_left,
        length(number) + 2                               as search_length,
        substring(l1, search_left, search_length)        as prev,
        substring(l2, search_left, search_length)        as cur,
        substring(l3, search_left, search_length)        as next,
        list_has_any(split(prev, ''), split('*+#$', '')) as prev_valid,
        list_has_any(split(cur, ''), split('*+#$', ''))  as cur_valid,
        list_has_any(split(next, ''), split('*+#$', '')) as next_valid,
        prev_valid or cur_valid or next_valid            as valid
    from src_data
)

-- Part 1: What is the sum of all of the part numbers in the engine schematic?
select
    1           as part,
    sum(number) as result
from parser
where valid

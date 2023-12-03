-- AOC2023 - Day 3: Cube Conundrum
-- https://adventofcode.com/2023/day/3

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 3
),

-- Part 1: What is the sum of all of the part numbers in the engine schematic?
part1 as (
    with parser as (
        select
            lag(src) over (order by 1)                                    as l1,
            src                                                           as l2,
            lead(src) over (order by 1)                                   as l3,
            regexp_extract_all(src, '\d+')                                as numbers,
            cast(unnest(numbers) as int)                                  as number,
            concat('(^|[^0-9])', number, '([^0-9]|$)')                    as search_string,
            length(list_first(string_split_regex(l2, search_string))) + 2 as pos_left,
            pos_left - 1                                                  as search_left,
            length(number) + 2                                            as search_length,
            substring(l1, search_left, search_length)                     as prev,
            substring(l2, search_left, search_length)                     as cur,
            substring(l3, search_left, search_length)                     as next,
            regexp_matches(prev, '[^0-9.]')                               as prev_valid,
            regexp_matches(cur, '[^0-9.]')                                as cur_valid,
            regexp_matches(next, '[^0-9.]')                               as next_valid,
            prev_valid or cur_valid or next_valid                         as valid
        from src_data
    )

    select
        1           as part,
        sum(number) as result
    from parser
    where valid
)

select * from part1

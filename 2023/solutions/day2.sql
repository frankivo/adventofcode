-- AOC2023 - Day 2: Cube Conundrum
-- https://adventofcode.com/2023/day/2

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 2
),

parser as (
    select
        src,
        cast(regexp_extract(src, 'Game (\d+)', 1) as int)                              as game_id,
        list_transform(regexp_extract_all(src, '(\d+) red', 1), i -> cast(i as int))   as red,
        list_transform(regexp_extract_all(src, '(\d+) green', 1), i -> cast(i as int)) as green,
        list_transform(regexp_extract_all(src, '(\d+) blue', 1), i -> cast(i as int))  as blue
    from src_data
),

-- Part 1: What is the sum of the IDs of those games?
part1 as (
    with validator as (
        select game_id
        from parser
        where
            list_max(red) <= 12
            and list_max(green) <= 13
            and list_max(blue) <= 14
    )

    select
        1            as part,
        sum(game_id) as result
    from validator
)

select * from part1

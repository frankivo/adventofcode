-- AOC2020 - Day 1: Report Repair
-- https://adventofcode.com/2020/day/1

-- Part 1: Find the two entries that sum to 2020; what do you get if you multiply them together?
with part1 as (
    with src_data as (
        select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
        from input_data
        where day = 1
    ),

    combinations as (
        select distinct
            cast(l.src as int) as a,
            cast(r.src as int) as b
        from src_data as l
        cross join src_data as r
    ),

    summed as (
        select
            a,
            b,
            a + b as sum,
            a * b as product
        from combinations
    )

    select
        1       as part,
        product as result
    from summed
    where sum = 2020
    limit 1
)

select * from part1
-- union
-- select * from part2

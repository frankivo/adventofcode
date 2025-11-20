-- AOC2020 - Day 1: Report Repair
-- https://adventofcode.com/2020/day/1

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 1
),

ints as (select cast(src as int) as src from src_data),

-- Part 1: Find the two entries that sum to 2020; what do you get if you multiply them together?
part1 as (
    with combinations as (
        select distinct
            l.src as a,
            r.src as b
        from ints as l
        cross join ints as r
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
),

-- Part 2: In your expense report, what is the product of the three entries that sum to 2020?
part2 as (
    with combinations as (
        select distinct
            l.src as a,
            m.src as b,
            r.src as c
        from ints as l
        cross join ints as m
        cross join ints as r
    ),

    summed as (
        select
            a,
            b,
            c,
            a + b + c as sum,
            a * b * c as product
        from combinations
    )

    select
        2       as part,
        product as result
    from summed
    where sum = 2020
    limit 1
)

select * from part1
union
select * from part2
order by part

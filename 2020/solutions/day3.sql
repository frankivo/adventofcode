-- AOC2020 - Day 3: Toboggan Trajectory
-- https://adventofcode.com/2020/day/3

-- Part 1: Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
with part1 as (
    with src_data as (
        select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
        from input_data
        where day = 3
    ),

    coordinates as (
        select
            row_number() over (order by 1)                         as y,
            ((y - 1) * 3) % length(src) + 1                        as x,
            case when substring(src, x, 1) = '#' then 1 else 0 end as hit
        from src_data
    )


    select
        1        as part,
        sum(hit) as result
    from coordinates
),

-- Part 2: In your expense report, what is the product of the three entries that sum to 2020?
part2 as (
    with src_data as (
        select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
        from input_data
        where day = 3
    ),

    coordinates as (
        select
            row_number() over (order by 1)                                                                                as y,
            case when substring(src, ((y - 1)) % length(src) + 1, 1) = '#' then 1 else 0 end                              as d1r1,
            case when substring(src, ((y - 1) * 3) % length(src) + 1, 1) = '#' then 1 else 0 end                          as d1r3,
            case when substring(src, ((y - 1) * 5) % length(src) + 1, 1) = '#' then 1 else 0 end                          as d1r5,
            case when substring(src, ((y - 1) * 7) % length(src) + 1, 1) = '#' then 1 else 0 end                          as d1r7,
            case when y % 2 = 1 and substring(src, cast((y - 1) / 2 % length(src) + 1 as int), 1) = '#' then 1 else 0 end as d2r1
        from src_data
    )


    select
        2                                                         as part,
        sum(d1r1) * sum(d1r3) * sum(d1r5) * sum(d1r7) * sum(d2r1) as result
    from coordinates
)

select * from part1
union
select * from part2
order by part

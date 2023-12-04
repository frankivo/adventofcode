-- AOC2023 - Day 3: Cube Conundrum
-- https://adventofcode.com/2023/day/3

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 3
),

lead_lag as (
    select
        row_number() over (order by 1) as id,
        lag(src) over (order by 1)     as l1,
        src                            as l2,
        lead(src) over (order by 1)    as l3
    from src_data
),

-- Part 1: What is the sum of all of the part numbers in the engine schematic?
part1 as (
    with parser as (
        select
            regexp_extract_all(l2, '\d+')                                 as numbers,
            cast(unnest(numbers) as int)                                  as number,
            concat('(^|[^0-9])', number, '([^0-9]|$)')                    as search_string,
            length(list_first(string_split_regex(l2, search_string))) + 1 as search_left,
            length(number) + 2                                            as search_length,
            substring(l1, search_left, search_length)                     as prev,
            substring(l2, search_left, search_length)                     as cur,
            substring(l3, search_left, search_length)                     as next,
            regexp_matches(prev, '[^0-9.]')                               as prev_valid,
            regexp_matches(cur, '[^0-9.]')                                as cur_valid,
            regexp_matches(next, '[^0-9.]')                               as next_valid,
            prev_valid or cur_valid or next_valid                         as valid
        from lead_lag
    )

    select
        1           as part,
        sum(number) as result
    from parser
    where valid
),

part2 as (
    with stars as (
        select
            id,
            regexp_extract_all(l2, '\*')               as stars,
            range(1, length(stars) + 1)                as star_count,
            unnest(star_count)                         as star_no,
            length(string_split(l2, '*')[star_no]) + 1 as star_pos

        from lead_lag
    ),

    numbers as (
        select
            id,
            l2,
            regexp_extract_all(l2, '\d+')                                 as numbers,
            cast(unnest(numbers) as int)                                  as number,
            concat('(^|[^0-9])', number, '([^0-9]|$)')                    as search_string,
            length(list_first(string_split_regex(l2, search_string))) + 1 as pos_left,
            pos_left + length(number)                                     as pos_right
        from lead_lag
    ),

    solution as (
        select
            s.id,
            s.star_pos,
            cast(product(n.number) as int) as gear_ratio
        from stars as s
        join lateral (
            select n.number from numbers as n
            where
                n.id between s.id - 1 and s.id + 1
                and s.id between n.pos_left - 1 and n.pos_right + 1
        ) as n on true
        group by all
        having count(n.number) = 2
    )

    select
        2               as part,
        sum(gear_ratio) as result
    from solution
)

select * from part1
union
select * from part2
order by part

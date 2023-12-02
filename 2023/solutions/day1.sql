-- AOC2023 - Day 1: Trebuchet?!
-- https://adventofcode.com/2023/day/1

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 1
),

-- Part 1: What is the sum of all of the calibration values?
part1 as (
    with calibration_values as (
        select
            regexp_extract_all(src, '\d')                                as numbers,
            cast(concat(list_first(numbers), list_last(numbers)) as int) as cv
        from src_data
    )

    select
        1       as part,
        sum(cv) as result
    from calibration_values
),

-- Part 2: What is the sum of all of the calibration values?
part2 as (

    with calibration_values as (
        select
            regexp_extract_all(src, '(\d|one|two|three|four|five|six|seven|eight|nine)') as values,
            list_transform(
                values, txt -> case
                    when txt = 'one' then '1'
                    when txt = 'two' then '2'
                    when txt = 'three' then '3'
                    when txt = 'four' then '4'
                    when txt = 'five' then '5'
                    when txt = 'six' then '6'
                    when txt = 'seven' then '7'
                    when txt = 'eight' then '8'
                    when txt = 'nine' then '9'
                    else txt
                end
            )                                                                            as numbers,
            list_first(numbers)                                                          as first,
            list_last(numbers)                                                           as last,
            cast(concat(list_first(numbers), list_last(numbers)) as int)                 as cv
        from src_data
    )

    select
        2       as part,
        sum(cv) as result
    from calibration_values
)

select * from part1
union
select * from part2
order by part

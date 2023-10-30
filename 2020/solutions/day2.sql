-- AOC2020 - Day 2: Password Philosophy
-- https://adventofcode.com/2020/day/2

-- Part 1: How many passwords are valid according to their policies?
with part1 as (
    with src_data as (
        select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
        from input_data
        where day = 2
    ),

    extract as (
        select regexp_extract_all(src, '(\d+|[a-z]+)') as extracts
        from src_data
    ),

    policies as (
        select
            extracts[1]                               as low,
            extracts[2]                               as high,
            extracts[3]                               as key,
            extracts[4]                               as password,
            length(regexp_extract_all(password, key)) as keycount,
            keycount >= low and keycount <= high      as valid
        from extract
    )

    select
        1        as part,
        count(*) as result
    from policies
    where valid
),

-- Part 2: In your expense report, what is the product of the three entries that sum to 2020?
part2 as (
    with src_data as (
        select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
        from input_data
        where day = 2
    ),

    extract as (
        select regexp_extract_all(src, '(\d+|[a-z]+)') as extracts
        from src_data
    ),

    policies as (
        select
            cast(extracts[1] as int)                                       as low,
            cast(extracts[2] as int)                                       as high,
            extracts[3]                                                    as key,
            extracts[4]                                                    as password,
            substring(password, low, 1)                                    as first,
            substring(password, high, 1)                                   as last,
            (first = key and last != key) or (first != key and last = key) as valid
        from extract
    )

    select
        2        as part,
        count(*) as result
    from policies
    where valid
)

select * from part1
union
select * from part2
order by part

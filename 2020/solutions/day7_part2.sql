-- AOC2020 - Day 7: Handy Haversacks
-- https://adventofcode.com/2020/day/7

-- Part 2: How many individual bags are required inside your single shiny gold bag?

with recursive bags (amount, bag) as (
    select
        1,
        'shiny gold'

    union all

    select
        cast(regexp_extract(unnest(split(s.src, ',')), '(\d+) (.+) bag', 1) as int) * b.amount,
        regexp_extract(unnest(split(s.src, ',')), '(\d+) (.+) bag', 2)
    from src_data as s
    inner join bags as b on
        s.src like concat_ws(' ', b.bag, 'bag%')
        and s.src not like '%contain no other bags.'

),

src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 7
)

select
    2           as part,
    sum(amount) as result
from bags where bag != 'shiny gold'

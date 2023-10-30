-- AOC2020 - Day 6: Custom Customs
-- https://adventofcode.com/2020/day/6

-- Part 1: How many bag colors can eventually contain at least one shiny gold bag?

with recursive selector (rule) as (
    select format('%contain%{}bag%', regexp_extract(src, '^(.+)(bags )', 1))
    from src_data
    where src like '%contain%shiny gold bag%'

    union

    select format('%contain%{}bag%', regexp_extract(sd.src, '^(.+)(bags )', 1))

    from src_data as sd
    inner join selector as s on sd.src like s.rule
),

src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 7
)

select
    1           as part,
    count(rule) as result
from selector

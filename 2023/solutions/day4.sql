-- AOC2023 - Day 4: Scratchcards
-- https://adventofcode.com/2023/day/4

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 4
),

parser as (
    select
        split(split(src, ':')[2], '|')                          as card_info,
        cast(regexp_extract_all(card_info[1], '\d+') as int []) as winning_numbers,
        cast(regexp_extract_all(card_info[2], '\d+') as int []) as card_numbers,
        length(list_intersect(winning_numbers, card_numbers))   as winning,
        cast(case
            when winning in (0, 1) then winning
            else pow(2, winning) / 2
        end as int)                                             as score

    from src_data
)

select
    1          as part,
    sum(score) as result
from parser

-- AOC2020 - Day 7: Handy Haversacks
-- https://adventofcode.com/2020/day/7

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 7
),

-- Part 1: How many bag colors can eventually contain at least one shiny gold bag?
part1 as (
    with recursive bags (rule) as (
        select format('%contain%{}bag%', regexp_extract(src, '^(.+)(bags )', 1))
        from src_data
        where src like '%contain%shiny gold bag%'

        union

        select format('%contain%{}bag%', regexp_extract(s.src, '^(.+)(bags )', 1))

        from src_data as s
        inner join bags as b on s.src like b.rule
    )

    select
        1           as part,
        count(rule) as result
    from bags
),

-- Part 2: How many individual bags are required inside your single shiny gold bag?
part2 as (
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
    )

    select
        2           as part,
        sum(amount) as result
    from bags where bag != 'shiny gold'
)

select * from part1
union
select * from part2
order by part

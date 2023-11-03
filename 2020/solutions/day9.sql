--- Day 9: Encoding Error ---
--- https://adventofcode.com/2020/day/9

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 9
),

-- Part 1: What is the first number that does not have this property?
part1 as (
    with numbers as (
        select
            row_number() over (order by 1) as id,
            cast(src as bigint)            as num
        from src_data
    ),

    config as (select case when max(id) = 20 then 5 else 25 end as samplesize from numbers),

    validator as (
        select
            w.id,
            w.num,
            n.valid
        from numbers as w
        left join lateral
            (
                with prev as (
                    select w.num from numbers as cur
                    left join numbers as w
                        on w.id >= (cur.id - (select samplesize from config)) and cur.id > w.id
                    where cur.id = n.id
                ),

                sums as (
                    select distinct a.num + b.num as sum
                    from prev as a
                    cross join prev as b
                )

                select
                    n.id,
                    exists(select s.sum from sums as s where s.sum = w.num) as valid
                from numbers as n
            ) as n on w.id = n.id
    )

    select
        1   as part,
        num as result
    from validator
    where id > (select samplesize from config) and not valid
)

select * from part1

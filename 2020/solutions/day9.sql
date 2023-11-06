--- Day 9: Encoding Error ---
--- https://adventofcode.com/2020/day/9

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 9
),

numbers as (
    select
        row_number() over (order by 1) as id,
        cast(src as bigint)            as num
    from src_data
),

-- Part 1: What is the first number that does not have this property?
part1 as (
    with config as (select case when max(id) = 20 then 5 else 25 end as samplesize from numbers),

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
),


-- Part 2: What is the encryption weakness in your XMAS-encrypted list of numbers?
part2 as (
    with config as (select result as target from part1),

    ranges as (
        select
            n1.id as start,
            n2.id as stop
        from numbers as n1
        join numbers as n2 on n1.id < n2.id
    ),

    sums as (
        select
            r.start,
            r.stop,
            s.sum,
            s.source

        from ranges as r
        join lateral (
            select
                r.start     as id,
                sum(n.num)  as sum,
                list(n.num) as source
            from numbers as n where n.id between r.start and r.stop
        ) as s
            on
                r.start = s.id
    )

    select
        2                                   as part,
        list_min(source) + list_max(source) as result
    from sums
    where sum = (select target from config)
)

select * from part1
union
select * from part2
order by part

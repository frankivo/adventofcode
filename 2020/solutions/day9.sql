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
        cast(src as int)               as num
    from src_data
),

-- Part 1: What is the first number that does not have this property?
part1 as (
    with recursive lookback (id, num) as (
        select
            id,
            num
        from numbers where id <= 5
        union
        select
            w.id,
            w.num
        from numbers as w
        where w.id = (select max(id) + 1 from lookback)
    )

    select * from lookback
),

prev as (
    select w.num from numbers as cur
    left join numbers as w
        on w.id >= (cur.id - 5) and cur.id > w.id
    where cur.id = 6
),

sums as (
    select distinct a.num + b.num as sum
    from prev as a
    cross join prev as b
    where a.num != b.num -- TODO: remove probably
)

select
    w.*,
    exists(select s.sum from sums as s where s.sum = w.num) as valid
from numbers as w
where w.id = 6

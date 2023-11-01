--- Day 9: Encoding Error ---
--- https://adventofcode.com/2020/day/9

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 9
),

-- Part 1: What is the first number that does not have this property?
part1 as (
    with recursive with_id as (
        select
            row_number() over (order by 1) as id,
            src                            as num
        from src_data
    ),

    lookback (id, num) as (
        select
            id,
            num
        from with_id where id <= 5
        union
        select
            w.id,
            w.num
        from with_id as w
        where w.id = (select max(id) + 1 from lookback)
    )

    select * from lookback
)

select * from part1

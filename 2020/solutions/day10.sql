--- Day 10: Adapter Array ---
--- https://adventofcode.com/2020/day/10

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 10
),

-- Part 1: What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
part1 as (
    with jolts as (
        select cast(src as int) as jolt from src_data
        union
        select 0
    ),

    distances as (
        select
            jolt,
            lead(jolt, 1) over (order by jolt) as next_jolt,
            coalesce(next_jolt - jolt, 3)      as dist
        from jolts
    ),

    sums as (
        select
            dist,
            count(dist) as amount
        from distances group by dist
    )

    select
        1                            as part,
        cast(product(amount) as int) as result
    from sums
),

-- Part 2: What is the total number of distinct ways you can arrange the adapters to connect the charging outlet to your device?
part2 as (
    select 1
)

select * from part1

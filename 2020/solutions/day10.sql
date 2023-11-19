--- Day 10: Adapter Array ---
--- https://adventofcode.com/2020/day/10

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 10
),

jolts as (select cast(src as int) as jolt from src_data),

-- Part 1: What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
part1 as (
    with jolt_with_start as (
        select jolt from jolts
        union
        select 0
    ),

    distances as (
        select
            jolt,
            lead(jolt, 1) over (order by jolt) as next_jolt,
            coalesce(next_jolt - jolt, 3)      as dist
        from jolt_with_start
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
    with recursive jolts_with_target as (
        select jolt
        from jolts
        union
        select max(jolt) + 3
        from jolts
        union
        select 0
    ),

    edge as (
        select
            row_number() over (order by jolt) as id,
            jolt,
            [jolt - 1, jolt - 2, jolt - 3]    as targets
        from jolts_with_target
    ),

    paths (jolt, path) as (
        select
            jolt,
            targets
        from edge
        where id = 1

        union all

        select
            e.jolt,
            list_append(p.path, e.jolt)
        from edge as e
        join paths as p on list_has(e.targets, p.jolt)
    )

    select
        2           as part,
        count(jolt) as result
    from paths
    where jolt = (select max(jolt) from jolts_with_target)
)

select * from part2

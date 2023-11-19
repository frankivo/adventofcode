--- Day 10: Adapter Array ---
--- https://adventofcode.com/2020/day/10

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 10
),

jolts as (
    select cast(src as int) as jolt from src_data
    union
    select 0
),

-- Part 1: What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
part1 as (
    with distances as (
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
    with recursive jolts_with_start_and_end as (
        select
            jolt,
            false as is_last
        from jolts
        union
        select
            max(jolt) + 3 as jolt,
            true          as is_last
        from jolts
    ),

    jolts_with_targets as (
        select
            jolt,
            [jolt - 1, jolt - 2, jolt - 3] as targets
        from jolts_with_start_and_end
    ),

    paths (jolt, path) as (
        select
            jolt,
            targets
        from jolts_with_targets
        where jolt = 0

        union all

        select
            j.jolt,
            list_append(p.path, j.jolt)
        from jolts_with_targets as j
        join paths as p on list_has(j.targets, p.jolt)
    )

    select
        2             as part,
        count(p.jolt) as result
    from paths as p
    inner join jolts_with_start_and_end as j
        on
            p.jolt = j.jolt
            and j.is_last
)

select * from part1
union
select * from part2
order by part

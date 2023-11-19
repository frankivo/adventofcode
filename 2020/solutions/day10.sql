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

    egde as (
        select
            jolt                            as node1id,
            lead(jolt) over (order by jolt) as node2id
        from jolts_with_target
    ),

    paths (startNode, endNode, path) as (
        select
            node1id,
            node2id,
            [node1id, node2id]
        from egde where node1id = 0

        union all

        select
            paths.startNode,
            egde.node2id,
            list_append(paths.path, egde.node2id)
        from paths
        join egde on paths.endNode = egde.node1id
        -- where egde.node2id != all(paths.path)

    )

    select * from paths
)

select * from part2

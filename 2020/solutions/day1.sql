with part1 as (
    select
        1 as result,
        1 as part
),

part2 as (
    select
        1 as result,
        2 as part
)

select * from part1
union
select * from part2

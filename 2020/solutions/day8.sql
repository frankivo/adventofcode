-- AOC2020 - Day 8: Handheld Halting
-- https://adventofcode.com/2020/day/8

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 8
),

instructions as (
    select
        row_number() over (order by 1) as id,
        split(src, ' ')                as exp,
        exp[1]                         as operation,
        cast(exp[2] as int)            as argument
    from src_data
),

-- Part 1: Run your copy of the boot code. Immediately before any instruction is executed a second time, what value is in the accumulator?
part1 as (
    with recursive accumulator as (
        select * from instructions where id = 1
        union
        select i.* from instructions as i
        inner join accumulator as a on i.id = a.id + if(a.operation = 'jmp', a.argument, 1)
    )

    select
        1             as part,
        sum(argument) as result
    from accumulator where operation = 'acc'
),

-- Part 2: What is the value of the accumulator after the program terminates?
-- Inspiration:
--   https://gitlab.com/autra/adventofcode/-/tree/master/year_2020/day8
--   https://github.com/xocolatl/advent-of-code/blob/master/2020/dec08.sql
part2 as (
    select
        2 as part,
        0 as result
)

select * from part1
union
select * from part2
order by part

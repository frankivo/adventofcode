-- AOC2020 - Day 8: Handheld Halting
-- https://adventofcode.com/2020/day/8

-- Part 1: Run your copy of the boot code. Immediately before any instruction is executed a second time, what value is in the accumulator?

with recursive instructor (pos, acc, visited, operation, argument, next) as (
    select
        rn,
        0,
        [1],
        operation,
        argument,
        rn + 1
    from instructions
    where rn = 1

    union all

    select
        i1.rn,
        case when i1.operation = 'acc' then i2.acc + i1.argument else i2.acc end,
        list_append(i2.visited, i1.rn),
        i1.operation,
        i1.argument,
        case when i1.operation = 'jmp' then i1.rn + i1.argument else i1.rn + 1 end

    from instructions as i1
    inner join instructor as i2 on
        i1.rn = i2.next
        and not list_contains(i2.visited, i2.next)
),

src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 8
),

instructions as (
    select
        row_number() over (order by 1) as rn,
        split(src, ' ')                as exp,
        exp[1]                         as operation,
        cast(exp[2] as int)            as argument
    from src_data
)

select * from instructor

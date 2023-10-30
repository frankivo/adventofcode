-- AOC2020 - Day 5: Toboggan Trajectory
-- https://adventofcode.com/2020/day/5

with recursive selector (src, pos, substr, selected_rows, selected_columns) as (
    select
        src,
        0,
        '',
        all_selected_rows,
        all_selected_columns
    from seats

    union all

    select
        src,
        pos + 1,
        substring(src, pos + 1, 1) as tmp_substr,
        case
            when tmp_substr = 'F' then filter(selected_rows, i -> i < (list_min(selected_rows) + list_max(selected_rows)) / 2)
            when tmp_substr = 'B' then filter(selected_rows, i -> i > (list_min(selected_rows) + list_max(selected_rows)) / 2)
            else selected_rows
        end,
        case
            when tmp_substr = 'L' then filter(selected_columns, i -> i < (list_min(selected_columns) + list_max(selected_columns)) / 2)
            when tmp_substr = 'R' then filter(selected_columns, i -> i > (list_min(selected_columns) + list_max(selected_columns)) / 2)
            else selected_columns
        end
    from selector
    where tmp_substr != ''
),

src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 5
),

seats as (
    select
        src,
        range(128) as all_selected_rows,
        range(8)   as all_selected_columns
    from src_data
),

seat_ids as (
    select
        list_first(selected_rows)    as x,
        list_first(selected_columns) as y,
        ((x * 8) + y)                as seatID
    from selector
    where pos = 10
),

-- Part 1: As a sanity check, look through your list of boarding parses. What is the highest seat ID on a boarding pars?
part1 as (
    select
        1           as part,
        max(seatID) as result
    from seat_ids
),

-- -- Part 2: What is the ID of your seat?
part2 as (
    with seat_finder as (
        select unnest(range(min(seatID), max(seatID))) as seatID from seat_ids
        except
        select unnest(list(seatID)) from seat_ids
    )

    select
        2      as part,
        seatID as result
    from seat_finder
)

select * from part1
union
select * from part2
order by part

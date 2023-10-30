-- AOC2020 - Day 5: Toboggan Trajectory
-- https://adventofcode.com/2020/day/5

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n')) as src
    from input_data
    where day = 5
),

seats as (
    select
        src,
        range(128) as all_rows,
        range(8)   as all_columns
    from src_data
),

rs1 as (
    select
        *,
        case
            when substring(src, 1, 1) = 'F' then list_filter(all_rows, i -> i < list_min(all_rows) + list_max(all_rows) / 2)
            else list_filter(all_rows, i -> i > (list_min(all_rows) + list_max(all_rows)) / 2)
        end as rs1
    from seats
),

rs2 as (
    select
        *,
        case
            when substring(src, 2, 1) = 'F' then filter(rs1, i -> i < (list_min(rs1) + list_max(rs1)) / 2)
            else filter(rs1, i -> i > (list_min(rs1) + list_max(rs1)) / 2)
        end as rs2
    from rs1
),

rs3 as (
    select
        *,
        case
            when substring(src, 3, 1) = 'F' then filter(rs2, i -> i < (list_min(rs2) + list_max(rs2)) / 2)
            else filter(rs2, i -> i > (list_min(rs2) + list_max(rs2)) / 2)
        end as rs3
    from rs2
),

rs4 as (
    select
        *,
        case
            when substring(src, 4, 1) = 'F' then filter(rs3, i -> i < (list_min(rs3) + list_max(rs3)) / 2)
            else filter(rs3, i -> i > (list_min(rs3) + list_max(rs3)) / 2)
        end as rs4
    from rs3
),

rs5 as (
    select
        *,
        case
            when substring(src, 5, 1) = 'F' then filter(rs4, i -> i < (list_min(rs4) + list_max(rs4)) / 2)
            else filter(rs4, i -> i > (list_min(rs4) + list_max(rs4)) / 2)
        end as rs5
    from rs4
),

rs6 as (
    select
        *,
        case
            when substring(src, 6, 1) = 'F' then filter(rs5, i -> i < (list_min(rs5) + list_max(rs5)) / 2)
            else filter(rs5, i -> i > (list_min(rs5) + list_max(rs5)) / 2)
        end as rs6
    from rs5
),

rs7 as (
    select
        *,
        case
            when substring(src, 7, 1) = 'F' then list_min(rs6)
            else list_max(rs6)
        end as seat_row
    from rs6
),

cs1 as (
    select
        *,
        case
            when substring(src, 8, 1) = 'L' then filter(all_columns, i -> i < (list_min(all_columns) + list_max(all_columns)) / 2)
            else filter(all_columns, i -> i > (list_min(all_columns) + list_max(all_columns)) / 2)
        end as cs1
    from seats
),

cs2 as (
    select
        *,
        case
            when substring(src, 9, 1) = 'L' then filter(cs1, i -> i < (list_min(cs1) + list_max(cs1)) / 2)
            else filter(cs1, i -> i > (list_min(cs1) + list_max(cs1)) / 2)
        end as cs2
    from cs1
),

cs3 as (
    select
        *,
        case
            when substring(src, 10, 1) = 'L' then list_min(cs2)
            else list_max(cs2)
        end as seat_column
    from cs2
),

seat_ids as (
    select ((rs7.seat_row * 8) + cs3.seat_column) as seatID
    from rs7 left join cs3 on rs7.src = cs3.src
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

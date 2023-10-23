-- Databricks notebook source
-- MAGIC %md
-- MAGIC # AOC2020 - Day 5: Binary Boarding
-- MAGIC https://adventofcode.com/2020/day/5

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'FBFBBFFRLR
BFFFBBFRRR
FFFBBBFRRR
BBFFBBFRLL'
where year = 2020 and day = 5

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC As a sanity check, look through your list of boarding passes. What is the highest seat ID on a boarding pass?

-- COMMAND ----------


with input_data as (
  select explode(split(data, '\n')) as lines from frank.adventofcode.inputdata where year = 2020 and day = 5
  qualify rank() over (partition by year, day order by loaded desc) = 1 and lines <> ''
),

seats as (
  select
    lines,
    sequence(0, 127) as rows,
    case when substring(lines, 1, 1) = 'F'
      then filter(rows, i -> i < (array_min(rows) + array_max(rows)) / 2 )
      else filter(rows, i -> i > (array_min(rows) + array_max(rows)) / 2 )
    end as rs1, -- Step 1
    case when substring(lines, 2, 1) = 'F'
      then filter(rs1, i -> i < (array_min(rs1) + array_max(rs1)) / 2 )
      else filter(rs1, i -> i > (array_min(rs1) + array_max(rs1)) / 2 )
    end as rs2,
    case when substring(lines, 3, 1) = 'F'
      then filter(rs2, i -> i < (array_min(rs2) + array_max(rs2)) / 2 )
      else filter(rs2, i -> i > (array_min(rs2) + array_max(rs2)) / 2 )
    end as rs3,
    case when substring(lines, 4, 1) = 'F'
      then filter(rs3, i -> i < (array_min(rs3) + array_max(rs3)) / 2 )
      else filter(rs3, i -> i > (array_min(rs3) + array_max(rs3)) / 2 )
    end as rs4,
    case when substring(lines, 5, 1) = 'F'
      then filter(rs4, i -> i < (array_min(rs4) + array_max(rs4)) / 2 )
      else filter(rs4, i -> i > (array_min(rs4) + array_max(rs4)) / 2 )
    end as rs5,
    case when substring(lines, 6, 1) = 'F'
      then filter(rs5, i -> i < (array_min(rs5) + array_max(rs5)) / 2 )
      else filter(rs5, i -> i > (array_min(rs5) + array_max(rs5)) / 2 )
    end as rs6,
    case when substring(lines, 7, 1) = 'F'
      then filter(rs6, i -> i < (array_min(rs6) + array_max(rs6)) / 2 )
      else filter(rs6, i -> i > (array_min(rs6) + array_max(rs6)) / 2 )
    end as row,
    sequence(0, 7) as columns,
    case when substring(lines, 8, 1) = 'L'
      then filter(columns, i -> i < (array_min(columns) + array_max(columns)) / 2 )
      else filter(columns, i -> i > (array_min(columns) + array_max(columns)) / 2 )
    end as cs1, -- Step 1
    case when substring(lines, 9, 1) = 'L'
      then filter(cs1, i -> i < (array_min(cs1) + array_max(cs1)) / 2 )
      else filter(cs1, i -> i > (array_min(cs1) + array_max(cs1)) / 2 )
    end as cs2,
    case when substring(lines, 10, 1) = 'L'
      then array_min(cs2)
      else array_max(cs2)
    end as column,
    (array_min(row) * 8) + column as seatID
  from input_data
)

select max(seatID) as result from seats

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC What is the ID of your seat?

-- COMMAND ----------


with input_data as (
  select explode(split(data, '\n')) as lines from frank.adventofcode.inputdata where year = 2020 and day = 5
  qualify rank() over (partition by year, day order by loaded desc) = 1 and lines <> ''
),

seats as (
  select
    lines,
    sequence(0, 127) as rows,
    case when substring(lines, 1, 1) = 'F'
      then filter(rows, i -> i < (array_min(rows) + array_max(rows)) / 2 )
      else filter(rows, i -> i > (array_min(rows) + array_max(rows)) / 2 )
    end as rs1, -- Step 1
    case when substring(lines, 2, 1) = 'F'
      then filter(rs1, i -> i < (array_min(rs1) + array_max(rs1)) / 2 )
      else filter(rs1, i -> i > (array_min(rs1) + array_max(rs1)) / 2 )
    end as rs2,
    case when substring(lines, 3, 1) = 'F'
      then filter(rs2, i -> i < (array_min(rs2) + array_max(rs2)) / 2 )
      else filter(rs2, i -> i > (array_min(rs2) + array_max(rs2)) / 2 )
    end as rs3,
    case when substring(lines, 4, 1) = 'F'
      then filter(rs3, i -> i < (array_min(rs3) + array_max(rs3)) / 2 )
      else filter(rs3, i -> i > (array_min(rs3) + array_max(rs3)) / 2 )
    end as rs4,
    case when substring(lines, 5, 1) = 'F'
      then filter(rs4, i -> i < (array_min(rs4) + array_max(rs4)) / 2 )
      else filter(rs4, i -> i > (array_min(rs4) + array_max(rs4)) / 2 )
    end as rs5,
    case when substring(lines, 6, 1) = 'F'
      then filter(rs5, i -> i < (array_min(rs5) + array_max(rs5)) / 2 )
      else filter(rs5, i -> i > (array_min(rs5) + array_max(rs5)) / 2 )
    end as rs6,
    case when substring(lines, 7, 1) = 'F'
      then filter(rs6, i -> i < (array_min(rs6) + array_max(rs6)) / 2 )
      else filter(rs6, i -> i > (array_min(rs6) + array_max(rs6)) / 2 )
    end as row,
    sequence(0, 7) as columns,
    case when substring(lines, 8, 1) = 'L'
      then filter(columns, i -> i < (array_min(columns) + array_max(columns)) / 2 )
      else filter(columns, i -> i > (array_min(columns) + array_max(columns)) / 2 )
    end as cs1, -- Step 1
    case when substring(lines, 9, 1) = 'L'
      then filter(cs1, i -> i < (array_min(cs1) + array_max(cs1)) / 2 )
      else filter(cs1, i -> i > (array_min(cs1) + array_max(cs1)) / 2 )
    end as cs2,
    case when substring(lines, 10, 1) = 'L'
      then array_min(cs2)
      else array_max(cs2)
    end as column,
    (array_min(row) * 8) + column as seatID
  from input_data
),

seat_ids as (
  select
    collect_set(seatID) as taken_seats,
    sequence(array_min(taken_seats), array_max(taken_seats)) as available_seats,
    array_except(available_seats, taken_seats) as missing
  from seats
)

select array_min(missing) as result from seat_ids

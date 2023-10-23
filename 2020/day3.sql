-- Databricks notebook source
-- MAGIC %md
-- MAGIC # AOC2020 - Day 3: Toboggan Trajectory
-- MAGIC https://adventofcode.com/2020/day/3

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#'
where year = 2020 and day = 3

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?

-- COMMAND ----------


with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 3
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

coordinates as (
  select
    row_number() over (order by 1) as y,
    ((y - 1) * 3) % length(data) + 1 as x,
    case when substring(data, x, 1) = '#' then 1 else 0 end as hit
  from input_data
)

select sum(hit) as result from coordinates

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC What do you get if you multiply together the number of trees encountered on each of the listed slopes?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 3
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

coordinates as (
  select
    row_number() over (order by 1) as y,
    case when substring(data, ((y - 1)) % length(data) + 1, 1) = '#' then 1 else 0 end as d1r1,
    case when substring(data, ((y - 1) * 3) % length(data) + 1, 1) = '#' then 1 else 0 end as d1r3,
    case when substring(data, ((y - 1) * 5) % length(data) + 1, 1) = '#' then 1 else 0 end as d1r5,
    case when substring(data, ((y - 1) * 7) % length(data) + 1, 1) = '#' then 1 else 0 end as d1r7,
    case when y % 2 = 1 and substring(data, (y -1) /2 % length(data) + 1, 1) = '#' then 1 else 0 end as d2r1
  from input_data
)

select sum(d1r1) * sum(d1r3) * sum(d1r5) * sum(d1r7) * sum(d2r1) as result from coordinates

-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 3

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

with_rn as (
  select
    row_number() over (order by 1) as rn,
    data
  from input_data
),

coordinates as (
  select
    ((rn - 1) * 3) % length(data) + 1 as x,
    case when substring(data, x, 1) = '#' then 1 else 0 end as hit
  from with_rn
)

select sum(hit) as result from coordinates

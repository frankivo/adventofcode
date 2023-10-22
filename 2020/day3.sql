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
  select explode(split(example_data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 3
  qualify rank() over (partition by year, day order by loaded desc) = 1
)

select *, 1 as x, 1 as y from input_data

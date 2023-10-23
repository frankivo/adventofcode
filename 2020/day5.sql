-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 5

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'BFFFBBFRRR
FFFBBBFRRR
BBFFBBFRLL'
where year = 2020 and day = 5

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC As a sanity check, look through your list of boarding passes. What is the highest seat ID on a boarding pass?

-- COMMAND ----------


with input_data as (
  select explode(split(example_data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 5
  qualify rank() over (partition by year, day order by loaded desc) = 1
)

select * from input_data

-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 1

-- COMMAND ----------

update frank.adventofcode.inputdata set example_data = '1721
979
366
299
675
1456' where year = 2020 and day = 1 

-- COMMAND ----------

-- MAGIC %md ## Part 1

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 1
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

ints as (select int(data) from input_data),

combinations as (
  select distinct l.data as left, r.data as right
  from ints as l
  cross join ints as r
),

summed as (
  select
    left,
    right,
    left + right as sum,
    left * right as product
  from combinations
)

select *
from summed
where sum = 2020
limit 1

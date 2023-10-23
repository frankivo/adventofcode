-- Databricks notebook source
-- MAGIC %md
-- MAGIC # AOC2020 - Day 1: Report Repair
-- MAGIC https://adventofcode.com/2020/day/1

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data = '1721
979
366
299
675
1456'
where year = 2020 and day = 1

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC Find the two entries that sum to 2020; what do you get if you multiply them together?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 1
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

combinations as (
  select distinct l.data as left, r.data as right
  from input_data as l
  cross join input_data as r
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

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC In your expense report, what is the product of the three entries that sum to 2020?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 1
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

combinations as (
  select distinct l.data as left, m.data as mid, r.data as right
  from input_data as l
  cross join input_data as m
  cross join input_data as r
),

summed as (
  select
    left,
    mid,
    right,
    left + mid + right as sum,
    left * mid * right as product
  from combinations
)

select *
from summed
where sum = 2020
limit 1

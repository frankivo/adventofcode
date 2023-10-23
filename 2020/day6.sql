-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 6
-- MAGIC https://adventofcode.com/2020/day/6

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'abc

a
b
c

ab
ac

a
a
a
a

b'
where year = 2020 and day = 6

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n\n')) as groups from frank.adventofcode.inputdata where year = 2020 and day = 6
  qualify rank() over (partition by year, day order by loaded desc) = 1 
),

answers as (
  select size(array_distinct(split(replace(groups, '\n'), ''))) as unique_answers
  from input_data
)

select sum(unique_answers) as result from answers

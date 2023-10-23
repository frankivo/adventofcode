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

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?

-- COMMAND ----------

with input_data as (
  select explode(split(example_data, '\n\n')) as groups from frank.adventofcode.inputdata where year = 2020 and day = 6
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

answers as (
  select
    groups,
    row_number() over (order by 1) as group_id,
    split(trim(groups), '\n') as group_answers
  from input_data
),

per_group as (
  select group_id, explode(group_answers) as answers from answers
), 

group_answers as (
  select
    group_id,
    row_number() over (partition by group_id order by answers) as answer_id,
    split(answers, '') as answers
  from per_group
), 

unique_per_group as (
  select
    group_id,
    answer_id,
    answers,
    lag(answers) over (partition by group_id order by answer_id desc) as prev,
    array_intersect(answers, coalesce(prev, answers)) as unique,
    size(unique) as unique_count
  from group_answers
)

select sum(unique_count) as result
from unique_per_group
where answer_id = 1

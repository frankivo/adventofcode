-- Databricks notebook source
-- MAGIC %md
-- MAGIC # AOC2020 - Day 6: Custom Customs
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
  select explode(split(data, '\n\n')) as groups from frank.adventofcode.inputdata where year = 2020 and day = 6
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

answers as (
  select
    groups,
    row_number() over (order by 1) as group_id,
    split(groups, '\n') as persons
  from input_data
),

per_person as (
  select group_id, explode(persons) as answers from answers
), 

person_answers as (
  select
    group_id,
    split(answers, '') as answer
  from per_person
  where answers <> ''
),

unique_per_group as (
  select
    group_id,
    answer,
    lag(answer) over (partition by group_id order by 1) as prev,
    array_intersect(answer, coalesce(prev, answer)) as unique_answers,
    size(unique_answers) as unique_count
  from person_answers
),

lows as (
  select unique_count from unique_per_group
  qualify row_number() over (partition by group_id order by unique_count asc) = 1
)

select sum(unique_count) as result from lows

-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 2

-- COMMAND ----------

update frank.adventofcode.inputdata set example_data = '1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc'
where year = 2020 and day = 2

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC How many passwords are valid according to their policies?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 2
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

extract as (
  select regexp_extract_all(data, "(\\d+|[a-z]+)") as values from input_data
),

policies as (
  select
    element_at(values, 1) as low,
    element_at(values, 2) as high,
    element_at(values, 3) as key,
    element_at(values, 4) as password,
    regexp_count(password, key) as keycount
  from extract
)

select count(*) as valid
from policies
where
  keycount >= low
  and keycount <= high

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC How many passwords are valid according to the new interpretation of the policies?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 2
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

extract as (
  select regexp_extract_all(data, "(\\d+|[a-z]+)") as values from input_data
),

policies as (
  select
    element_at(values, 1) as low,
    element_at(values, 2) as high,
    element_at(values, 3) as key,
    element_at(values, 4) as password,
    substring(password, low, 1) as first,
    substring(password, high, 1) as last
  from extract
)

select count(*) as valid
from policies
where
  (first = key and last <> key)
  or (first <> key and last = key)

-- Databricks notebook source
-- MAGIC %md
-- MAGIC # AOC2020 - Day 7: Handy Haversacks
-- MAGIC https://adventofcode.com/2020/day/7

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.'
where year = 2020 and day = 7

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC How many bag colors can eventually contain at least one shiny gold bag?

-- COMMAND ----------

with input_data as (
  select explode(split(example_data, '\n')) as rules from frank.adventofcode.inputdata where year = 2020 and day = 7
  qualify rank() over (partition by year, day order by loaded desc) = 1 
),

direct as (
  select rules, concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as container
  from input_data
  where regexp_like(rules, '(contain).+(shiny gold bag)')
),

sub as (
  select distinct concat(regexp_extract(i.rules, '^(.+)(bags )', 1), 'bag') as container
  from input_data as i, direct as d
  where regexp_like(i.rules, format_string('(contain).+(%s)', container))
)

select container from sub
union
select container from direct


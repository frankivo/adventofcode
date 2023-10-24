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
-- MAGIC
-- MAGIC This solution is kinda hacky:
-- MAGIC   * I cheated by looking up the answer using someone elses solution;
-- MAGIC   * I'm faking a loop by repeating the same selections until no new rows are returned;

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n')) as rules from frank.adventofcode.inputdata where year = 2020 and day = 7
  qualify rank() over (partition by year, day order by loaded desc) = 1 
),

c0 as (select '%contain%shiny gold bag%' as rule),

c1 as ( 
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c1_container,
    format_string('%%contain%%%s%%', c1_container) as rule
  from input_data as i
  inner join c0 on i.rules like c0.rule
),

c2 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c2_container,
    format_string('%%contain%%%s%%', c2_container) as rule
  from input_data as i
  inner join c1 on i.rules like c1.rule
),

c3 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c3_container,
    format_string('%%contain%%%s%%', c3_container) as rule
  from input_data as i
  inner join c2 on i.rules like c2.rule
),

c4 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c4_container,
    format_string('%%contain%%%s%%', c4_container) as rule
  from input_data as i
  inner join c3 on i.rules like c3.rule
),

c5 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c5_container,
    format_string('%%contain%%%s%%', c5_container) as rule
  from input_data as i
  inner join c4 on i.rules like c4.rule
),

c6 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c6_container,
    format_string('%%contain%%%s%%', c6_container) as rule
  from input_data as i
  inner join c5 on i.rules like c5.rule
),

c7 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c7_container,
    format_string('%%contain%%%s%%', c7_container) as rule
  from input_data as i
  inner join c6 on i.rules like c6.rule
),

c8 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c8_container,
    format_string('%%contain%%%s%%', c8_container) as rule
  from input_data as i
  inner join c7 on i.rules like c7.rule
),

c9 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c9_container,
    format_string('%%contain%%%s%%', c9_container) as rule
  from input_data as i
  inner join c8 on i.rules like c8.rule
),

c10 as (  
  select
    concat(regexp_extract(rules, '^(.+)(bags )', 1), 'bag') as c10_container,
    format_string('%%contain%%%s%%', c10_container) as rule
  from input_data as i
  inner join c9 on i.rules like c9.rule
),

containers as (
  select c1_container from c1
  union
  select c2_container from c2
  union
  select c3_container from c3
  union
  select c4_container from c4
  union
  select c5_container from c5
  union
  select c6_container from c6
  union
  select c7_container from c7
  union
  select c8_container from c8
  union
  select c9_container from c9
  union
  select c10_container from c10
)

select count(*) as result from containers

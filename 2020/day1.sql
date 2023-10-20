-- Databricks notebook source
with raw as (
select explode(split('1721
979
366
299
675
1456', '\n')) as data
),

ints as (
  select int(data) from raw
),

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

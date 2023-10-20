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
  select distinct * from ints as y cross join ints as x
)

select *, (x.data + y.data) from combinations 

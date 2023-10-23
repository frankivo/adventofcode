-- Databricks notebook source
-- MAGIC %md # AOC2020 - Day 4

-- COMMAND ----------

update frank.adventofcode.inputdata
set example_data =
'ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in'
where year = 2020 and day = 4

-- COMMAND ----------

-- MAGIC %md ## Part 1
-- MAGIC Count the number of valid passports - those that have all required fields. Treat cid as optional. In your batch file, how many passports are valid?

-- COMMAND ----------


with input_data as (
  select explode(split(data, '\n\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 4
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

validator as (
  select case
    when data like '%byr:%'
    and  data like '%iyr:%'
    and  data like '%eyr:%'
    and  data like '%hgt:%'
    and  data like '%hcl:%'
    and  data like '%ecl:%'
    and  data like '%pid:%'
    then 1 else 0 end as valid
  from input_data
)

select sum(valid) as result from validator

-- COMMAND ----------

-- MAGIC %md ## Part 2
-- MAGIC Count the number of valid passports - those that have all required fields and valid values. Continue to treat cid as optional. In your batch file, how many passports are valid?

-- COMMAND ----------

with input_data as (
  select explode(split(data, '\n\n')) as data from frank.adventofcode.inputdata where year = 2020 and day = 4
  qualify rank() over (partition by year, day order by loaded desc) = 1
),

kv as (
  select
    data,
    regexp_extract(data, '(byr:(\\d+))', 2) as byr,
    regexp_extract(data, '(iyr:(\\d+))', 2) as iyr,
    regexp_extract(data, '(eyr:(\\d+))', 2) as eyr,
    regexp_extract(data, '(hgt:(\\d+)(in|cm))', 2) as hgt_num,
    regexp_extract(data, '(hgt:(\\d+)(in|cm))', 3) as hgt_type,
    nullif(regexp_extract(data, '(hcl:#([0-9a-f]{6}))', 2), '') as hcl,
    regexp_extract(data, '(ecl:([a-z]{3}))', 2) as ecl,
    nullif(regexp_extract(data, '(pid:(\\d{9}))', 2), '') as pid,
    case
      when byr between 1920 and 2002
      and  iyr between 2010 and 2020
      and  eyr between 2020 and 2030
      and  (hgt_type = 'cm' and hgt_num between 150 and 193 or hgt_type = 'in' and hgt_num between 59 and 76)
      and  hcl is not null
      and  ecl in ('amb', 'blu', 'brn', 'grn', 'hzl', 'oth')
      and  pid is not null 
    then 1 else 0 end as valid
  from input_data
)

select sum(valid) as result from kv

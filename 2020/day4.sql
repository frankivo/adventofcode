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
    row_number() over (order by 1) as rn,
    replace(data, '\n', ' ') as data_one_line,
    concat_ws(' ', sort_array(split(data_one_line, ' '))) as sorted_data,
    regexp_like(data_one_line, 'byr:(19[2-9][0-9]|200[012])') as byr,
    regexp_like(data_one_line, 'ecl:(amb|blu|brn|gry|grn|hzl|oth)') as ecl,
    regexp_like(data_one_line, 'eyr:(202[0-9]|2030)') as eyr,
    regexp_like(data_one_line, 'hcl:#([a-z0-9]{6})') as hcl,
    regexp_like(data_one_line, 'hgt:(1[5678][0-9]|19[0123])cm|hgt:(59|6[0-9]|7[0-6])in') as hgt,
    regexp_like(data_one_line, 'iyr:(201[0-9]|2020)') as iyr,
    regexp_like(data_one_line, 'pid:(\\d{9})') as pid,
    byr and ecl and eyr and hcl and hgt and iyr and pid as valid
  from input_data
)

select count(*) as result from kv where valid

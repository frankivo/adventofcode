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

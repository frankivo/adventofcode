-- AOC2020 - Day 4: Toboggan Trajectory
-- https://adventofcode.com/2020/day/4

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n\n')) as src
    from input_data
    where day = 4
),

-- Part 1: Count the number of valid passports - those that have all required fields. Treat cid as optional.
-- In your batch file, how many passports are valid?
part1 as (
    with validator as (
        select
            case
                when
                    src like '%byr:%'
                    and src like '%iyr:%'
                    and src like '%eyr:%'
                    and src like '%hgt:%'
                    and src like '%hcl:%'
                    and src like '%ecl:%'
                    and src like '%pid:%'
                    then 1
                else 0
            end as valid
        from src_data
    )

    select
        1          as part,
        sum(valid) as result
    from validator
),

-- Part 2: Count the number of valid passports - those that have all required fields and valid values. Continue to treat cid as optional.
-- In your batch file, how many passports are valid?
part2 as (
    with kv as (
        select
            regexp_matches(src, '\bbyr:(19[2-9][0-9]|200[012])\b')                            as byr,
            regexp_matches(src, '\becl:(amb|blu|brn|gry|grn|hzl|oth)\b')                      as ecl,
            regexp_matches(src, '\beyr:(202[0-9]|2030)\b')                                    as eyr,
            regexp_matches(src, '\bhcl:#([a-z0-9]{6})\b')                                     as hcl,
            regexp_matches(src, '\bhgt:(1[5678][0-9]|19[0123])cm|hgt:(59|6[0-9]|7[0-6])in\b') as hgt,
            regexp_matches(src, '\biyr:(201[0-9]|2020)\b')                                    as iyr,
            regexp_matches(src, '\bpid:(\d{9})\b')                                            as pid,
            byr and ecl and eyr and hcl and hgt and iyr and pid                               as valid
        from src_data
    )

    select
        2        as part,
        count(*) as result
    from kv
    where valid
)

select * from part1
union
select * from part2
order by part

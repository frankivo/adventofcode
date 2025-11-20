-- AOC2020 - Day 6: Custom Customs
-- https://adventofcode.com/2020/day/6

with src_data as (
    select unnest(string_split_regex(regexp_replace(source_column, '\n$', ''), '\n\n')) as src
    from input_data
    where day = 6
),

-- For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
part1 as (
    with answers as (
        select length(list_distinct(split(regexp_replace(src, '\n', '', 'g'), ''))) as unique_answers
        from src_data
    )

    select
        1                   as part,
        sum(unique_answers) as result
    from answers
),

-- For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
part2 as (
    with answers as (
        select
            src,
            row_number() over (order by 1) as group_id,
            string_split_regex(src, '\n')  as persons
        from src_data
    ),

    per_person as (
        select
            group_id,
            unnest(persons) as answers
        from answers
    ),

    person_answers as (
        select
            group_id,
            split(answers, '') as answer
        from per_person
    ),

    unique_per_group as (
        select
            group_id,
            answer,
            lag(answer) over (partition by group_id order by 1) as prev,
            list_intersect(answer, coalesce(prev, answer))      as unique_answers,
            length(unique_answers)                              as unique_count
        from person_answers
    ),

    lows as (
        select unique_count from unique_per_group
        qualify row_number() over (partition by group_id order by unique_count asc) = 1 -- noqa
    )

    select
        2                 as part,
        sum(unique_count) as result
    from lows
)

select * from part1
union
select * from part2
order by part

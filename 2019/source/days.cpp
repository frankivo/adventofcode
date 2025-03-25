#include "days.hpp"

#include "day01.cpp"
#include "day02.cpp"
#include "day03.cpp"
#include "day04.cpp"
#include "day05.cpp"
#include "day06.cpp"
#include "day07.cpp"

Days::Days() {
    days[1] = std::make_unique<Day01>();
    days[2] = std::make_unique<Day02>();
    days[3] = std::make_unique<Day03>();
    days[4] = std::make_unique<Day04>();
    days[6] = std::make_unique<Day06>();
    days[7] = std::make_unique<Day07>();
};

const std::unique_ptr<Day> Days::by_number(int day_no) {
    auto d = days.find(day_no);
    if (d == days.end())
        return nullptr;
    return std::move(d->second);
};

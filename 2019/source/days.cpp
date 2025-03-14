#include "days.h"

#include "day01.cpp"
#include "day02.cpp"

Days::Days() {
	days[1] = std::make_unique<Day01>();
	days[2] = std::make_unique<Day02>();
};

std::unique_ptr<Day> Days::by_number(int day_no) {
	auto d = days.find(day_no);
        if (d == days.end())
        	return nullptr;
	return std::move(d->second);
};

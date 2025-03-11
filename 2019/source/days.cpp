#include "days.h"

#include "day01.cpp"

Days::Days() {
	days[1] = std::make_unique<Day01>();
};

std::unique_ptr<Day> Days::by_number(int day_no) {
	auto d = days.find(day_no);
        if (d == days.end()) {
        	std::cerr << "Not found!" << std::endl;
        	return NULL;
	}
	return std::move(d->second);
};

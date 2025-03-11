#include <iostream>
#include <map>
#include <memory>
#include "days.h"

int main(int argc, char *argv[]) {
	auto day_no = std::stoi(argv[1]);
	std::cout << "Running day " << day_no << std::endl;

	auto days = Days();
	auto day = days.by_number(day_no);

	if (day == NULL)
		return 1;

	std::cout << "Result part 1: " << day->part1() << std::endl;
	std::cout << "Result part 2: " << day->part2() << std::endl;
	return 0;
}

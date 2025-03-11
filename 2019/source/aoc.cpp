#include <iostream>
#include <map>
#include <memory>
#include "all_days.h"

int main(int argc, char *argv[]) {
	std::map<int, std::unique_ptr<day>> days;
	days[1] = std::make_unique<day01>();

	auto day_no = std::stoi(argv[1]);
	std::cout << "Running day " << day_no << std::endl;
	auto& d = days[day_no];

	std::cout << "Result part 1: " << d->part1() << std::endl;
	std::cout << "Result part 2: " << d->part2() << std::endl;
}

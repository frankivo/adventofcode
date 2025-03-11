#include <iostream>
#include <map>
#include <memory>
#include "all_days.h"

int main(int argc, char *argv[]) {
	std::map<int, std::unique_ptr<day>> days;
	days[1] = std::make_unique<day01>();

	auto day_no = std::stoi(argv[1]);
	std::cout << "Running day " << day_no << std::endl;

	auto d = days.find(day_no);
        if (d == days.end()) {
                std::cout << "Not found!" << std::endl;
		return 1;
	}

	std::cout << "Result part 1: " << d->second->part1() << std::endl;
	std::cout << "Result part 2: " << d->second->part2() << std::endl;
	return 0;
}

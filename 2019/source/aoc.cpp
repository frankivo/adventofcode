#include <iostream>

#include "all_days.h"

int main(int argc, char *argv[]) {
	auto day = std::stoi(argv[1]);
	std::cout << "Running day " << day << std::endl;

	auto d = day01();
	auto r = d.run();
	std::cout << "Result: " << r << std::endl;
}

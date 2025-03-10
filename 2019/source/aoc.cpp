#include <iostream>

#include "day01.cpp"

int main(int argc, char *argv[]) {
	auto day = std::stoi(argv[1]);
	std::cout << "Running day " << day << std::endl;

	auto d = day1();
	auto r = d.run();
	std::cout << "Result: " << r << std::endl;
}

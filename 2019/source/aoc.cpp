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
	auto r = d->run();

//	auto d = day01();
//	auto r = d.run();
	std::cout << "Result: " << r << std::endl;
}

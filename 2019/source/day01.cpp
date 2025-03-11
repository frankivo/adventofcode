
#include <iostream>
#include <fstream>
#include <cmath>

#include "day.h"

class day01 : public day {
	public:
		int part1() {
			std::string line;
			int fuel_req = 0;
			std::ifstream infile("input/day01.txt");

			while (getline(infile, line)) {
				auto num = std::stoi(line);
				fuel_req += std::floor(num / 3) - 2;
			}
			infile.close();

			return fuel_req;
		}

		int part2() {
			return 0;
		}
};

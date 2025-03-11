#include <algorithm>
#include <cmath>
#include <iostream>
#include <fstream>
#include <vector>

#include "day.h"

class Day01 : public Day {
	public:
		int part1() {
			int fuel_req = 0;

			auto lines = data();
			std::for_each(lines.begin(), lines.end(), [&fuel_req,this](int mass) {
				fuel_req += fuel(mass);
			});

			return fuel_req;
		};

		int part2() {
                        int fuel_req = 0;

                        auto lines = data();
			std::for_each(lines.begin(), lines.end(), [&fuel_req,this](int mass) {
				while (mass >= 0) {
					mass = fuel(mass);
					if (mass > 0)
						fuel_req += mass;
				}
                        });

			return fuel_req;
		};

	private:
		std::vector<int> data() {
			std::vector<int> data;
			std::string line;

			std::ifstream infile("input/day01.txt");
			while (getline(infile, line))
                                data.push_back(std::stoi(line));
			infile.close();

			return data;
		};

		int fuel(int mass) const {
			return std::floor(mass / 3) - 2;
		};
};

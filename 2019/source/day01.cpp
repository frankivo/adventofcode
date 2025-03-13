#include "day.h"

#include <algorithm>
#include <cmath>

class Day01 : public Day {
	public:
		Day01() : Day(1) {};

		int part1() {
			int fuel_req = 0;

			auto lines = data();
			std::for_each(lines.begin(), lines.end(), [&fuel_req,this](std::string mass) {
				fuel_req += fuel(std::stoi(mass));
			});

			return fuel_req;
		};

		int part2() {
                        int fuel_req = 0;

                        auto lines = data();
			std::for_each(lines.begin(), lines.end(), [&fuel_req,this](std::string mass) {
				int imass = std::stoi(mass);
				while (imass >= 0) {
					imass = fuel(imass);
					if (imass > 0)
						fuel_req += imass;
				}
                        });

			return fuel_req;
		};

		const int fuel(int mass) const {
			return std::floor(mass / 3) - 2;
		};
};

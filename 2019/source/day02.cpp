#include "day.h"

#include <iostream>
#include <boost/algorithm/string.hpp>

class Day02 : public Day {
	public:
		Day02() : Day(2) {};

		int part1() {
			auto data = numbers();
			data[1] = 12;
			data[2] = 2;

			for(auto i = 0; i < data.size(); i += 4) {
				if (data[i] == 1)
					data[data[i+3]] = data[data[i+1]] + data[data[i+2]];
				else if (data[i] == 2)
					data[data[i+3]] = data[data[i+1]] * data[data[i+2]];
				else if (data[i] == 99)
					break;
			}
			return data[0];
		};

		int part2() {
			return -1;
		};


		std::vector<int> numbers() {
			auto line = data()[0];
			std::vector<std::string> values;
			std::vector<int> nums;

			boost::split(values, line, boost::is_any_of(","));
			for (auto& i : values) nums.push_back(std::stoi(i));
			return nums;
		};

		void print(std::vector<int> data) {
			for (auto i = 0; i < data.size(); i++)
				std::cout << data[i] << ",";
			std::cout << std::endl;
		};
};

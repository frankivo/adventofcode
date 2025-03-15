#include "day.h"

#include <boost/algorithm/string.hpp>

class Day02 : public Day {
	public:
		Day02() : Day(2) {};

		int part1() const {
			return solve(12, 2);
		};

		int part2() const {
			for (auto n = 0; n < 100; n++)
				for (auto v = 0; v < 100; v++)
					if (solve(n, v) == 19690720)
						return 100 * n + v;
			return -1;
		};

		int solve(int noun, int verb) const {
			auto data = numbers();
			data[1] = noun;
			data[2] = verb;

			for(auto i = 0; i < int(data.size()); i += 4) {
				if (data[i] == 1)
					data[data[i+3]] = data[data[i+1]] + data[data[i+2]];
				else if (data[i] == 2)
					data[data[i+3]] = data[data[i+1]] * data[data[i+2]];
				else if (data[i] == 99)
					break;
			}
			return data[0];

		};

		const std::vector<int> numbers() const {
			auto line = data()[0];
			std::vector<std::string> values;
			std::vector<int> nums;

			boost::split(values, line, boost::is_any_of(","));
			for (auto& i : values) nums.push_back(std::stoi(i));
			return nums;
		};
};

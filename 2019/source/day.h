#ifndef H_DAY
#define H_DAY

#include <string>
#include <vector>

class Day {
	public:
		Day(int);
		virtual const int part1() = 0;
		virtual const int part2() = 0;
		virtual ~Day() {};
	protected:
		const std::vector<std::string> data();
		int number;
};

#endif

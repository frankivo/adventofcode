
#include <iostream>
#include <fstream>
#include <cmath>

class day01 {
	public:
		int run() {
			std::string line;
			int fuel_req = 0;
			std::ifstream infile("input/day1.txt");

			while (getline(infile, line)) {
				auto num = std::stoi(line);
				fuel_req += std::floor(num / 3) - 2;
			}
			infile.close();

			return fuel_req;
		}
};

#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <unordered_map>
#include <iostream>
#include <boost/range/irange.hpp>

#include "day05.hpp"

class Day07 : public Day
{

public:
    Day07() : Day(7), input(parse_data()) {};

    int part1() const
    {
        auto instructor = Day05();
        auto best = -1;
        auto range = std::vector<int>{0,1,2,3,4};

        do {
            auto res = 0;
            for (auto i = 0; i < 5; i++)
                res = instructor.solve(input, range[i], res);
            best = std::max(best, res);
        }
        while (std::next_permutation(range.begin(), range.end()));

        return best;
    }

    int part2() const
    {
        return -1;
    };

private:
    const std::vector<int> input;

    const std::vector<int> parse_data() const {
        auto line = data()[0];
        std::vector<std::string> values;
        std::vector<int> nums;

        boost::split(values, line, boost::is_any_of(","));
        for (auto& i : values) nums.push_back(std::stoi(i));
            return nums;
    };
};

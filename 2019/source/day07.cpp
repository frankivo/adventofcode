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

        for (auto i1 : boost::irange(0, 5)) {
            auto a1 = instructor.solve(input, i1, 0);
            for (auto i2 : boost::irange(0, 5)) {
                auto a2 = instructor.solve(input, i2, a1);
                for (auto i3 : boost::irange(0, 5)) {
                    auto a3 = instructor.solve(input, i3, a2);
                    for (auto i4 : boost::irange(0, 5)) {
                        auto a4 = instructor.solve(input, i4, a3);
                        for (auto i5 : boost::irange(0, 5)) {
                            auto a5 = instructor.solve(input, i5, a4);
                            best = std::max(best, a5);
                        }
                    }
                }
            }
        }

        return
        instructor.solve(input, 0,
        instructor.solve(input, 1,
        instructor.solve(input, 2,
        instructor.solve(input, 3,
            instructor.solve(input, 4, 0)
        ))));
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

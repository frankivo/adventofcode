#include "day.hpp"

#include <algorithm>
#include <iostream>
#include <boost/range/irange.hpp>

class Day04 : public Day
{

public:
    Day04() : Day(4) {};

    int part1() const
    {
        auto range = boost::irange(256310, 732736);
        auto res = std::count_if(range.begin(), range.end(), [this](int pw) {
            return valid(pw);
        });
        return res;
    };

    int part2() const
    {
        return -2;
    };

private:
    bool valid(int password) const
    {
        auto pw = std::to_string(password);
        std::vector<std::string> slices;
        for (size_t i = 0; i <= pw.size() - 2; ++i)
            slices.push_back(pw.substr(i, 2));

        bool result_same = std::any_of(slices.begin(), slices.end(), [](std::string pair) {
            return pair[0] == pair[1];
        });
        bool result_inc = std::all_of(slices.begin(), slices.end(), [](std::string pair) {
            return static_cast<int>(pair[1]) >= static_cast<int>(pair[0]);
        });

        return result_same && result_inc;
    };
};

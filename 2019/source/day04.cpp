#include "day.hpp"

#include <iostream>

class Day04 : public Day
{

public:
    Day04() : Day(4) {};

    int part1() const
    {
        valid(223450);
        return -1;
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

        for (auto &p : slices)
        {
            std::cout << p << std::endl;
        }

        return false;
    };
};

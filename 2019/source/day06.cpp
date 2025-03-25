#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <iostream>
#include <unordered_map>

class Day06 : public Day
{

public:
    Day06() : Day(6), orbits(parse_data()) {};

    int part1() const
    {
        auto total = 0;
        for (const auto& o : orbits)
            total += distance(o.first);
        return total;
    }

    int part2() const
    {
        return -1;
    };

private:
    typedef std::unordered_map<std::string, std::string> orbit_map;

    const orbit_map orbits;

    const orbit_map parse_data() const {
        orbit_map orbs;

        for (const auto& line: data()) {
            std::vector<std::string> values;
            boost::split(values, line, boost::is_any_of(")"));
            orbs[values[1]] = values[0];
        }

        return orbs;
    };

    int distance(const std::string& orbit) const {
        auto dist = 0;
        auto cur = orbit;

        while (cur != "COM") {
            cur = orbits.at(cur);
            dist+=1;
        }

        return dist;
    }
};

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
        auto you = path("YOU");
        auto san = path("SAN");

        auto dist = 0;
        for (auto& y : you) {
            std::cout<< y << "::" << y <<std::endl;
            auto it = std::find(san.begin(), san.end(), y);
            if (it != san.end())
                return dist + std::distance(san.begin(), it);
            dist++;
        }

        return -1;
    };

private:
    typedef std::unordered_map<std::string, std::string> orbit_map;
    typedef std::vector<std::string> orbit_distance;

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
    };

    orbit_distance path(const std::string& orbit) const {
        orbit_distance distances;
        auto cur = orbit;

        while (cur != "COM") {
            cur = orbits.at(cur);
            distances.push_back(cur);
        }

        return distances;
    };
};

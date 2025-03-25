#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <unordered_map>

class Day06 : public Day
{

public:
    Day06() : Day(6), orbits(parse_data()) {};

    int part1() const
    {
        auto total = 0;
        for (const auto& o : orbits)
            total += path(o.first).size();
        return total;
    }

    int part2() const
    {
        auto you = path("YOU");
        auto san = path("SAN");
        auto dist = 0;
        for (auto& y : you) {
            auto it = std::find(san.begin(), san.end(), y);
            if (it != san.end())
                return dist + std::distance(san.begin(), it);
            dist++;
        }
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

    std::vector<std::string> path(const std::string& orbit) const {
        std::vector<std::string> distances;
        auto cur = orbit;
        while (cur != "COM") {
            cur = orbits.at(cur);
            distances.push_back(cur);
        }
        return distances;
    };
};

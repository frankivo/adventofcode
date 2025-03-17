#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <iostream>
#include <boost/regex.hpp>
#include <cmath>

class Day03 : public Day {
    public:
        Day03() : Day(3) {};

        typedef std::pair<std::string, int> command;
        typedef std::pair<int, int> coordinate;

        int part1() const {
            auto wire1 = wire(0);
            auto wire2 = wire(1);

            std::vector<coordinate> duplicates;
            std::set_intersection(
                wire1.begin(), wire1.end(), wire2.begin(), wire2.end(), std::back_inserter(duplicates)
            );

            auto nearest = std::numeric_limits<int>::max();
            for (auto& pos : duplicates) {
                auto x = std::abs(pos.first);
                auto y = std::abs(pos.second);
                if (x +y < nearest)
                    nearest =x +y;
            }

            return nearest;
        };

        int part2() const {
            return -2;
        };

    private:
        std::vector<command> cmds(const int wire) const {
            auto line = data()[wire];
            std::vector<command> data;
            boost::regex pattern("([A-Z])([0-9]+)");

            boost::sregex_iterator begin(line.begin(), line.end(), pattern);
            boost::sregex_iterator end;

            for (auto i = begin; i != end; ++i) {
                auto match = *i;
                auto cmd = match[1].str();
                auto val = std::stoi(match[2].str());
                command tuple(cmd, val);
                data.push_back(tuple);
            }

            return data;
        };

        std::set<coordinate> wire(const int wire) const {
            auto x = 0;
            auto y = 0;
            std::set<coordinate> positions;

            for (auto cmd : cmds(wire)) {
                for (auto i = 0; i < cmd.second; i++) {
                    if (cmd.first == "R")
                        x += 1;
                    if (cmd.first == "L")
                        x -= 1;
                    if (cmd.first == "U")
                        y += 1;
                    if (cmd.first == "D")
                        y -= 1;

                    coordinate tuple(x, y);
                    positions.insert(tuple);
                }
            }

            return positions;
        };
};

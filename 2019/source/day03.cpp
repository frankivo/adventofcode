#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <iostream>
#include <boost/regex.hpp>

class Day03 : public Day {
    public:
        Day03() : Day(3) {};

        int part1() const {
            std::vector<std::pair<int,int>> positions;
            wire(positions, 0);
            wire(positions, 1);

            std::set<std::pair<int,int>> duplicates;
            for (auto& pos : positions)
                if (count(positions.begin(), positions.end(), pos) > 1)
                    duplicates.insert(pos);

            auto nearest = -1;
            for (auto& pos : duplicates)
                if (pos.first + pos.second < nearest || nearest == -1)
                    nearest = pos.first + pos.second;

            return nearest;
        };

        int part2() const {
            return -2;
        };

    private:
        std::vector<std::pair<std::string, int>> cmds(const int wire) const {
            auto line = data()[wire];
            std::vector<std::pair<std::string, int>> data;
            boost::regex pattern("([A-Z])([0-9]+)");

            boost::sregex_iterator begin(line.begin(), line.end(), pattern);
            boost::sregex_iterator end;

            for (auto i = begin; i != end; ++i) {
                auto match = *i;
                auto cmd = match[1].str();
                auto val = std::stoi(match[2].str());
                std::pair<std::string, int> tuple(cmd, val);
                data.push_back(tuple);
            }

            return data;
        };

        void wire(std::vector<std::pair<int,int>> &positions, const int wire) const {
            auto x = 0;
            auto y = 0;

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

                    std::pair<int,int> tuple(x, y);
                    positions.push_back(tuple);
                }
            }
        };
};

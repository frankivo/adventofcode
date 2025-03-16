#include "day.h"

#include <boost/algorithm/string.hpp>
#include <iostream>
#include <boost/regex.hpp>

class Day03 : public Day {
    public:
        Day03() : Day(3) {};

        int part1() const {
            auto x = 0;
            auto y = 0;

            auto z = cmds();

            for (auto cmd : cmds()) {
                std::cout<<cmd.first<<std::endl;
            }
            //     std::cout << cmd << std::endl;
            //     if (cmd == "RES")
            //         x = y = 0;
            //     else if (cmd[0] == 'R')


            // }
            return -1;
        };

        int part2() const {
            return -2;
        };

    private:
        std::vector<std::pair<std::string, int>> cmds() const {
            auto line = data()[0] + ",RES," + data()[1];
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
        }
};

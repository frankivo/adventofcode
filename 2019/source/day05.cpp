#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <iostream>

class Day05 : public Day
{

public:
    Day05() : Day(5) { input = parse_data(); }

    int part1() const
    {
        std::vector<int> clone(input.begin(), input.end());
        auto i = 0;

        auto opcode = 0;

        while (opcode != 99) {
            auto cur = clone[i];
            std::cout<< i << " :: " << cur << std::endl;

            opcode = opcodes(cur % 100);
            auto paramC = modi(cur / 100 % 10);
            auto paramB = modi(cur / 1000 % 10);

            std::cout << opcode << std::endl;
            std::cout << paramC << std::endl;
            std::cout << paramB << std::endl;

            int param1, param2, x;

            switch (opcode)
            {
            case Multiply:
                param1 = (paramC == Position) ? clone[clone[i+1]] : clone[i+1];
                param2 = (paramB == Position) ? clone[clone[i+2]] : clone[i+2];
                x = param1 * param2;
                clone[i+4] = x;
                i+=4;

                break;
            default:
                break;
            }
        }

        for (auto&n: clone) {
            std::cout << n << ", ";
        }
        std::cout << std::endl;

        return -1;
    };

    int part2() const
    {
        return -1;
    };

private:
    enum opcodes {
        Add = 1,
        Multiply = 2,
        Exit = 99,
    };

    enum modi {
        Position, Immediate
    };

    std::vector<int> input;


    const std::vector<int> parse_data() const {
        auto line = data()[0];
        line = "1002,4,3,4,33";
        std::vector<std::string> values;
        std::vector<int> nums;

        boost::split(values, line, boost::is_any_of(","));
        for (auto& i : values) nums.push_back(std::stoi(i));
            return nums;
    };
};

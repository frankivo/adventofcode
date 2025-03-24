#include "day.hpp"

#include <boost/algorithm/string.hpp>
#include <iostream>

class Day05 : public Day
{

public:
    Day05() : Day(5), input(parse_data()) {};

    int part1() const
    {
        return solve(1);
    }

    int part2() const
    {
        return solve(5);
    };

private:
    enum opcodes {
        Start, Add, Multiply, Input, Output, JumpIfTrue, JumpIfFalse, LessThan, Equals, Exit = 99,
    };

    enum modi {
        Position, Immediate,
    };

    const std::vector<int> input;

    const std::vector<int> parse_data() const {
        auto line = data()[0];
        // line = "1002,4,3,4,33";
        // line = "1101,100,-1,4,0";
        std::vector<std::string> values;
        std::vector<int> nums;

        boost::split(values, line, boost::is_any_of(","));
        for (auto& i : values) nums.push_back(std::stoi(i));
            return nums;
    };

    int solve(const int systemid) const {
        std::vector<int> clone(input.begin(), input.end());

        auto i = 0;
        auto opcode = Start;
        int output;

        while (opcode != Exit) {
            auto cur = clone[i];

            opcode = opcodes(cur % 100);
            auto paramC = modi(cur / 100 % 10);
            auto paramB = modi(cur / 1000 % 10);

            switch (opcode) {
                case Add:
                    clone[clone[i+3]] = ((paramC == Position) ? clone[clone[i+1]] : clone[i+1]) + ((paramB == Position) ? clone[clone[i+2]] : clone[i+2]);
                    break;
                case Multiply:
                    clone[clone[i+3]] = ((paramC == Position) ? clone[clone[i+1]] : clone[i+1]) * ((paramB == Position) ? clone[clone[i+2]] : clone[i+2]);
                    break;
                case Input:
                    ((paramC == Position) ? clone[clone[i+1]] : clone[i+1]) = systemid;
                    break;
                case Output:
                    output = ((paramC == Position) ? clone[clone[i+1]] : clone[i+1]);
                    break;
                case JumpIfTrue:
                    if ((paramC == Position) ? clone[clone[i+1]] : clone[i+1] != 0) {
                        i = (paramB == Position) ? clone[clone[i+2]] : clone[i+2];
                        continue;
                    }
                    break;
                default:
                    break;
                }

            switch (opcode) {
                case Add:
                case Multiply:
                    i+=4;
                    break;
                case JumpIfTrue:
                    i+=3;
                    break;
                default:
                    i+=2;
                    break;
            }
        }

        return output;
    };
};

#include "day.hpp"

#include <boost/algorithm/string.hpp>

// Heavily inspired on https://github.com/boriskurikhin/AdventOfCode2019/blob/master/Day5.java

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

        while (opcode != Exit) {
            auto cur = clone[i];

            opcode = opcodes(cur % 100);
            auto& param1 = (modi(cur / 100 % 10) == Position) ? clone[clone[i+1]] : clone[i+1];
            auto& param2 = ( modi(cur / 1000 % 10) == Position) ? clone[clone[i+2]] : clone[i+2];
            auto& param3 = clone[clone[i+3]];

            switch (opcode) {
                case Add:
                    clone[clone[i+3]] = param1 + param2;
                    break;
                case Multiply:
                    clone[clone[i+3]] = param1 * param2;
                    break;
                case Input:
                    param1 = systemid;
                    break;
                case Output:
                    if (param1 != 0)
                        return param1;
                    break;
                case JumpIfTrue:
                    if (param1 != 0) {
                        i = param2;
                        continue;
                    }
                    break;
                case JumpIfFalse:
                    if (param1 == 0) {
                        i = param2;
                        continue;
                    }
                    break;
                case LessThan:
                    param3 = param1 < param2;
                    break;
                case Equals:
                    param3 = param1 == param2;
                    break;
                default:
                    break;
                }

            switch (opcode) {
                case Add:
                case Multiply:
                case LessThan:
                case Equals:
                    i+=4;
                    break;
                case JumpIfTrue:
                case JumpIfFalse:
                    i+=3;
                    break;
                case Input:
                case Output:
                    i+=2;
                    break;
                default:
                    break;
            }
        }
        return -1;
    };
};

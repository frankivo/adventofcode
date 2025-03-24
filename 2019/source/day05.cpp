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
        std::cout << "part2" << std::endl;
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
        // line = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
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
            // std::cout << "part22: " << opcode << std::endl;
            auto cur = clone[i];

            opcode = opcodes(cur % 100);
            auto paramC = modi(cur / 100 % 10);
            auto paramB = modi(cur / 1000 % 10);
            auto& param1 = (paramC == Position) ? clone[clone[i+1]] : clone[i+1];
            auto& param2 = (paramB == Position) ? clone[clone[i+2]] : clone[i+2];

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
                    output = param1;
                    break;
                case JumpIfTrue:
                    if (param1 != 0)
                        i = param2;
                    else i+=3;
                    break;
                case JumpIfFalse:
                    if (param1 == 0)
                        i = param2;
                    else i+=3;
                    break;
                case LessThan:
                    clone[clone[i+3]] = param1 < param2;
                    break;
                case Equals:
                    clone[clone[i+3]] = param1 == param2;
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
                        // i+=3;
                        break;
                case Input:
                case Output:
                    i+=2;
                    break;
                default:
                    break;
            }
        }

        return output;
    };
};

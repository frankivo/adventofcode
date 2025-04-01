#include "day05.hpp"

#include <boost/algorithm/string.hpp>

// Heavily inspired on https://github.com/boriskurikhin/AdventOfCode2019/blob/master/Day5.java

int Day05::part1() const
{
    return solve(input, 1);
}

int Day05::part2() const
{
    return solve(input, 5);
};

int Day05::solve(const std::vector<int> inst, const int systemid, const int signal) const {
    std::vector<int> clone(inst.begin(), inst.end());

    auto i = 0;
    auto opcode = Start;
    auto switchme = true;

    while (opcode != Exit) {
        auto cur = clone[i];

        opcode = opcodes(cur % 100);
        auto& param1 = (modi(cur / 100 % 10) == modi::Position) ? clone[clone[i+1]] : clone[i+1];
        auto& param2 = ( modi(cur / 1000 % 10) == modi::Position) ? clone[clone[i+2]] : clone[i+2];
        auto& param3 = clone[clone[i+3]];

        switch (opcode) {
            case opcodes::Add:
                clone[clone[i+3]] = param1 + param2;
                break;
            case opcodes::Multiply:
                clone[clone[i+3]] = param1 * param2;
                break;
            case opcodes::Input:
                if (switchme) {
                    param1 = systemid;
                    switchme = false;
                }
                else {
                    param1 = signal;
                }
                break;
            case opcodes::Output:
                if (param1 != 0)
                    return param1;
                break;
            case opcodes::JumpIfTrue:
                if (param1 != 0) {
                    i = param2;
                    continue;
                }
                break;
            case opcodes::JumpIfFalse:
                if (param1 == 0) {
                    i = param2;
                    continue;
                }
                break;
            case opcodes::LessThan:
                param3 = param1 < param2;
                break;
            case opcodes::Equals:
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

const std::vector<int> Day05::parse_data() const {
    auto line = data()[0];
    std::vector<std::string> values;
    std::vector<int> nums;

    boost::split(values, line, boost::is_any_of(","));
    for (auto& i : values) nums.push_back(std::stoi(i));
        return nums;
};

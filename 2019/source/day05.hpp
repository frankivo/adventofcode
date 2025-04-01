#pragma once

#include "day.hpp"

class Day05 : public Day
{

public:
    Day05() : Day(5), input(parse_data()) {};

    int part1() const;

    int part2() const;

    int solve(const std::vector<int> inst, const int systemid, const int signal = -1) const;

private:
    enum opcodes {
        Start, Add, Multiply, Input, Output, JumpIfTrue, JumpIfFalse, LessThan, Equals, Exit = 99,
    };

    enum modi {
        Position, Immediate,
    };

    const std::vector<int> input;

    const std::vector<int> parse_data() const;
};

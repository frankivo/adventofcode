#include "day.hpp"

#include <format>
#include <fstream>

Day::Day(const int number) : number(number) {};

const std::vector<std::string> Day::data() const {
    auto filename = std::format("input/day{:0{}}.txt", number, 2);

    std::vector<std::string> data;
    std::string line;

    std::ifstream infile(filename);
    if (!infile.is_open())
        throw std::invalid_argument("File not found: " + filename);

    while (getline(infile, line))
        data.push_back(line);
    infile.close();

    return data;
};

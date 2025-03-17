#include <iostream>
#include "days.hpp"

int main(const int, const char *argv[]) {
    auto day_no = std::stoi(argv[1]);
    std::cout << "Running day " << day_no << std::endl;

    auto days = Days();
    auto day = days.by_number(day_no);

    if (!day) {
        std::cerr << "No solution found!" << std::endl;
        return 1;
    }

    try {
        auto rp1 = day->part1();
        std::cout << "Result part 1: " << rp1 << std::endl;
        auto rp2 = day->part2();
        std::cout << "Result part 2: " << rp2 << std::endl;
        return 0;
    }
    catch (const std::exception& ex) {
        std::cerr << "Error occured: " << ex.what() << std::endl;
        return -1;
    }
}

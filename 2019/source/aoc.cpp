#include <iostream>
#include "days.h"

int main(int, char *argv[]) {
    auto day_no = std::stoi(argv[1]);
    std::cout << "Running day " << day_no << std::endl;

    auto days = Days();
    auto day = days.by_number(day_no);

    if (!day) {
        std::cerr << "No solution found!" << std::endl;
        return 1;
    }

    auto rp1 = day->part1();
    std::cout << "Result part 1: " << rp1 << std::endl;
    auto rp2 = day->part2();
    std::cout << "Result part 2: " << rp2 << std::endl;
    return 0;
}

#include <iostream>
#include "days.hpp"

void run(const int day_no) {
    std::cout << "Running day " << day_no << std::endl;

    auto days = Days();
    auto day = days.by_number(day_no);

    if (!day)
        throw std::exception("Solution not found!");

    auto rp1 = day->part1();
    std::cout << "Result part 1: " << rp1 << std::endl;
    auto rp2 = day->part2();
    std::cout << "Result part 2: " << rp2 << std::endl;
};

int main(const int, const char *argv[]) {
    try {
        run(std::stoi(argv[1]));
        return 0;
    }
    catch (const std::exception& ex) {
        std::cerr << "Error occured: " << ex.what() << std::endl;
        return -1;
    }
    catch (...) {
        std::cerr << "Unknown error occured!" << std::endl;
        return -2;
    }
};

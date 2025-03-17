#ifndef H_DAY
#define H_DAY

#include <string>
#include <vector>

class Day {
    public:
        Day(const int);
        virtual int part1() const = 0;
        virtual int part2() const = 0;
        virtual ~Day() {};
    protected:
        const std::vector<std::string> data() const;
        const int number;
};

#endif

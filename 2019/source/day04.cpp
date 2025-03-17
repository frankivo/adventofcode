#include "day.hpp"

#include <boost/bind.hpp>
#include <boost/range/irange.hpp>
#include <boost/regex.hpp>
#include <iostream>

class Day04 : public Day
{

public:
    Day04() : Day(4),
        range(boost::irange(256310, 732736)) {};

    int part1() const
    {
        return std::count_if(range.begin(), range.end(), boost::bind(&Day04::valid, this, _1));
    };

    int part2() const
    {
        std::cout << valid_extended(123444) << std::endl;

        return -1;
    };

private:
    const boost::integer_range<int> range;

    bool valid(int password) const
    {
        auto pw = std::to_string(password);
        std::vector<std::string> slices;
        for (auto i = 0; i <= 6 - 2; i++)
            slices.push_back(pw.substr(i, 2));

        bool result_same = std::any_of(slices.begin(), slices.end(), [](std::string pair)
            { return pair[0] == pair[1]; }
        );

        bool result_inc = std::all_of(slices.begin(), slices.end(), [](std::string pair)
            { return static_cast<int>(pair[1]) >= static_cast<int>(pair[0]); }
        );

        return result_same && result_inc;
    };

    bool valid_extended(int password) const
    {
        auto pw = std::to_string(password);

        for (auto i = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{2,})")); i != boost::sregex_iterator(); i++) {
            auto match = *i;
            auto match_str = match.str();
            auto position = match.position();

            std::cout << "Gevonden match: " << match_str << " op positie: " << position << std::endl;
        }

        return false;
    };
};

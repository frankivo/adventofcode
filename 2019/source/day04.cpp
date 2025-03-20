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
        std::cout << valid_extended(112233) << std::endl;
        std::cout << valid_extended(123444) << std::endl;
        std::cout << valid_extended(111122) << std::endl;

        return std::count_if(range.begin(), range.end(), boost::bind(&Day04::valid_extended, this, _1));
    };

private:
    const boost::integer_range<int> range;

    typedef std::pair<int, int> tint;

    bool valid(int password) const
    {
        auto pw = std::to_string(password);
        std::vector<std::string> slices;
        for (auto i = 0; i <= 6 - 2; i++)
            slices.push_back(pw.substr(i, 2));

        bool result_same = std::any_of(slices.begin(), slices.end(), [](auto& pair)
            { return pair[0] == pair[1]; }
        );

        bool result_inc = std::all_of(slices.begin(), slices.end(), [](auto& pair)
            { return static_cast<int>(pair[1]) >= static_cast<int>(pair[0]); }
        );

        return result_same && result_inc;
    };

    bool valid_extended(int password) const
    {
        auto pw = std::to_string(password);

        std::vector<int> gps; // Groups of two
        for (
            auto i = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{1})"));
            i != boost::sregex_iterator();
            i++
        ) gps.push_back(i->position());

        std::vector<tint> gpb; // Larger groups
        for (
            auto i = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{2,})"));
            i != boost::sregex_iterator();
            i++
        ) gpb.push_back(std::pair<int,int>(i->position(), i->str().length()));

        auto has_pair_not_in_group = std::any_of(gps.begin(), gps.end(), [gpb](auto& sml) {
            return std::none_of(gpb.begin(), gpb.end(), [sml](auto& big) {
                return sml >= big.first && sml <= big.first + big.second -1;
            });
        });

        std::vector<std::string> slices;
        for (auto i = 0; i <= 6 - 2; i++)
            slices.push_back(pw.substr(i, 2));

        bool result_inc = std::all_of(slices.begin(), slices.end(), [](auto& pair)
            { return static_cast<int>(pair[1]) >= static_cast<int>(pair[0]); }
        );

        return result_inc && has_pair_not_in_group;
    };
};

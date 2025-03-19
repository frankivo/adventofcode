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

        return -1;
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

        std::vector<tint> big_group_positions;
        auto begin = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{2,})"));
        for (auto i = begin; i != boost::sregex_iterator(); i++)
            big_group_positions.push_back(std::pair<int,int>(i->position(), i->str().length()));

        std::vector<int> small_group_positions;
        begin = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{1})"));
        for (auto i = begin; i != boost::sregex_iterator(); i++)
        small_group_positions.push_back(i->position());

        // auto in_big = std::none_of(begin, small_group_positions.end(), [big_group_positions](auto x) {
        //     bool matched = std::count_if(big_group_positions.begin(), big_group_positions.end(), [x](auto m) {
        //         return x->position() >= m.first && x->position() <= m.first + m.second;
        //     }) > 0;
        //     // std::cout << pw << " :: "<< i->str() << " :: " << i->position() << " :: " << matched << std::endl;
        //     return matched;
        // });

        return false;
    };
};

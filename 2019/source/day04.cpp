#include "day.hpp"

#include <boost/bind.hpp>
#include <boost/range/irange.hpp>
#include <boost/regex.hpp>

class Day04 : public Day
{

public:
    Day04() : Day(4),
        range(boost::irange(256310, 732736)) {};

    int part1() const
    {
        return std::count_if(range.begin(), range.end(), boost::bind(&Day04::valid, this, _1, 1));
    };

    int part2() const
    {
        return std::count_if(range.begin(), range.end(), boost::bind(&Day04::valid, this, _1, 2));
    };

private:
    const boost::integer_range<int> range;

    bool valid(int password, int part) const
    {
        auto pw = std::to_string(password);

        std::vector<std::pair<int, std::string>> slices;
        for (auto i = 0; i <= 6 - 2; i++)
            slices.push_back(std::pair(i, pw.substr(i, 2)));

        // Test "Going from left to right, the digits never decrease"
        bool result_inc = std::all_of(slices.begin(), slices.end(), [](auto& slice)
            { return static_cast<int>(slice.second[1]) >= static_cast<int>(slice.second[0]); }
        );

        if (result_inc == false)
            return false;

        if (part == 1) {
            // Test "Two adjacent digits are the same"
            return std::any_of(slices.begin(), slices.end(), [](auto& slice)
                { return slice.second[0] == slice.second[1]; }
            );
        }

        std::vector<std::pair<int,int>> group; // Repeating digit groups (larger than 2)
        for (
            auto i = boost::sregex_iterator(pw.begin(), pw.end(), boost::regex(R"((.)\1{2,})"));
            i != boost::sregex_iterator();
            i++
        ) group.push_back(std::pair(i->position(), i->str().length()));

        // Test "the two adjacent matching digits are not part of a larger group of matching digits"
        return std::any_of(slices.begin(), slices.end(), [group](auto& slice) {
            if (slice.second[0] != slice.second[1])
                return false;
            return std::none_of(group.begin(), group.end(), [slice](auto& big) {
                return slice.first >= big.first && slice.first <= big.first + big.second -1;
            });
        });
    };
};

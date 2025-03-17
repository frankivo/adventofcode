#include "day.hpp"

#include <unordered_map>
#include <boost/regex.hpp>

class Day03 : public Day {
    public:
        Day03() : Day(3) {};
        int part1() const {
            auto nearest = std::numeric_limits<int>::max();
            for (auto& pos : intersections(wire(0).first, wire(1).first)) {
                auto dist = std::abs(pos.first) + std::abs(pos.second);
                if (dist < nearest)
                    nearest = dist;
            }
            return nearest;
        };

        int part2() const {
            auto wire1 = wire(0);
            auto wire2 = wire(1);

            auto nearest = std::numeric_limits<int>::max();
            for (auto& pos : intersections(wire1.first, wire2.first)) {
                auto dist = wire1.second[pos] + wire2.second[pos];
                if (dist < nearest)
                    nearest = dist;
            }
            return nearest;
        };

    private:
        typedef std::pair<std::string, int> command;
        typedef std::pair<int, int> coordinate;

        struct pair_hash {
            template <class T1, class T2>
            std::size_t operator () (const std::pair<T1,T2> &pair) const {
                return std::hash<T1>{}(pair.first) ^ std::hash<T2>{}(pair.second);
            }
        };

        std::vector<command> cmds(const int wire) const {
            auto line = data()[wire];
            std::vector<command> data;
            boost::regex pattern("([A-Z])([0-9]+)");

            boost::sregex_iterator begin(line.begin(), line.end(), pattern);
            boost::sregex_iterator end;

            for (auto i = begin; i != end; ++i) {
                auto match = *i;
                auto cmd = match[1].str();
                auto val = std::stoi(match[2].str());
                data.push_back(command(cmd, val));
            }

            return data;
        };

        std::pair<std::set<coordinate>, std::unordered_map<coordinate, int, pair_hash>> wire(const int wire) const {
            auto x = 0;
            auto y = 0;
            auto d = 0;
            std::set<coordinate> positions;
            std::unordered_map<coordinate, int, pair_hash> distances;

            for (auto& cmd : cmds(wire)) {
                for (auto i = 0; i < cmd.second; i++) {
                    d++;
                    if (cmd.first == "R")
                        x += 1;
                    if (cmd.first == "L")
                        x -= 1;
                    if (cmd.first == "U")
                        y += 1;
                    if (cmd.first == "D")
                        y -= 1;

                    auto c = coordinate(x, y);
                    positions.insert(c);
                    if (distances.find(c) == distances.end())
                        distances[c] = d;
                }
            }

            std::pair<std::set<coordinate>, std::unordered_map<coordinate, int, pair_hash>> res(positions, distances);
            return res;
        };

        std::vector<coordinate> intersections(const std::set<coordinate> wire1, const std::set<coordinate> wire2) const {
            std::vector<coordinate> duplicates;
            std::set_intersection(
                wire1.begin(), wire1.end(), wire2.begin(), wire2.end(), std::back_inserter(duplicates)
            );
            return duplicates;
        };
};

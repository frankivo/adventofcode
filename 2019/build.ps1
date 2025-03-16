mkdir -force ./target/ | Out-Null
clang++ -Wall -Wextra -std=c++23 ./source/*.cpp -o ./target/aoc.exe


all: clang++

g++: input
	mkdir -p ./target/
	g++ -Wall -Wextra -std=c++23 source/*.cpp -o target/aoc

clang++: input
	mkdir -p ./target/
	clang++ -Wall -Wextra -std=c++23 source/*.cpp -o target/aoc

clean:
	rm -R ./target/

input:
	mkdir -p ./input/

	@for i in $(shell seq 1 25); do \
		url="https://adventofcode.com/2019/day/$${i}/input"; \
		lz=$$(printf %02d $$i); \
		out="input/day$${lz}.txt"; \
		cmd="wget --header="Cookie:$${AOC_COOKIE}" $${url} -O $${out}"; \
		echo $$cmd; \
		$$cmd; \
	done

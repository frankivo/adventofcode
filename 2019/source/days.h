#include "day.h"

#include <map>
#include <memory>

class Days {
	public:
		Days();
		std::unique_ptr<Day> by_number(int day_no);
	private:
		std::map<int, std::unique_ptr<Day>> days;
};

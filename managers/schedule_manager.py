from config.patterns import schedule_pattern
from entities.schedule import Schedule

class ScheduleManager:

    # =============== Construction ============== #
    def __init__(self, table_num):
        self.table_num = table_num
        self.schedule_list = []

    # --------------- add methods --------------- #
    def add_schedule(self):
        self.schedule_list.append(Schedule(0, 0, 1))

    # --------------- get methods --------------- #
    def get_schedule_list(self):
        return self.schedule_list


    # --------------- update methods --------------- #
    def update_schedule(self, all_schedules):
        self.schedule_list.clear()
        for schedule in all_schedules:
            self.schedule_list.append(Schedule(schedule[0], schedule[1], schedule[2]))

    # --------------- remove methods --------------- #
    def remove_schedule_row(self, number_row):
        self.schedule_list.pop(number_row)

    # --------------- general methods --------------- #
    def initialize_schedule(self, path):
        pattern = schedule_pattern

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue

                if match.group(1):  # שורת TagesPlan
                    var_name = match.group(1)
                    if self.is_valid(var_name):
                        program_number = int(match.group(2))
                        self.schedule_list.append(Schedule(0, 0, program_number))
                        # print(f"TagesPlan {var_name}, תוכנית ראשית: {program_number}")

                else:  # שורת initProgWunsch
                    var_name = match.group(3)
                    if self.is_valid(var_name):
                        hour = int(match.group(4))
                        minute = int(match.group(5))
                        program_number = int(match.group(6))
                        self.schedule_list.append(Schedule(hour, minute, program_number))
                        # print(f"{var_name}: שעה {hour}, דקה {minute}, תוכנית {program_number}")

    def is_valid(self, var_name):
        if (
                (self.table_num in [1, 2, 3, 4, 5] and var_name == "sun_thur")
                or (self.table_num == 6 and var_name == "fr")
                or (self.table_num == 7 and var_name == "sa")
        ):
            return True
        return False

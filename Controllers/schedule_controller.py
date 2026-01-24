from PyQt6.QtWidgets import QMessageBox

from Config.patterns import schedule_pattern
from Config.special import get_space


class ScheduleController:
    def __init__(self, view, model):
        self.view = view
        self.model = model
        self.is_copy_sunday = True

        self.view.get_all_channels_method = self.get_all_channels
        self.view.remove_row_method = self.remove_row
        self.view.add_row_method = self.add_row
        self.view.update_schedule_method = self.update_schedule
        self.view.is_copied_sunday_method = self.is_copied_sunday
        self.view.toggle_copy_sunday_method = self.toggle_copy_sunday

    def init_model(self, path):
        pattern = schedule_pattern
        mapping_day = {"sun_thur": [1,2,3,4,5], "sunday":[1], "monday":[2], "tuesday":[3], "wednesday":[4], "thursday":[5], "fr": [6],"sa": [7]}

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue

                if match.group(1):  # שורת TagesPlan
                    var_name = match.group(1)
                    if var_name in ["kipurEve", "kipur", "blink"]:
                        return
                    days = mapping_day[var_name]

                    if not self.is_copy_sunday or (var_name == "monday"):
                        self.is_copy_sunday = False
                    else:
                        self.is_copy_sunday = True
                    # self.is_copy_sunday = False if not self.is_copy_sunday or (var_name == "monday") else True
                    # print(self.is_copy_sunday)
                    # if self.is_valid(var_name):
                    # print(self.is_copy_sunday)
                    program_number = int(match.group(2))
                    for day in days:
                        self.model.add_cell(day, 0, 0, program_number)

                else:  # שורת initProgWunsch
                    var_name = match.group(3)
                    days = mapping_day[var_name]
                    hour = int(match.group(4))
                    minute = int(match.group(5))
                    program_number = int(match.group(6))

                    for day in days:
                        self.model.add_cell(day, hour, minute, program_number)

                    # if self.is_valid(var_name):
                    #     program_number = int(match.group(6))
                        # self.schedule_list.append(Schedule(hour, minute, program_number))
                        # print(f"{var_name}: שעה {hour}, דקה {minute}, תוכנית {program_number}")

    def show_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def get_all_channels(self, table_num):
        return self.model.get_all_channels(table_num)

    def remove_row(self, table_number, table, btn):
        for row_index in range(table.rowCount()):
            if table.cellWidget(row_index, 0) is btn:
                self.model.remove_row(table_number, row_index)
        self.show_view()

    def add_row(self, table_number):
        self.model.add_row(table_number)
        self.show_view()

    def update_schedule(self, is_copy_sunday, table_list):
        # check data
        if not self._check_time(table_list):
            return

        # save data
        self.is_copy_sunday = is_copy_sunday

        # prepare data for model
        data_list = []

        for idx, table in enumerate(table_list):
            if 1 <= idx <= 4 and self.is_copy_sunday:
                for row_num in range(table_list[0].rowCount()):
                    # get time
                    time_edit = table_list[0].cellWidget(row_num, 1)
                    hours = time_edit.time().hour()
                    minutes = time_edit.time().minute()

                    # get number program
                    combo = table_list[0].cellWidget(row_num, 2)
                    num_prog = int(combo.currentText())
                    data_list.append((hours, minutes, num_prog))
                self.model.update_schedule(idx, data_list)
                data_list.clear()
            else:
                for row_num in range(table_list[idx].rowCount()):
                    # get time
                    time_edit = table_list[idx].cellWidget(row_num, 1)
                    hours = time_edit.time().hour()
                    minutes = time_edit.time().minute()

                    # get number program
                    combo = table_list[idx].cellWidget(row_num, 2)
                    num_prog = int(combo.currentText())
                    data_list.append((hours, minutes, num_prog))
                self.model.update_schedule(idx, data_list)
                data_list.clear()
            #


        # self.model.update_schedule(is_copy_sunday, table_list)
        self.show_view()
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    # ============================== Logic ============================== #
    def _check_time(self, table_list):
        for idx, table in enumerate (table_list):      # for each table

            prev = None                     # reset
            current = None                  # reset
            row_count = table.rowCount()    # get row count

            for row in range(row_count):                    # for each row in table
                prev = current
                current = table.cellWidget(row, 1).time()

                # if first iteration or if last iteration
                if prev is None or current is None:
                    continue

                # compare
                if current <= prev:
                    QMessageBox.critical(self.view, "שגיאה", f"יש בעיה עם הזמנים בטבלה מספר {idx + 1}")
                    return False
        return True

    def reset(self):
        self.model.reset()

    def is_copied_sunday(self):
        return self.is_copy_sunday

    def toggle_copy_sunday(self):
        self.is_copy_sunday = not self.is_copy_sunday

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        new_lines = []
        for line in lines:
            if "write schedule here" in line:
                self.add_new_lines(new_lines)
                continue
            if "write schedule order here" in line:
                if self.is_copy_sunday:
                    new_lines.append("\t\tnew WochenPlan(\"time table\", sun_thur, sun_thur, sun_thur, sun_thur, fr, sa, sun_thur);")
                else:
                    new_lines.append("\t\tnew WochenPlan(\"time table\", monday, tuesday, wednesday, thursday, fr, sa, sunday);")
                continue

            new_lines.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)

    def add_new_lines(self, new_lines):
        first_row = True

        # sunday - thursday
        if self.is_copy_sunday:
            table = self.model.all_schedule_tables[0]
            cell_list = table.cell_list                 # get schedule cells
            for cell in cell_list:                      #
                # prepare data
                if cell.prog_num < 10:
                    prog_num = f"0{cell.prog_num}"
                else:
                    prog_num = f"{cell.prog_num}"

                # first row
                if first_row:
                    first_row = False
                    line = f"\t\tTagesPlan sun_thur = new TagesPlan(\"Sun_Thur\", tk.p{prog_num});\n"
                    new_lines.append(line)
                    continue

                # all other rows
                line = f"\t\tsun_thur.initProgWunsch("
                if cell.hour >= 10:
                    line += f"{cell.hour}, "
                else:
                    line += f" {cell.hour}, "

                if cell.minute >= 10:
                    line += f"{cell.minute},  tk.p{prog_num} );\n"
                else:
                    line += f" {cell.minute},  tk.p{prog_num} );\n"

                new_lines.append(line)
            new_lines.append("\n")

        else:
            for i in range(5):
                table = self.model.all_schedule_tables[i]   # get table
                cell_list = table.cell_list                 # get schedule cells
                day = {0: "sunday", 1: "monday", 2: "tuesday", 3: "wednesday", 4: "thursday"}.get(i, "לא קיים")
                for cell in cell_list:                      #
                    # prepare data
                    if cell.prog_num < 10:
                        prog_num = f"0{cell.prog_num}"
                    else:
                        prog_num = f"{cell.prog_num}"

                    # first row
                    if first_row:
                        first_row = False
                        line = f"\t\tTagesPlan {day} = new TagesPlan(\"{day}\", tk.p{prog_num});\n"
                        new_lines.append(line)
                        continue

                    # all other rows
                    line = f"\t\t{day}.initProgWunsch("
                    if cell.hour >= 10:
                        line += f"{cell.hour}, "
                    else:
                        line += f" {cell.hour}, "

                    if cell.minute >= 10:
                        line += f"{cell.minute},  tk.p{prog_num} );\n"
                    else:
                        line += f" {cell.minute},  tk.p{prog_num} );\n"
                        print(f"{day}, {prog_num}")
                    new_lines.append(line)
                new_lines.append("\n")
                first_row = True


        # friday
        new_lines.append("\n")

        friday_table = self.model.all_schedule_tables[5]
        first_row = True
        cell_list = friday_table.cell_list
        for cell in cell_list:
            # make program number for the line
            if cell.prog_num < 10:
                prog_num = f"0{cell.prog_num}"
            else:
                prog_num = f"{cell.prog_num}"

            # if it's the first row
            if first_row:
                first_row = False
                line = f"\t\tTagesPlan fr = new TagesPlan(\"Fr\",  tk.p{prog_num});\n"
                new_lines.append(line)
                continue

            #
            line = (f"\t\tfr.initProgWunsch("
                    f"{get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                    f"{get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
            new_lines.append(line)
        new_lines.append("\n")

        # saturday
        saturday_table = self.model.all_schedule_tables[6]
        first_row = True
        cell_list = saturday_table.cell_list
        for cell in cell_list:
            # make program number for the line
            if cell.prog_num < 10:
                prog_num = f"0{cell.prog_num}"
            else:
                prog_num = f"{cell.prog_num}"

            # if it's the first row
            if first_row:
                first_row = False
                line = f"\t\tTagesPlan sa = new TagesPlan(\"Sat\",  tk.p{prog_num});\n"
                new_lines.append(line)
                continue

            #
            line = (f"\t\tsa.initProgWunsch("
                    f"{get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                    f"{get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
            new_lines.append(line)
        new_lines.append("\n")



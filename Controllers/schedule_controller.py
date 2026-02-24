from PyQt6.QtWidgets import QMessageBox

import Config
from Managers.load_data_manager import LoadDataManager


class ScheduleController:
    def __init__(self, view, model):
        # Fields
        self.view               = view
        self.model              = model
        self.is_copy_sunday     = True

        # Set View Methods
        self.view.fetch_all_channels_method     = self.fetch_all_channels
        self.view.remove_row_method             = self.remove_row
        self.view.add_row_method                = self.add_row
        self.view.update_schedule_method        = self.update_schedule
        self.view.is_copied_sunday_method       = self.is_copied_sunday
        self.view.toggle_copy_sunday_method     = self.toggle_copy_sunday

    def init_model(self, path):
        data = LoadDataManager.load_schedule_data(path, self.is_copy_sunday)
        for day, hour, minute, program_number in data:
            self.model.create_cell(day, hour, minute, program_number)

    def show_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def fetch_all_channels(self, table_num):
        """
        This method return all the channels of the table number
        """
        return self.model.get_all_channels(table_num)

    def remove_row(self, table_number, table, btn):
        """
        This method removes a row (time & program number) from the table
        """
        for row_index in range(table.rowCount()):
            if table.cellWidget(row_index, 0) is btn:
                table.removeRow(row_index)
                self.model.remove_cell_from_table(table_number, row_index)
        # self.show_view()

    def add_row(self, table_number):
        """
        This method add new row (time & program number) to the table
        """
        self.model.add_cell_to_table(table_number)
        self.show_view()

    def update_schedule(self, is_copy_sunday, table_list):
        """
        This method update the model with the new data
        """
        # check data
        if not self._check_time(table_list):
            return

        # save data
        self.is_copy_sunday     = is_copy_sunday
        data_list               = []

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
                self.model.set_new_cells(idx, data_list)
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
                self.model.set_new_cells(idx, data_list)
                data_list.clear()

        self.show_view()
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    # ============================== Logic ============================== #
    def _check_time(self, table_list):
        """
        This method check if the logic of the time is valid
        """
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
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_schedule_model()

    def is_copied_sunday(self):
        """
        This method check if the model copied the schedule
        """
        return self.is_copy_sunday

    def toggle_copy_sunday(self):
        """
        This method swap the value of the variable "is_copy_sunday"
        """
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
                    f"{Config.special.get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                    f"{Config.special.get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
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
                    f"{Config.special.get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                    f"{Config.special.get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
            new_lines.append(line)
        new_lines.append("\n")



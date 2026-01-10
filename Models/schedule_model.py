class CellScheduleModel:
    def __init__(self, hour: int, minute: int, prog_num: int):
        self.hour = hour
        self.minute = minute
        self.prog_num = prog_num

class TableScheduleModel:
    def __init__(self, table_num):
        self.cell_list = []
        self.table_num = table_num

    def add_cell(self, hour, minute, prog_num):
        self.cell_list.append(CellScheduleModel(hour, minute, prog_num))

    def get_all_channels(self):
        return self.cell_list

class ScheduleModel:
    def __init__(self):
        self.all_schedule_tables = []
        for i in range(7):
            self.all_schedule_tables.append(TableScheduleModel(i+1))

    def add_cell(self, table_num, hour, minute, prog_num):
        for table in self.all_schedule_tables:
            if table.table_num == table_num:
                table.add_cell(hour, minute, prog_num)
                return

    def get_all_channels(self, card_number):
        for table in self.all_schedule_tables:
            if table.table_num == card_number:
                return table.get_all_channels()
        return []

    def remove_row(self, table_number, row):
        for table in self.all_schedule_tables:
            if table.table_num == table_number:
                table.cell_list.pop(row)

    def add_row(self, table_num):
        for table in self.all_schedule_tables:
            if table.table_num == table_num:
                table.add_cell(0, 0, 1)

    def update_schedule(self, is_copy_sunday, table_list):
        if not is_copy_sunday:
            return

        for idx, table in enumerate(table_list):
            self.all_schedule_tables[idx].cell_list.clear() # clear previous cell list
            if is_copy_sunday and 1 <= idx <= 4:
                for row_num in range(table_list[0].rowCount()):
                    time_edit = table_list[0].cellWidget(row_num, 1)
                    hours = time_edit.time().hour()
                    minutes = time_edit.time().minute()

                    combo = table_list[0].cellWidget(row_num, 2)
                    num_prog = int(combo.currentText())

                    self.all_schedule_tables[idx].add_cell(hours, minutes, num_prog)
                continue
            for row_num in range(table.rowCount()):
                time_edit = table.cellWidget(row_num, 1)
                hours = time_edit.time().hour()
                minutes = time_edit.time().minute()

                combo = table.cellWidget(row_num, 2)
                num_prog = int(combo.currentText())

                self.all_schedule_tables[idx].add_cell(hours, minutes, num_prog)




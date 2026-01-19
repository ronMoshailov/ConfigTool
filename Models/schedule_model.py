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

    # ============================== CRUD ============================== #
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

    def update_schedule(self, idx, data_list):
        self.all_schedule_tables[idx].cell_list.clear()
        for (hour, minute, prog_num) in data_list:
            self.all_schedule_tables[idx].add_cell(hour, minute, prog_num)

    # ============================== Logic ============================== #
    def reset(self):
        self.all_schedule_tables.clear()
        for i in range(7):
            self.all_schedule_tables.append(TableScheduleModel(i+1))



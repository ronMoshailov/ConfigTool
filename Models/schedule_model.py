class _CellScheduleModel:
    def __init__(self, hour: int, minute: int, prog_num: int):
        self.hour = hour
        self.minute = minute
        self.prog_num = prog_num

class _TableScheduleModel:
    def __init__(self, table_num):
        self.cell_list = []
        self.table_num = table_num

    def add_cell(self, hour, minute, prog_num):
        """
        This method add cell to the table
        """
        self.cell_list.append(_CellScheduleModel(hour, minute, prog_num))

    def get_all_channels(self):
        """
        This method return all cells that belong to this table
        """
        return self.cell_list

class ScheduleModel:
    def __init__(self):
        self.all_schedule_tables = []
        for i in range(7):
            self.all_schedule_tables.append(_TableScheduleModel(i+1))

    # ============================== CRUD ============================== #
    def create_cell(self, table_num, hour, minute, prog_num):
        """
        This method create a cell and append it to the table
        """
        for table in self.all_schedule_tables:
            if table.table_num == table_num:
                table.add_cell(hour, minute, prog_num)
                return

    def get_all_channels(self, table_num):
        """
        This method return all cells that belong to this table
        """
        for table in self.all_schedule_tables:
            if table.table_num == table_num:
                return table.get_all_channels()
        return []

    def remove_cell_from_table(self, table_number, row):
        """
        This method removes a cell from the table
        """
        for table in self.all_schedule_tables:
            if table.table_num == table_number:
                table.cell_list.pop(row)

    def add_cell_to_table(self, table_num):
        """
        This method add empty cell to the table
        """
        for table in self.all_schedule_tables:
            if table.table_num == table_num:
                table.add_cell(0, 0, 1)

    def set_new_cells(self, idx, data_list):
        """
        This method clear the cells in the table and append them back with new data
        """
        self.all_schedule_tables[idx].cell_list.clear()
        for (hour, minute, prog_num) in data_list:
            self.all_schedule_tables[idx].add_cell(hour, minute, prog_num)

    # ============================== Logic ============================== #
    def reset_schedule_model(self):
        """
        This method clear all the data in the model
        """
        self.all_schedule_tables.clear()
        for i in range(7):
            self.all_schedule_tables.append(_TableScheduleModel(i+1))



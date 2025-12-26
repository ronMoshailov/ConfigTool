class CellScheduleModel:
    def __init__(self, hour, minute, prog_num):
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

    def update_schedule(self, is_copy_sunday):
        if not is_copy_sunday:
            return

        sunday_table = self.all_schedule_tables[0]
        for i in range(1, 5):
            for i in range(1, 5):
                target = self.all_schedule_tables[i]
                target.cell_list.clear()

                for cell in sunday_table.cell_list:
                    target.add_cell(cell.hour, cell.minute, cell.prog_num)

        # for i in range(7):
        #     class_table = self.all_schedule_tables[i]
        #     arg_table = table_list[i]
        #
        #     class_table.cell_list.clear()
        #
        #     for row in range(arg_table.rowCount()):
        #
        #         time_edit = arg_table.cellWidget(row, 1)  # עמודה 0 = QTimeEdit
        #         combo_prog = arg_table.cellWidget(row, 2)  # עמודה 1 = QComboBox
        #
        #         hour = time_edit.time().hour()
        #         minute = time_edit.time().minute()
        #         program_num = int(combo_prog.currentText())
        #
        #         class_table.add_cell(hour, minute, program_num)



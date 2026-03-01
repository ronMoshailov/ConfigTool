from PyQt6.QtWidgets import QMessageBox

import Config
from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


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
        self.view.toggle_copy_sunday_method     = self.toggle_copy_sunday
        self.view.set_new_cells_method          = self.set_new_cells

    def init_model(self, path):
        data = LoadDataManager.load_schedule_data(path, self.is_copy_sunday)
        for day, hour, minute, program_number in data:
            self.model.create_cell(day, hour, minute, program_number)

    def show_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def set_new_cells(self, idx, data_list):
        self.model.set_new_cells(idx, data_list)

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


    # ============================== Logic ============================== #

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_schedule_model()


    def toggle_copy_sunday(self):
        """
        This method swap the value of the variable "is_copy_sunday"
        """
        self.is_copy_sunday = not self.is_copy_sunday

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        """
        This method write the data from the model to the project
        """
        code = WriteDataManager.create_schedule_init_tk1_code(path, self.is_copy_sunday, self.model.all_schedule_tables)

        with open(path, 'w', encoding='utf-8') as file:
            file.writelines(code)

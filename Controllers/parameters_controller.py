import re

from PyQt6.QtWidgets import QCheckBox

from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class ParametersTaController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

        # Data
        self.all_images = None

        # Set View Methods
        self.view.update_parameters_method  = self.update_parameters

        # Set Main Controller Methods
        self.get_sp_by_image_method         = None

    def init_model(self, path, images_len):
        data = LoadDataManager.load_parameters_ta(path, images_len)
        for index, min_list, max_list, type_list, str, cycle, is_active in data:
            self.model.add_program(index, min_list, max_list, type_list, str, cycle, is_active)

    def show_view(self, all_images):
        self.all_images = all_images
        self.view.show_view(self.all_images, self.model.get_parameters())

    def hide_view(self):
        self.view.hide_view()

    # ============================== CRUD ============================== #
    def update_parameters(self, tbl):
        """
        This method update the parameters model from the data in the table
        """
        total_rows  = tbl.rowCount() - 2
        total_cols  = tbl.columnCount()
        images_list = []

        # col 0 - skip

        # col 1 - get image list for the comments
        for col in range(0, total_cols):
            line = tbl.cellWidget(1, col)
            value = line.text()
            if value in images_list:
                break
            images_list.append(value)

        image_len = len(images_list)

        # Get image list for the comments
        for row_num in range(2, total_rows+2):
            min_list = []
            max_list = []
            type_list = []

            for col in range(0, image_len):
                min_list.append(int(tbl.item(row_num, col).text()))
            for col in range(image_len, 2*image_len):
                max_list.append(int(tbl.item(row_num, col).text()))
            for col in range(2*image_len, 3*image_len):
                type_list.append(int(tbl.item(row_num, col).text()))
            struct   = int(tbl.item(row_num  , 3*image_len   ).text())
            cycle = int(tbl.item(row_num  , 3*image_len+1 ).text())
            to_copy = tbl.cellWidget(row_num, 3*image_len+2).findChild(QCheckBox).isChecked()

            self.model.update_parameters(row_num-2, min_list, max_list, type_list, struct, cycle, to_copy)

        # Show view
        self.show_view(self.all_images)

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_parameters_model()

    # ============================== Write To File ============================== #
    def write_to_file(self, path_parameters_ta_dst, path_init_tk1, img_list):
        # data
        code = WriteDataManager.create_parameters_ta_code(path_parameters_ta_dst, img_list, self.model.get_parameters(), self.model.is_equal)
        WriteDataManager.write_code(path_parameters_ta_dst, code)

        # data
        code = WriteDataManager.create_parameters_ta_init_tk1_code(path_init_tk1, self.model.get_program, self.get_sp_by_image_method)
        WriteDataManager.write_code(path_init_tk1, code)


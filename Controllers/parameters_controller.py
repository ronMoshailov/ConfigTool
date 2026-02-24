import re

from PyQt6.QtWidgets import QCheckBox

from Managers.load_data_manager import LoadDataManager


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
        code = []

        # update tk1.java file
        with open(path_parameters_ta_dst, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write parameters here" in line:
                self.add_lines(code, img_list)
                continue

            code.append(line)

        with open(path_parameters_ta_dst, 'w', encoding='utf-8') as f:
            f.writelines(code)
        #########################
        # data
        code = []

        # update tk1.java file
        with open(path_init_tk1, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write sy here" in line:
                self.add_sy_code(code)
                continue

            code.append(line)

        with open(path_init_tk1, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def add_lines(self, code, img_list):
        # data
        line = ""
        param_length = len(self.model.parameters[0].min_list)
        min_idx = 51
        max_idx = 66
        type_idx = 81
        str_param = 96
        cycle_param = 97

        # ----- add comment index line ----- #
        line += "//												"
        for _ in range(param_length):
            line += f"{min_idx}    "
            min_idx += 1
        line += " "

        for _ in range(param_length):
            line += f"{max_idx}    "
            max_idx += 1
        line += " "

        for _ in range(param_length):
            line += f"{type_idx}    "
            type_idx += 1

        line = line[:-1] + f"{str_param}    {cycle_param}\n"

        # ----- add index code line ----- #
        line += "\tstatic final int[] parameters_indexes = {"

        # write min++
        for _ in range(param_length):
            line += "min++,"
        line += " "

        # write max++
        for _ in range(param_length):
            line += "max++,"
        line += " "

        # write typ++
        for _ in range(param_length):
            line += "typ++,"
        line += "  96,   97 };\n"
        code.append(line)

        # ----- comments ----- #
        line = "//\t                                        "
        for img in img_list:
            line += " " * (6 - len(img))
            line += img
        line += " "
        for img in img_list:
            line += " " * (6 - len(img))
            line += img
        line += " "
        for img in img_list:
            line += " " * (6 - len(img))
            line += img
        code.append(line + "\n")

        # ----- comments ----- #
        all_types = self.model.parameters[0].type_list
        line = "//\t                                        "
        temp = ""
        for idx, value in enumerate (all_types):
            if idx == 0 and value == 1:
                temp += "    SY"
                continue
            elif value == 0:
                temp += f"   ET{idx}"
            elif value == 1:
                temp += f"   FO{idx}"

        line += temp + " " + temp + " " + temp
        code.append(line + "\n")

        # ----- programs ----- #
        first_program_param = self.model.parameters[0]
        first_iteration = True
        for param in self.model.parameters:
            # data
            line = ""

            # write program number
            if param.program_number >= 10:
                line += f"\tstatic int[]       DVI35_P{param.program_number}          ="
            else:
                line += f"\tstatic int[]       DVI35_P0{param.program_number}          ="
            line += " {   "

            # write min
            for min_num in param.min_list:
                if min_num >= 10:
                    line += f"{min_num},   "
                else:
                    line += f" {min_num},   "

            # write max
            for max_num in param.max_list:
                if max_num >= 100:
                    line += f"{max_num},  "
                elif 10 <= max_num <= 99:
                    line += f" {max_num},  "
                else:
                    line += f"  {max_num},  "

            line += "   "
            # write type
            for type_num in param.type_list:
                line += f"{type_num},    "

            # write str
            line = line[:-2]
            line += str(param.str) + ",  "

            # write cycle
            if param.cycle >= 100:
                line += f"{param.cycle}"
            else:
                line += f" {param.cycle}"
            line += " }"

            if param.program_number >= 10:
                if self.model.is_equal(first_program_param, param) and not first_iteration:
                    line += f"; // {param.program_number} - P0{first_program_param.program_number}\n"
                else:
                    line += f"; // {param.program_number} - ACTIVE\n"
            else:
                if self.model.is_equal(first_program_param, param) and not first_iteration:
                    line += f"; //  {param.program_number} - P0{first_program_param.program_number}\n"
                else:
                    line += f"; //  {param.program_number} - ACTIVE\n"

            first_iteration = False
            code.append(line)

    def add_sy_code(self, code):
        for i in range(32):
            # data
            program = self.model.get_program(i)
            sy_num = program.max_list[self.get_sp_by_image_method("A")]

            # write line
            line = "\t\ttk.p"
            if program.program_number >= 10:
                line += f"{program.program_number}"
            else:
                line += f"0{program.program_number}"

            line += "         = new VAProg(  tk , "

            if program.program_number >= 10:
                line += f"\"P{program.program_number}\""
            else:
                line += f"\"P0{program.program_number}\""

            line += " , "

            if program.program_number >= 10:
                line += f"{program.program_number}"
            else:
                line += f" {program.program_number}"

            line += "  ,   "

            if program.cycle >= 100:
                line += f"{program.cycle}"
            elif 10 <= program.cycle <= 99:
                line += f" {program.cycle}"
            else:
                line += f"  {program.cycle}"

            line += " ,   "

            if sy_num >= 100:
                line += f"{sy_num}"
            elif 10 <= sy_num <= 99:
                line += f" {sy_num}"
            else:
                line += f"  {sy_num}"

            line += " ,   "

            if sy_num >= 100:
                line += f"{sy_num}"
            elif 10 <= sy_num <= 99:
                line += f" {sy_num}"
            else:
                line += f"  {sy_num}"

            line += f" ,      999 ,   0 ); //"

            if program.program_number >= 10:
                line += f"{program.program_number} - "
            else:
                line += f" {program.program_number} - "

            if program.is_copied:
                line += "P01\n"
            else:
                line += "ACTIVE\n"

            code.append(line)

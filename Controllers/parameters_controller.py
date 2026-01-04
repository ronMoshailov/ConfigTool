import re

from PyQt6.QtWidgets import QCheckBox


class ParametersTaController:
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.update_parameters_method = self.update_parameters

    def init_model(self, path, images_len):
        pattern = re.compile(r'^static int\[\]\s+DVI35_P(\d+)\s*=\s*\{([^}]*)\}')
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue
                index = int(match.group(1))       # 03
                values_str = match.group(2)  # "0, 0, 0, ..."

                # # הפיכת הטקסט של הערכים לרשימת מספרים
                values = [int(v.strip()) for v in values_str.split(",")]

                min_list = values[0: images_len]
                max_list = values[images_len: 2 * images_len]
                type_list = values[2 * images_len: 3 * images_len]

                str = values[3 * images_len]
                cycle = values[ 3 * images_len + 1]

                self.model.add_program(index, min_list, max_list, type_list, str, cycle)

    def show_view(self, all_images):
        self.view.show_view(all_images, self.model.get_parameters())

    def hide_view(self):
        self.view.hide_view()

    def update_parameters(self, tbl):
        total_rows = tbl.rowCount() - 2
        total_cols = tbl.columnCount()
        images_list = []

        # col 0 - skip

        # col 1 - get image list for the comments
        for col in range(0, total_cols):
            combo = tbl.cellWidget(1, col)
            value = combo.currentText()
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

            # print(f"row_num: {row_num-1}")
            # print(f"min_list: {min_list}")
            # print(f"max_list: {max_list}")
            # print(f"type_list: {type_list}")
            # print(f"str: {struct}")
            # print(f"cycle: {cycle}")
            # print(f"to_copy: {str(to_copy)}")
        # update parameters

        # print(images_list)

    def write_to_file(self, path_parameters_ta_dst):
        # data
        code = []

        # update tk1.java file
        with open(path_parameters_ta_dst, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write parameters here" in line:
                self.add_lines(code)
                continue

            code.append(line)

        with open(path_parameters_ta_dst, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def add_lines(self, code):
        # data
        line = ""
        param_length = len(self.model.parameters[0].min_list)
        min_idx = 51
        max_idx = 66
        type_idx = 81
        str_param = 96
        cycle_param = 97

        # add comment index
        line += "//												"
        for _ in range(param_length):
            line += f"{min_idx}    "
            min_idx += 1

        for _ in range(param_length):
            line += f"{max_idx}    "
            max_idx += 1

        for _ in range(param_length):
            line += f"{type_idx}    "
            type_idx += 1

        line += f"    {str_param}    {cycle_param}\n"

        # write index code
        line += "\tstatic final int[] parameters_indexes = {"

        # write min++
        for _ in range(param_length):
            line += "min++,"
        # write max++
        for _ in range(param_length):
            line += "max++,"
        # write typ++
        for _ in range(param_length):
            line += "typ++,"
        line += "   96,   97 };\n"
        code.append(line)

        # comments
        ########
        #######
        ########

        # programs
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
                    line += f"{max_num},   "
                elif 10 <= max_num <= 99:
                    line += f" {max_num},   "
                else:
                    line += f"  {max_num},   "

            line += "  "
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






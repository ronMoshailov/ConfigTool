from PyQt6.QtWidgets import QCheckBox


class ParametersTaController:
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.update_parameters_method = self.update_parameters

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


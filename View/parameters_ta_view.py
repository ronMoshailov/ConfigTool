from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QWidget, QTableWidget, QAbstractItemView, QTableWidgetItem, QCheckBox, QHBoxLayout, \
    QLineEdit, QHeaderView

from Config.style import parameters_ta_window_style


class ParametersTaView(QWidget):
    def __init__(self):
        super().__init__()

        # Controller Methods
        self.update_parameters_method = None

        # Table
        self.tbl = QTableWidget(self)
        self.tbl.setAlternatingRowColors(True)                                          # allows every even row to be colored in different color
        self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)          # disable the choosing
        self.tbl.verticalHeader().setDefaultSectionSize(35)                             # set height of each row
        self.tbl.horizontalHeader().setVisible(False)                                   # remove column names
        self.tbl.itemChanged.connect(lambda: self.update_parameters_method(self.tbl))

        # Content Layout
        root_layout = QHBoxLayout()
        root_layout.addWidget(self.tbl)

        # Self
        self.setLayout(root_layout)
        self.tbl.setStyleSheet(parameters_ta_window_style)
        self.hide()

    def show_view(self, all_images, parameters_list):
        # Data
        images_len          = len(all_images)
        total_cols          = 3 * images_len + 3
        self.image_combos   = []

        # Reset
        self._create_table(total_cols)

        # Block signals when the table is build
        self.tbl.blockSignals(True)

        # --------- ROW 0 = merged groups ---------
        self._create_row_0(images_len)

        # --------- ROW 1 = Combo Images  ---------
        self._create_row_1(all_images)

        # --------- ROW 2 - ROW 33 = Combo Images  ---------
        self._create_row_2_to_33(parameters_list, images_len)

        # Release signals
        self.tbl.blockSignals(False)

        # Style
        header = self.tbl.horizontalHeader()                                    # get horizontal header
        for col in range(0, self.tbl.columnCount()):
            header.setSectionResizeMode(col, QHeaderView.ResizeMode.Stretch)    # set each column to stretch

        # Show
        self.show()

    def hide_view(self):
        self.hide()

    # ============================== Layout ============================== #
    def _create_table(self, total_cols):
        self.tbl.clear()                            # Clear the table
        self.tbl.setRowCount(0)                     # Remove the rows
        self.tbl.setColumnCount(0)                  # Remove the columns
        self.tbl.setRowCount(2 + 32)                # set how many rows the table will have
        self.tbl.setColumnCount(total_cols)         # set how many columns the table will have
        self.tbl.setVerticalHeaderLabels([""] + ["תמונה"] + [str(num) for num in range(1, 32+1)] + ["שכפל את 1"])   # set headers

    def _create_row_0(self, images_len):
        # min
        item = QTableWidgetItem("min")
        item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item.setBackground(QColor("#D1D1D1"))
        self.tbl.setItem(0, 0, item)
        self.tbl.setSpan(0, 0, 1, images_len)

        # max
        item = QTableWidgetItem("max")
        item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item.setBackground(QColor("#D1D1D1"))
        self.tbl.setItem(0, images_len, item)
        self.tbl.setSpan(0, images_len, 1, images_len)

        # type
        item = QTableWidgetItem("type")
        item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item.setBackground(QColor("#D1D1D1"))
        self.tbl.setItem(0, 2 * images_len, item)
        self.tbl.setSpan(0, 2 * images_len, 1, images_len)

        # str
        item = QTableWidgetItem("options")
        item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item.setBackground(QColor("#D1D1D1"))
        self.tbl.setItem(0, 3 * images_len, item)
        self.tbl.setSpan(0, 3 * images_len, 1, images_len)

    def _create_row_1(self, all_images):
        images_len = len(all_images)

        for i in range (images_len*3):
            line = QLineEdit()
            line.setDisabled(True)
            line.setAlignment(Qt.AlignmentFlag.AlignCenter)
            self.tbl.setCellWidget(1, i, line)

            for image in all_images:
                if int(image.sp) == i % images_len:
                    line.setText(image.image_name)
                    break

        # str
        item_str = QTableWidgetItem("str")
        item_str.setFlags(item_str.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item_str.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item_str.setBackground(QColor("#ECECEC"))
        self.tbl.setItem(1, 3 * images_len, item_str)

        # cycle
        item_cycle = QTableWidgetItem("cycle")
        item_cycle.setFlags(item_cycle.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item_cycle.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item_cycle.setBackground(QColor("#ECECEC"))
        self.tbl.setItem(1, 3 * images_len + 1, item_cycle)

        # checkbox
        item_cycle = QTableWidgetItem("copy")
        item_cycle.setFlags(item_cycle.flags() & ~Qt.ItemFlag.ItemIsEditable)
        item_cycle.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        item_cycle.setBackground(QColor("#ECECEC"))
        self.tbl.setItem(1, 3 * images_len + 2, item_cycle)

    def _create_row_2_to_33(self, parameters_list, images_len):
        row_num = 2

        for parameter in parameters_list:
            self.normalize_list(parameter.min_list, images_len, 0)
            self.normalize_list(parameter.max_list, images_len, 0)
            self.normalize_list(parameter.type_list, images_len, 0)

            # Min
            for i in range(images_len):
                item = QTableWidgetItem(str(parameter.min_list[i]))
                item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                item.setBackground(QColor("#C7F3FF"))
                self.tbl.setItem(row_num, i, item)
            # Max
            for i in range(images_len, 2*images_len):
                item = QTableWidgetItem(str(parameter.max_list[i%images_len]))
                item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                item.setBackground(QColor("#C7C9FF"))
                self.tbl.setItem(row_num, i, item)
            # Type
            for i in range(2*images_len, 3*images_len):
                item = QTableWidgetItem(str(parameter.type_list[i%images_len]))
                item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                item.setBackground(QColor("#DEC7FF"))
                self.tbl.setItem(row_num, i, item)

            # Str
            item = QTableWidgetItem(str(parameter.str))
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            item.setBackground(QColor("#FFC7F8"))
            self.tbl.setItem(row_num, 3*images_len, item)

            # Cycle
            item = QTableWidgetItem(str(parameter.cycle))
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            item.setBackground(QColor("#FFC7D1"))
            self.tbl.setItem(row_num, 3*images_len+1, item)

            # Checkbox
            checkbox = QCheckBox()
            checkbox.setCursor(Qt.CursorShape.PointingHandCursor)
            if parameter.is_copied:
                checkbox.setChecked(True)
            checkbox.stateChanged.connect(lambda _: self.update_parameters_method(self.tbl))

            layout = QHBoxLayout()
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)

            container = QWidget()
            container.setObjectName("container")
            container.setLayout(layout)

            self.tbl.setCellWidget(row_num, 3 * images_len + 2, container)

            row_num+=1

    # ============================== Logic ============================== #
    def normalize_list(self, lst, target_len, fill_value=0):
        if len(lst) < target_len:
            lst.extend([fill_value] * (target_len - len(lst)))
        elif len(lst) > target_len:
            del lst[target_len:]


#######################################################################################################
# The table run the method "update_parameters_method" when an item change value.                      #
# "update_parameters_method" update all the cells even if just one cell changed.                      #
# It's bad complexity, but I already had this method ready so it's not worth to write another method. #
# The maximum rows that this table will have is 34.                                                   #
# The maximum columns that this table will have is not more than 30, and it's extremely unusual.      #
# So because the table not be more than 34*30 I don't want to write new method.                       #
#######################################################################################################
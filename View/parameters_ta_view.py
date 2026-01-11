from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QWidget, QTableWidget, QAbstractItemView, QVBoxLayout, QTableWidgetItem, QComboBox, \
    QCheckBox, QHBoxLayout, QPushButton

from Config.style import parameters_ta_window_style


class ParametersTaView(QWidget):
    def __init__(self):
        super().__init__()

        # =============== Controller Methods =============== #
        self.update_parameters_method = None

        # =============== Controller Methods =============== #

        self.tbl = QTableWidget(self)
        self.tbl.setAlternatingRowColors(True)                  # allows every even row to be colored in different color
        # self.tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)       # disable the focus
        self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)  # disable the choosing
        self.tbl.verticalHeader().setDefaultSectionSize(35)     # set height of each row
        self.tbl.horizontalHeader().setDefaultSectionSize(80)     # set height of each row
        self.tbl.horizontalHeader().setVisible(False)           # remove column names

        btn = QPushButton("עדכן")
        btn.clicked.connect(lambda: self.update_parameters_method(self.tbl))

        btn_layout = QVBoxLayout()
        btn_layout.addWidget(btn)
        btn_layout.addStretch()

        root_layout = QHBoxLayout()
        root_layout.addWidget(self.tbl)
        root_layout.addLayout(btn_layout)

        self.setLayout(root_layout)
        self.tbl.setStyleSheet(parameters_ta_window_style)
        self.hide()

    def show_view(self, all_images, parameters_list):

        self.tbl.clear()  # מוחק את תוכן הטבלה (items) וגם כותרות
        self.tbl.setRowCount(0)
        self.tbl.setColumnCount(0)  # אם רוצים לאפס גם את העמודות

        # data
        all_images_names = [img.image_name for img in all_images]
        images_len = len(all_images_names)
        total_cols = 3 * images_len + 3
        self.image_combos = []

        # style
        self.tbl.setRowCount(2 + 32)                 # set how many rows the table will have
        self.tbl.setColumnCount(total_cols)              # set how many columns the table will have

        # set vertical header
        self.tbl.setVerticalHeaderLabels([""] + ["תמונה"] + [str(num) for num in range(1, 32+1)] + ["שכפל את 1"])       # set headers

        # --------- ROW 0 = merged groups ---------
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

        # --------- ROW 1 = Combo Images  ---------
        for i in range (images_len*3):
            combo = QComboBox()
            combo.addItems(all_images_names)
            self.image_combos.append(combo)

            self.tbl.setCellWidget(1, i, combo)
            for image in all_images:
                if int(image.sp) == i % images_len:
                    combo.setCurrentText(image.image_name)
                    break
            combo.currentIndexChanged.connect(lambda _, idx=i: self.sync_combos(idx))
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

        # --------- ROW 2 - ROW 33 = Combo Images  ---------
        row_num = 2

        for parameter in parameters_list:
            self.normalize_list(parameter.min_list, images_len, 0)
            self.normalize_list(parameter.max_list, images_len, 0)
            self.normalize_list(parameter.type_list, images_len, 0)

            # Min
            for i in range(images_len):
                item = QTableWidgetItem(str(parameter.min_list[i]))
                print(i)
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
            # checkbox.stateChanged.connect(lambda state, tbl=self.tbl, r=row_num: self.on_state_changed(state, tbl, r))
            checkbox.setCursor(Qt.CursorShape.PointingHandCursor)
            if parameter.is_copied:
                checkbox.setChecked(True)
                # for col in range(self.tbl.columnCount()):
                #     item = self.tbl.item(row_num, col)
                #     if item:
                #         item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEnabled)

            layout = QHBoxLayout()
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)

            container = QWidget()
            container.setObjectName("container")
            container.setLayout(layout)

            self.tbl.setCellWidget(row_num, 3 * images_len + 2, container)

            row_num+=1

        self.show()

    def hide_view(self):
        self.hide()

    def normalize_list(self, lst, target_len, fill_value=0):
        if len(lst) < target_len:
            lst.extend([fill_value] * (target_len - len(lst)))
        elif len(lst) > target_len:
            del lst[target_len:]

    def sync_combos(self, index):
        """Synchronize comboboxes by column across groups."""
        images_len = len(self.image_combos) // 3

        base_combo = self.image_combos[index]
        value = base_combo.currentText()

        # מציאת העמדה בתוך הקבוצה
        col = index % images_len

        # עדכון כל הקומבואים עם אותו col חוץ מהקומבו ששונה
        for i, combo in enumerate(self.image_combos):
            if i % images_len == col and i != index:
                combo.blockSignals(True)
                combo.setCurrentText(value)
                combo.blockSignals(False)

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
            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            container.setLayout(layout)
            checkbox = QCheckBox()
            checkbox.setCursor(Qt.CursorShape.PointingHandCursor)  # סמן יד כשהעכבר מעל

            # יצירת container לעטיפה ולמרכוז
            container = QWidget()
            container.setObjectName("container")
            layout = QHBoxLayout(container)
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)
            container.setLayout(layout)

            # עיצוב מודרני עם צבעים עדינים, פינות מעוגלות ואפקט hover

            # עיצוב ה-checkbox עצמו (indicator) במראה מודרני


            self.tbl.setCellWidget(row_num, 3 * images_len + 2, container)

            row_num+=1

        self.show()


    def hide_view(self):
        self.hide()

    def sync_combos(self, index):
        """Synchronize comboboxes separated by images_len."""
        images_len = len(self.image_combos) // 3

        base_combo = self.image_combos[index]
        value = base_combo.currentText()

        linked_indices = []

        # קדימה
        if index + images_len < len(self.image_combos):
            linked_indices.append(index + images_len)

        # אחורה
        if index - images_len >= 0:
            linked_indices.append(index - images_len)

        # לעדכן את אלה המקושרים
        for idx in linked_indices:
            combo = self.image_combos[idx]

            # מניעת לולאת אירועים (מכיוון שהעדכון יפעיל שוב את signal)
            combo.blockSignals(True)
            combo.setCurrentText(value)
            combo.blockSignals(False)

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QLineEdit, QTableWidget, QComboBox, QCheckBox, QHeaderView

import Config

class MoveView(QWidget):
    def __init__(self):
        super().__init__()

        # Table
        self.tbl = QTableWidget(0, 5, self)

        # Headers
        self.tbl.setHorizontalHeaderLabels(["מחיקה", "שם מופע", "סוג", "מופע ראשי", "מינימום ירוק"])

        header = self.tbl.horizontalHeader() # get horizontal header
        header.setSectionResizeMode(0, QHeaderView.ResizeMode.Fixed) # set fixed width to column 0

        self.tbl.setColumnWidth(0, 80) # set column to width of 80px

        for col in range(1, self.tbl.columnCount()):
            header.setSectionResizeMode(col, QHeaderView.ResizeMode.Stretch) # set each column to stretch

        self.tbl.verticalHeader().setVisible(False)
        self.tbl.verticalHeader().setDefaultSectionSize(100)

        # Controller methods
        self.remove_move_method = None
        self.update_detectors_method = None
        self.update_name_method = None
        self.update_type_method = None
        self.update_min_green_method = None
        self.update_main_method = None

        # button
        add_detector_btn = QPushButton("הוסף מופע")
        add_detector_btn.setObjectName("add_button")

        # layout
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.tbl)
        self.root_layout.addWidget(add_detector_btn)

        self.setLayout(self.root_layout)

        self.setStyleSheet(Config.style.move_panel_style)

        self.hide()

    def show_view(self, move_list, all_types):
        """
        Show the 'set move' panel.

        :return: None
        """
        # self._show_scroll_bar(detector_list)
        self.tbl.setRowCount(0)

        # fill table
        for idx, move in enumerate (move_list):
            move_name      = move.name
            move_type      = move.type
            move_is_main   = move.is_main
            move_min_green = move.min_green

            self.tbl.insertRow(self.tbl.rowCount())

            # remove (col 0)
            remove_btn = QPushButton("X")
            remove_btn.setObjectName("remove_button")

            remove_btn.clicked.connect(lambda _, btn=remove_btn: self.remove_move_method(self.tbl, btn))
            self.tbl.setCellWidget(idx, 0, remove_btn)


            # name (col 1)
            line_edit = QLineEdit()
            line_edit.setText(move_name)  # הערך ההתחלתי
            line_edit.editingFinished.connect(lambda le=line_edit, m=move: self.update_name_method(m, le.text()))
            self.tbl.setCellWidget(idx, 1, line_edit)

            # type (col 2)
            combo = QComboBox()
            combo.addItems(all_types)
            combo.setCurrentText(move_type)
            combo.currentTextChanged.connect(lambda text, m=move: self.update_type_method(text, m))
            self.tbl.setCellWidget(idx, 2, combo)

            # add detector name (col 3)
            checkbox = QCheckBox()
            checkbox.setChecked(move_is_main)
            checkbox.stateChanged.connect(lambda state, m=move: self.update_main_method(m, state))

            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)

            self.tbl.setCellWidget(idx, 3, container)

            # min green (col 4)
            line_edit = QLineEdit()
            line_edit.setText(str(move_min_green))  # הערך ההתחלתי
            line_edit.editingFinished.connect(lambda le=line_edit, m=move: self.update_min_green_method(m, le.text()))
            self.tbl.setCellWidget(idx, 4, line_edit)

        self.show()

    def hide_view(self):
        self.hide()


from functools import partial

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QLineEdit, QTableWidget, QComboBox, \
    QCheckBox, QHeaderView, QMessageBox

import Config

class MoveView(QWidget):
    def __init__(self):
        super().__init__()

        # Controller Methods
        self.add_move_method            = None
        self.remove_move_method         = None
        self.rename_move_method         = None
        self.update_type_method         = None
        self.update_min_green_method    = None
        self.update_main_method         = None

        # Table
        self.tbl = QTableWidget(0, 5, self)
        self.tbl.setHorizontalHeaderLabels(["מחיקה", "שם מופע", "סוג", "מופע ראשי", "מינימום ירוק"])
        self.tbl.setColumnWidth(0, 90)
        self.tbl.verticalHeader().setVisible(False)
        self.tbl.verticalHeader().setDefaultSectionSize(60)
        self.tbl.setObjectName("RootTable")

        # Headers
        header = self.tbl.horizontalHeader()                                    # get horizontal header
        header.setSectionResizeMode(0, QHeaderView.ResizeMode.Fixed)            # set fixed width to column 0
        for col in range(1, self.tbl.columnCount()):
            header.setSectionResizeMode(col, QHeaderView.ResizeMode.Stretch)    # set each column to stretch

        # Add Move Button
        add_detector_btn = QPushButton("הוסף מופע")
        add_detector_btn.clicked.connect(lambda: self.add_move_method())
        add_detector_btn.setObjectName("add_button")

        # Root Layout
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.tbl)
        self.root_layout.addWidget(add_detector_btn)

        # Self
        self.setLayout(self.root_layout)
        self.setObjectName("RootWidget")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setStyleSheet(Config.style.move_panel_style)
        self.hide()

    def show_view(self, move_list, all_types):
        # Clear Table Rows
        self.tbl.setRowCount(0)

        # Fill Table
        for idx, move in enumerate (move_list):
            # Data
            move_name      = move.name
            move_type      = move.type
            move_is_main   = move.is_main
            move_min_green = move.min_green

            # Add New Row
            self.tbl.insertRow(self.tbl.rowCount())

            # Remove Button (col 0)
            remove_btn = QPushButton("X")
            remove_btn.setObjectName("remove_button")
            remove_btn.clicked.connect(partial(self.remove_move_method, self.tbl, remove_btn))
            self.tbl.setCellWidget(idx, 0, remove_btn)

            # move name (col 1)
            line_edit = QLineEdit()
            line_edit.setText(move_name)
            self.handle_rename(line_edit, move_name)
            # line_edit.editingFinished.connect(self.handle_rename(line_edit, move_name))
            self.tbl.setCellWidget(idx, 1, line_edit)

            # type (col 2)
            combo = QComboBox()
            combo.addItems(all_types)
            combo.setCurrentText(move_type)
            combo.currentTextChanged.connect(lambda text, m=move.name: self.update_type_method(m, text))
            combo.wheelEvent = lambda event: None
            self.tbl.setCellWidget(idx, 2, combo)

            # is main (col 3)
            checkbox = QCheckBox()
            checkbox.setChecked(move_is_main)
            checkbox.stateChanged.connect(lambda state, m=move.name: self.update_main_method(m, state))
            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)
            self.tbl.setCellWidget(idx, 3, container)

            # min green (col 4)
            line_edit = QLineEdit()
            line_edit.setText(str(move_min_green))
            self.handle_min_green_update(line_edit, move_name)
            # line_edit.editingFinished.connect(lambda le=line_edit, m=move.name: self.update_min_green_method(m, le.text()))
            self.tbl.setCellWidget(idx, 4, line_edit)

        self.show()

    def hide_view(self):
        self.hide()

    def handle_rename(self, line_edit, move_name):
        def handler():
            error = self.rename_move_method(move_name, line_edit.text())
            if error:
                QMessageBox.critical(self, "שגיאה", error)

        line_edit.editingFinished.connect(handler)

    def handle_min_green_update(self, line_edit, move_name):
        def handler():
            error = self.update_min_green_method(move_name, line_edit.text())
            if error:
                QMessageBox.critical(self, "שגיאה", error)

        line_edit.editingFinished.connect(handler)

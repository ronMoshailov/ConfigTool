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

        # Data
        self.tbl        = None

        # Table
        self.create_table()

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
        for idx, move in enumerate(move_list):
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
            self.handle_remove_move(remove_btn, move_name)
            self.tbl.setCellWidget(idx, 0, remove_btn)

            # move name (col 1)
            line_edit = QLineEdit()
            line_edit.setText(move_name)
            self.handle_rename(line_edit, move_name)
            self.tbl.setCellWidget(idx, 1, line_edit)

            # type (col 2)
            combo = QComboBox()
            combo.addItems(all_types)
            combo.setCurrentText(move_type)
            self.handle_change_type(combo, move.name)
            combo.wheelEvent = lambda event: None
            self.tbl.setCellWidget(idx, 2, combo)

            # is main (col 3)
            checkbox = QCheckBox()
            checkbox.setChecked(move_is_main)
            self.handle_main_change(checkbox, move.name)
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
            self.tbl.setCellWidget(idx, 4, line_edit)

        self.show()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ============================== Create ============================== #
    def create_table(self):
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

    # ============================== Handler ============================== #
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

    def handle_remove_move(self, btn, move_name):
        def handler():
            self.remove_move_method(move_name)
        btn.clicked.connect(handler)

    def handle_change_type(self, combo, move_name):
        def handler():
            self.update_type_method(move_name, combo.currentText())
        combo.currentTextChanged.connect(handler)

    def handle_main_change(self, checkbox, move_name):
        def handler(state):
            self.update_main_method(move_name, state)
        checkbox.stateChanged.connect(handler)


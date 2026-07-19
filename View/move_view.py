from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QTableWidget, QHeaderView

from Utilities.exceptions import InvalidValue
from Utilities.style import move_panel_style

from Utilities.Enum.move_type import MoveType
from View.base_view import BaseView


class MoveView(BaseView):
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
        self.move_list  = None

        # Table
        self.create_table()

        # Add Move Button
        add_detector_btn = self.create_button("הוסף מופע", self.on_add_move_clicked, object_name="add_button")

        # Root Layout
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.tbl)
        root_layout.addWidget(add_detector_btn)

        # Self
        self.setLayout(root_layout)
        self.setObjectName("RootWidget")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setStyleSheet(move_panel_style)
        self.hide()

    def show_view(self, move_list):
        self.move_list = move_list  # set data
        self.tbl.setRowCount(0)     # clear table

        # Fill Table
        for idx, move in enumerate(move_list):
            # Add New Row
            self.tbl.insertRow(self.tbl.rowCount())

            # Remove Button (col 0)
            callback = lambda _, m=move.name: self.on_remove_move_clicked(m)
            remove_btn = self.create_button("X", callback, object_name="remove_button")
            self.tbl.setCellWidget(idx, 0, remove_btn)

            # move name (col 1)
            line_edit = self.create_textbox(move.name)
            self.handle_rename(line_edit, move.name)
            self.tbl.setCellWidget(idx, 1, line_edit)

            # type (col 2)
            all_types = [t.value for t in MoveType]
            callback = lambda m=move.name: self.update_type_method(m, combo.currentText())
            combo = self.create_combo(all_types, callback, disable_wheel_event=True)
            self.tbl.setCellWidget(idx, 2, combo)

            # is main (col 3)
            checkbox = self.create_check_box(is_checked=move.is_main)
            # self.handle_main_change(checkbox, move.name)
            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addWidget(checkbox)
            layout.setAlignment(Qt.AlignmentFlag.AlignCenter)
            layout.setContentsMargins(0, 0, 0, 0)
            self.tbl.setCellWidget(idx, 3, container)

            # min green (col 4)
            line_edit = self.create_textbox(str(move.min_green))
            self.handle_min_green_update(line_edit, move.name)
            self.tbl.setCellWidget(idx, 4, line_edit)

        self.show()

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
                self.show_error(error)

        line_edit.editingFinished.connect(handler)

    def handle_min_green_update(self, line_edit, move_name):
        def handler():
            try:
                self.update_min_green_method(move_name, line_edit.text())
            except InvalidValue as e:
                self.show_error(str(e))
                self.show_view(self.move_list)
        line_edit.editingFinished.connect(handler)

    def handle_main_change(self, checkbox, move_name):
        def handler(state):
            self.update_main_method(move_name, state)
        checkbox.stateChanged.connect(handler)

    def on_add_move_clicked(self):
        self.add_move_method()

    def on_remove_move_clicked(self, move_name):
        self.remove_move_method(move_name)
from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QTableWidget, QTableWidgetItem, QComboBox, \
    QHeaderView, QLineEdit

from Config.style import detector_panel_style


class DetectorView(QWidget):
    def __init__(self):
        super().__init__()

        # Controller Methods
        self.remove_detector_method         = None
        self.add_detector_method            = None
        self.update_variable_name_method    = None
        self.update_detector_type_method    = None
        self.update_move_name_method        = None
        self.update_detector_name_method    = None
        self.update_ext_unit_method         = None

        # Table
        self.tbl = QTableWidget(0, 6, self)
        self.tbl.setHorizontalHeaderLabels(["מחיקה", "שם משתנה", "סוג גלאי", "שם גלאי", "מופע תנועה", "יח' הארכה"])
        self.tbl.setColumnWidth(0, 80)                            # set column to width of 80px
        self.tbl.verticalHeader().setVisible(False)
        self.tbl.verticalHeader().setDefaultSectionSize(60)                     # default row size
        self.tbl.setObjectName("RootTable")

        header = self.tbl.horizontalHeader()                                    # get horizontal header
        header.setSectionResizeMode(0, QHeaderView.ResizeMode.Fixed)            # set fixed width to column 0
        for col in range(1, self.tbl.columnCount()):
            header.setSectionResizeMode(col, QHeaderView.ResizeMode.Stretch)    # set each column to stretch

        # Button
        add_detector_btn = QPushButton("הוסף גלאי")
        add_detector_btn.setObjectName("add_button")
        add_detector_btn.clicked.connect(lambda: self.add_detector_method("Variable Name", "DDetector", "Detector Name", "k0", 0))

        # Button Layout
        btn_layout = QHBoxLayout()
        btn_layout.addWidget(add_detector_btn)

        # Root Layout
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.tbl)
        self.root_layout.addLayout(btn_layout)

        # Self
        self.setLayout(self.root_layout)
        self.setStyleSheet(detector_panel_style)
        self.setObjectName("RootWidget")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.hide()

    def show_view(self, detector_list, all_types, all_moves_names):
        # Data
        self.tbl.setRowCount(0)
        self.all_types = all_types
        self.all_moves_names = all_moves_names

        # Fill Table
        for idx, detector in enumerate (detector_list):
            var_name        = detector.var_name         # str
            class_name      = detector.class_name       # str
            detector_name   = detector.datector_name    # str
            move_name       = detector.move_name        # str
            ext_unit        = detector.ext_unit         # int

            self.tbl.insertRow(self.tbl.rowCount())     # add new row

            # Add Remove Button (col 0)
            remove_btn = QPushButton("X")
            remove_btn.setObjectName("remove_button")
            remove_btn.clicked.connect(lambda _, var=var_name: self.remove_detector_method(var))
            self.tbl.setCellWidget(idx, 0, remove_btn)

            # Add "var name" (col 1)
            variable_line_edit = QLineEdit()
            variable_line_edit.setText(var_name)
            variable_line_edit.editingFinished.connect(lambda le=variable_line_edit, var=var_name: self.update_variable_name_method(var, le.text()))
            self.tbl.setCellWidget(idx, 1, variable_line_edit)

            # Add "class name" (col 2)
            combo = QComboBox()
            combo.addItems(self.all_types)
            combo.setCurrentText(class_name)
            combo.wheelEvent = lambda event: None
            combo.currentIndexChanged.connect(lambda _, c=combo, var=var_name: self.update_detector_type_method(c.currentText(), var))
            self.tbl.setCellWidget(idx, 2, combo)

            # Add "detector name" (col 3)
            line_edit = QLineEdit()
            line_edit.setText(detector_name)
            line_edit.editingFinished.connect(lambda le=line_edit, var=var_name: self.update_detector_name_method(var, le.text()))
            self.tbl.setCellWidget(idx, 3, line_edit)

            # Add "move name" (col 4)
            combo = QComboBox()
            combo.addItems(self.all_moves_names)
            combo.wheelEvent = lambda event: None
            combo.currentIndexChanged.connect(lambda _, c=combo, var=var_name: self.update_move_name_method(c.currentText(), var))

            if move_name in self.all_moves_names:
                combo.setCurrentText(move_name)
            else:
                combo.setCurrentIndex(0)

            self.tbl.setCellWidget(idx, 4, combo)

            # add ext unit (col 5)
            line_edit = QLineEdit()
            line_edit.setText(str(ext_unit))
            line_edit.editingFinished.connect(lambda le=line_edit, var=var_name: self.update_ext_unit_method(var, int(le.text())))
            self.tbl.setCellWidget(idx, 5, line_edit)

        self.show()

    def hide_view(self):
        self.hide()




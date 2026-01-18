from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QTableWidget, QTableWidgetItem, QComboBox, QHeaderView

from Config.style import detector_panel_style


class DetectorView(QWidget):
    def __init__(self):
        super().__init__()

        # Set Controller Methods
        self.remove_detector_method = None
        self.update_detectors_method = None

        # Table
        self.tbl = QTableWidget(0, 6, self)
        self.tbl.setHorizontalHeaderLabels(["מחיקה", "שם משתנה", "סוג גלאי", "שם גלאי", "מופע תנועה", "יח' הארכה"])
        self.tbl.setColumnWidth(0, 80)                            # set column to width of 80px
        self.tbl.verticalHeader().setVisible(False)
        self.tbl.verticalHeader().setDefaultSectionSize(50)                     # default row size

        header = self.tbl.horizontalHeader()                                    # get horizontal header
        header.setSectionResizeMode(0, QHeaderView.ResizeMode.Fixed)            # set fixed width to column 0
        for col in range(1, self.tbl.columnCount()):
            header.setSectionResizeMode(col, QHeaderView.ResizeMode.Stretch)    # set each column to stretch

        # Button
        add_detector_btn = QPushButton("הוסף גלאי")
        add_detector_btn.setObjectName("add_button")
        add_detector_btn.clicked.connect(self.add_detector)

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
        self.hide()

    def show_view(self, detector_list, all_types, all_moves_names):
        # Data
        self.tbl.setRowCount(0)
        self.all_types = all_types
        self.all_moves_names = all_moves_names

        # Fill Table
        for idx, detector in enumerate (detector_list):
            var_name = detector.var_name                # str
            class_name = detector.class_name            # str
            detector_name = detector.datector_name      # str
            move_name = detector.move_name              # str
            ext_unit = detector.ext_unit                # int

            self.tbl.insertRow(self.tbl.rowCount())     # add new row

            # Add Remove Button (col 0)
            remove_btn = QPushButton("X")
            remove_btn.setObjectName("remove_button")
            remove_btn.clicked.connect(lambda _, btn=remove_btn: self.remove_detector_method(self.tbl, btn))
            self.tbl.setCellWidget(idx, 0, remove_btn)

            # Add "var name" (col 1)
            item = QTableWidgetItem(var_name)
            self.tbl.setItem(idx, 1, item)

            # Add "class name" (col 2)
            combo = QComboBox()
            combo.addItems(self.all_types)
            combo.setCurrentText(class_name)
            self.tbl.setCellWidget(idx, 2, combo)

            # Add "detector name" (col 3)
            item = QTableWidgetItem(detector_name)
            self.tbl.setItem(idx, 3, item)

            # Add "move name" (col 4)
            combo = QComboBox()
            combo.addItems(self.all_moves_names)

            if move_name in self.all_moves_names:
                combo.setCurrentText(move_name)
            else:
                combo.setCurrentIndex(0)

            self.tbl.setCellWidget(idx, 4, combo)

            # add ext unit (col 5)
            item = QTableWidgetItem(str(ext_unit))
            self.tbl.setItem(idx, 5, item)

        self.show()

    def hide_view(self):
        self.hide()

    # ============================== CRUD ============================== #
    def add_detector(self):
        idx = self.tbl.rowCount()
        self.tbl.insertRow(idx)

        # add var name (col 0)
        remove_btn = QPushButton("X")
        remove_btn.setObjectName("remove_button")

        remove_btn.clicked.connect(lambda _, btn=remove_btn: self.remove_detector_method(self.tbl, btn))
        self.tbl.setCellWidget(idx, 0, remove_btn)

        # add var name (col 1)
        item = QTableWidgetItem("")
        self.tbl.setItem(idx, 1, item)

        # add class name (col 2)
        combo = QComboBox()
        combo.addItems(self.all_types)
        combo.setCurrentIndex(0)
        self.tbl.setCellWidget(idx, 2, combo)

        # add detector name (col 3)
        item = QTableWidgetItem("")
        self.tbl.setItem(idx, 3, item)

        # add move name (col 4)
        combo = QComboBox()
        combo.addItems(self.all_moves_names)
        combo.setCurrentIndex(0)
        self.tbl.setCellWidget(idx, 4, combo)

        # add ext unit
        item = QTableWidgetItem(str(0))
        self.tbl.setItem(idx, 5, item)




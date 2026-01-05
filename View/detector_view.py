from PyQt6.QtCore import Qt
from PyQt6.QtGui import QFont, QIntValidator
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QScrollArea, QPushButton, QLabel, QRadioButton, \
    QLineEdit, QButtonGroup, QFrame, QTableWidget, QTableWidgetItem, QComboBox

from Config.special import clear_widget_from_layout
from Config.style import detector_panel_style


class DetectorView(QWidget):
    def __init__(self):
        super().__init__()

        # table
        self.tbl = QTableWidget(0, 6, self)
        self.tbl.setHorizontalHeaderLabels(["מחיקה", "שם משתנה", "סוג גלאי", "שם גלאי", "מופע תנועה", "יח' הארכה"])

        self.tbl.setColumnWidth(0,  50)  # שם משתנה
        self.tbl.setColumnWidth(1, 100)  # סוג גלאי
        self.tbl.setColumnWidth(2, 200)  # שם גלאי
        self.tbl.setColumnWidth(3, 100)  # מופע תנועה
        self.tbl.setColumnWidth(4, 200)  # יח' הארכה
        self.tbl.setColumnWidth(5, 100)  # יח' הארכה

        self.tbl.verticalHeader().setVisible(False)
        # self.tbl.horizontalHeader().setStretchLastSection(True) # עמודה מסוימת מתרחבת אוטומטית
        # self.tbl.resizeColumnsToContents() # width by content
        self.tbl.verticalHeader().setDefaultSectionSize(50)

        #
        self.remove_detector_method = None
        self.update_detectors_method = None

        # button
        btn_layout = QHBoxLayout()
        add_detector_btn = QPushButton("הוסף גלאי")
        add_detector_btn.setObjectName("add_button")

        update_detector_btn = QPushButton("עדכן")
        update_detector_btn.setObjectName("update_button")

        btn_layout.addWidget(add_detector_btn)
        btn_layout.addWidget(update_detector_btn)

        # layout
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.tbl)
        self.root_layout.addLayout(btn_layout)

        # self.root_layout.addWidget(self.tbl)
        self.setLayout(self.root_layout)

        #

        add_detector_btn.clicked.connect(self.add_detector)
        update_detector_btn.clicked.connect(lambda: self.update_detectors_method(self.tbl))

        #
        self.setStyleSheet(detector_panel_style)


        self.hide()

    def show_view(self, detector_list, all_types, all_moves_names):
        """
        Show the 'set move' panel.

        :return: None
        """
        # self._show_scroll_bar(detector_list)
        self.tbl.setRowCount(0)
        self.all_types = all_types
        self.all_moves_names = all_moves_names

        # fill table
        for idx, detector in enumerate (detector_list):
            var_name = detector.var_name                # str
            class_name = detector.class_name            # str
            detector_name = detector.datector_name      # str
            move_name = detector.move_name              # str
            ext_unit = detector.ext_unit                # int

            self.tbl.insertRow(self.tbl.rowCount())

            # add var name (col 0)
            remove_btn = QPushButton("X")
            remove_btn.setObjectName("remove_button")

            remove_btn.clicked.connect(lambda _, btn=remove_btn: self.remove_detector_method(self.tbl, btn))
            self.tbl.setCellWidget(idx, 0, remove_btn)


            # add var name (col 1)
            item = QTableWidgetItem(var_name)
            self.tbl.setItem(idx, 1, item)

            # add class name (col 2)
            combo = QComboBox()
            combo.addItems(self.all_types)
            combo.setCurrentText(class_name)
            self.tbl.setCellWidget(idx, 2, combo)

            # add detector name (col 3)
            item = QTableWidgetItem(detector_name)
            self.tbl.setItem(idx, 3, item)

            # add move name (col 4)
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




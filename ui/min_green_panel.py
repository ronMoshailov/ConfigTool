import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea, \
    QCheckBox

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
from config.style import min_group_row_style

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'classes')))


class MinGreenPanel(QWidget):
    def __init__(self, main_controller):
        super().__init__()
        self.main_controller = main_controller
        self.col = QVBoxLayout()
        # =============== labels =============== #
        print("end to build min green panel")

        # =============== active buttons =============== #
        # btn         = QPushButton("➕")


        # =============== layout =============== #

        # =============== create the layout =============== #

        self.setLayout(self.col)
        self.hide()

        # =============== scroll rows =============== #



    def show_panel(self):
        print("show_panel green_min")

        while self.col.count():
            item = self.col.takeAt(0)        # get the first QLayoutItem of the layout
            widget = item.widget()      # get the widget
            if widget:                  #
                widget.deleteLater()

        all_moves = self.main_controller.config_manager.get_all_moves()
        print("all_moves: ", all_moves)

        print("start to build min green panel")
        for move in all_moves:
            row = QHBoxLayout()
            label = QLabel(move.name)
            textbox = QLineEdit(move.min_green)
            btn = QPushButton("עדכן")
            row.addWidget(label)
            row.addWidget(textbox)
            row.addWidget(btn)

            container = QWidget()
            container.setObjectName("minRowCard")  # כדי שה־CSS למעלה יתפוס
            container.setLayout(row)
            container.setStyleSheet(min_group_row_style)
            self.col.addWidget(container)

        self.col.addStretch()
        self.show()


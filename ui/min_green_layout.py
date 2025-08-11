import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea, \
    QCheckBox

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
from config.style import min_group_row_style

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'classes')))


class MinGreenLayout(QWidget):
    def __init__(self):
        super().__init__()
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



    def show_panel(self, all_moves):
        print(f"min_green_layout:\tshow_panel\t[start] ")

        print("* starting to remove children")
        while self.col.count():
            item = self.col.takeAt(0)        # get the first QLayoutItem of the layout
            widget = item.widget()      # get the widget
            if widget:                  #
                widget.deleteLater()

        print("* creating rows for each move")
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
        print(f"min_green_layout:\tshow_panel\t[end]\n")


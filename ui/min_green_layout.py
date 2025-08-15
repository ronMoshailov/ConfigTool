from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout
from PyQt5.QtCore import QTimer

from controllers.data_controller import DataController
from config.style import min_group_row_style

from entities.toast import Toast


class MinGreenLayout(QWidget):
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self):
        super().__init__()
        self.data_controller = DataController()
        self.col = QVBoxLayout()
        self.setLayout(self.col)
        self.hide()


    # =============== scroll rows =============== #

    def show_panel(self):
        print(f"min_green_layout:\tshow_panel\t[start] ")
        all_moves = self.data_controller.get_all_moves()
        print("* starting to remove children")
        while self.col.count():
            item = self.col.takeAt(0)  # get the first QLayoutItem of the layout
            widget = item.widget()  # get the widget
            if widget:  #
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

            btn.clicked.connect(
                lambda _=False, l=label, t=textbox: self.data_controller.update_min_green(l.text(), t.text())
            )
        QTimer.singleShot(500, lambda: Toast("הודעה נשלחה בהצלחה"))

        self.col.addStretch()
        self.show()
        print(f"min_green_layout:\tshow_panel\t[end]\n")


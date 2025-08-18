from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame
from PyQt5.QtCore import QTimer

from config.special import set_blue_button_white_text_style
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
        dictinary = {}

        row = None
        for i, move in enumerate(all_moves):
            if i % 4 == 0:
                row = QHBoxLayout()
                row.setSpacing(8)
                self.col.addLayout(row)

            label = QLabel(move.name)
            textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
            dictinary[move.name] = textbox

            card = QFrame()
            card.setStyleSheet(min_cards_style)
            card.setObjectName("minRowCard")
            card_layout = QHBoxLayout(card)
            card_layout.setContentsMargins(8, 8, 8, 8)
            card_layout.setSpacing(6)
            card_layout.addWidget(label)
            card_layout.addWidget(textbox)

            row.addWidget(card)

        row = QHBoxLayout()
        row.setSpacing(8)

        btn = QPushButton("עדכן")
        btn.clicked.connect(lambda: self.data_controller.update_min_green(dictinary)
        )
        QTimer.singleShot(500, lambda: Toast("הודעה נשלחה בהצלחה"))

        set_blue_button_white_text_style([btn])
        row.addWidget(btn)
        container = QWidget()
        container.setLayout(row)
        container.setStyleSheet(min_group_row_style)
        self.col.addWidget(container)

        self.col.addStretch()
        self.show()
        print(f"min_green_layout:\tshow_panel\t[end]\n")

min_cards_style = """
QFrame#minRowCard {
    background: #ffffff;
    border: 1px solid #cbd5e1;
    border-radius: 10px;
}
QFrame#minRowCard:hover {
    background: #f9fbff;
    border-color: #8aa4c1;
}
QFrame#minRowCard QLabel {
    color: #243447;
    font-size: 14px;
    font-weight: 600;
}
QFrame#minRowCard QLineEdit {
    background: #f8fafc;
    border: 1px solid #cbd5e1;
    border-radius: 6px;
    padding: 4px 8px;
    min-height: 26px;
}
QFrame#minRowCard QLineEdit:focus {
    border-color: #3b82f6;
}
"""

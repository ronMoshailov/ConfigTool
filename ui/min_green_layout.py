from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy
from PyQt6.QtCore import QTimer

from config.special import set_blue_button_white_text_style
from controllers.data_controller import DataController
from config.style import min_group_row_style, dark_button_style


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
    def _placeholder(self):
        ph = QWidget()
        ph.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)
        ph.setMinimumHeight(1)
        ph.setStyleSheet("background: transparent; border: none;")
        return ph

    def show_panel(self):
        print(f"min_green_layout:\tshow_panel\t[start] ")
        clear_layout(self.col)
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
            # בכל פתיחת שורה חדשה
            if i % 4 == 0:
                # אם יש שורה קודמת לא מלאה – השלם ל-4
                if row is not None and items_in_row < 4:
                    for _ in range(4 - items_in_row):
                        row.addWidget(self._placeholder(), 1)

                row = QHBoxLayout()
                row.setSpacing(8)
                self.col.addLayout(row)
                items_in_row = 0

            # כרטיס
            label = QLabel(move.name)
            textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
            dictinary[move.name] = textbox

            card = QFrame()
            card.setStyleSheet(min_cards_style)
            card.setObjectName("minRowCard")
            card.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)

            card_layout = QHBoxLayout(card)
            card_layout.setContentsMargins(8, 8, 8, 8)
            card_layout.setSpacing(6)
            card_layout.addWidget(label)
            card_layout.addWidget(textbox)

            # כל פריט בשורה עם stretch=1 → בדיוק רבע רוחב
            row.addWidget(card, 1)
            items_in_row += 1

        # השלם את השורה האחרונה אם לא מלאה
        if row is not None and items_in_row < 4:
            for _ in range(4 - items_in_row):
                row.addWidget(self._placeholder(), 1)

        # --- שורת כפתור "עדכן" ---
        btn_row = QHBoxLayout()
        btn_row.setSpacing(8)
        btn = QPushButton("עדכן")
        btn.clicked.connect(lambda: self.data_controller.update_min_green(dictinary))
        set_blue_button_white_text_style([btn])
        btn_row.addStretch()
        btn_row.addWidget(btn)
        btn_row.addStretch()

        container = QWidget()
        container.setLayout(btn_row)
        container.setStyleSheet(min_group_row_style)
        self.col.addWidget(container)

        self.col.addStretch()
        self.show()
        print("min_green_layout:\tshow_panel\t[end]\n")


def clear_layout(layout):
    while layout.count():
        item = layout.takeAt(0)
        w = item.widget()
        if w is not None:
            w.deleteLater()
            continue
        child = item.layout()
        if child is not None:
            clear_layout(child)     # ניקוי רקורסיבי
            child.deleteLater()
            continue
        # spacerItem – אין מה למחוק (יימחק עם ה־item)

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

from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy, \
    QGridLayout, QMessageBox

from config.style import min_green_panel_style
from controllers.data_controller import DataController


class MinGreenLayout(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== Grid Layout =============== #
        self.root_layout = QGridLayout()
        self.root_layout.setHorizontalSpacing(36)
        self.root_layout.setVerticalSpacing(36)
        self.root_layout.setContentsMargins(40, 40, 40, 40)

        # =============== Self =============== #
        self.setLayout(self.root_layout)

        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        self.setStyleSheet(min_green_panel_style)
        self.hide()


    # =============== methods =============== #
    def show_panel(self):
        # =============== Data =============== #
        all_moves = self.data_controller.get_all_moves()
        self.min_green_dict = {}
        cards_in_row = 7

        # =============== Clear grid =============== #
        self._clear_layout()

        # =============== Build grid =============== #
        for i, move in enumerate(all_moves):
            row_num = i // cards_in_row
            col_num = i % cards_in_row

            # =============== QLabel =============== #
            label = QLabel(move.name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)

            # =============== QLineEdit =============== #
            textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
            textbox.setAlignment(Qt.AlignmentFlag.AlignCenter)
            textbox.setValidator(QIntValidator(0, 99))
            if move.name.startswith("B"):
                textbox.setReadOnly(True)

            # =============== Add to dictionary =============== #
            self.min_green_dict[move.name] = textbox

            # =============== Vertical layout =============== #
            vertical_layout = QVBoxLayout()
            vertical_layout.addWidget(label)
            vertical_layout.addWidget(textbox)
            vertical_layout.setContentsMargins(0, 0, 0, 18)  # left, top, right, bottom

            # =============== QFrame =============== #
            card = QFrame()
            card.setObjectName("card")
            card.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)
            card.setMaximumHeight(100)
            card.setLayout(vertical_layout)

            # =============== Add to layout =============== #
            self.root_layout.addWidget(card, row_num, col_num)

        btn = QPushButton("עדכן")
        btn.setCursor(Qt.CursorShape.PointingHandCursor)
        btn.setObjectName("update_button")
        btn.clicked.connect(self._update)

        # =============== Add to layout =============== #
        self.root_layout.setRowStretch(self.root_layout.rowCount(), 1)
        self.root_layout.addWidget(btn, self.root_layout.rowCount(), 0, 1, cards_in_row)  # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
        self.show()

    # =============== inner methods =============== #
    def _clear_layout(self, layout=None):
        """
        Recursively clear all widgets and layouts inside a given layout.
        If no layout is provided, clear the root_layout.
        """
        if layout is None:
            layout = self.root_layout

        while layout.count():
            item = layout.takeAt(0)

            if item.widget():
                item.widget().deleteLater()
            elif item.layout():
                self._clear_layout(item.layout())

        if layout is not self.root_layout:
            layout.deleteLater()

    def _update(self):
        if self.data_controller.update_min_green(self.min_green_dict):
            QMessageBox.information(self, "מינימום ירוק", "כל הזמני מינימום ירוק עודכנו בהצלחה")
            return
        QMessageBox.information(self, "מינימום ירוק", "העדכון נכשלה")


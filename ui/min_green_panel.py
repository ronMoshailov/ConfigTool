from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy, \
    QGridLayout

from config.style import min_green_panel_style
from controllers.data_controller import DataController


class MinGreenLayout(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== Grid Layout =============== #
        self.root_layout = QGridLayout()

        # =============== Self =============== #
        self.setLayout(self.root_layout)
        self.hide()

        # =============== Style =============== #
        self._set_style()

    # =============== methods =============== #
    def show_panel(self):
        # Data
        all_moves = self.data_controller.get_all_moves()
        min_green_dict = {}
        cards_in_row = 7

        # clear grid
        self._clear_grid()

        for i, move in enumerate(all_moves):
            # get number row and number column
            row_num = i // cards_in_row
            col_num = i % cards_in_row

            # create components
            label = QLabel(move.name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)

            textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
            textbox.setAlignment(Qt.AlignmentFlag.AlignCenter)
            textbox.setValidator(QIntValidator(0, 99))
            if move.name.startswith("B"):
                textbox.setReadOnly(True)

            # init dictionary
            min_green_dict[move.name] = textbox

            # create QFrame
            card = QFrame()
            card.setObjectName("card")
            card.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)
            card.setMaximumHeight(100)

            # create vertical layout
            vertical_layout = QVBoxLayout()
            vertical_layout.addWidget(label)
            vertical_layout.addWidget(textbox)
            vertical_layout.setContentsMargins(0, 0, 0, 18)  # left, top, right, bottom

            # set layout
            card.setLayout(vertical_layout)

            # and QFrame to the gird
            self.root_layout.addWidget(card, row_num, col_num)

        #
        self.root_layout.setRowStretch(self.root_layout.rowCount(), 1)
        self.root_layout.setContentsMargins(40, 40, 40, 40)
        self.root_layout.addLayout(self._create_btn_layout(min_green_dict), self.root_layout.rowCount() + 1, 0, 1, cards_in_row)  # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
        self.show()

    # =============== inner methods =============== #
    def _create_btn_layout(self, min_green_dict):
        """
        This method create and initialize the layout of the button

        :param min_green_dict: dictionary for the changed
        :return:
        """
        btn_row = QHBoxLayout()
        btn_row.setSpacing(8)
        btn = QPushButton("עדכן")
        btn.clicked.connect(lambda: self.data_controller.update_min_green(min_green_dict))
        btn_row.addStretch()
        btn_row.addWidget(btn)
        btn_row.addStretch()
        return btn_row



    def _clear_grid(self):
        while self.root_layout.count():             # how many 'QLayoutItem' exist
            item = self.root_layout.takeAt(0)       # get the first 'QLayoutItem' (disconnect from parent, make independent but alive)
            if item.widget():                           # get the widget (if not exist return None)
                item.widget().deleteLater()             # remove the widget when if possible (let him finish if he in the middle of process)



    def _set_style(self):
        self.root_layout.setHorizontalSpacing(36)
        self.root_layout.setVerticalSpacing(36)

        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)

        self.setObjectName("root")

        self.setStyleSheet(min_green_panel_style)

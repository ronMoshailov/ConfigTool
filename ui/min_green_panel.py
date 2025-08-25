from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy, \
    QGridLayout

from controllers.data_controller import DataController


class MinGreenLayout(QWidget):

    def __init__(self):
        super().__init__()
        self.btn_grid_layout = QGridLayout()
        self.btn_grid_layout.setHorizontalSpacing(36)
        self.btn_grid_layout.setVerticalSpacing(36)

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== style =============== #
        root_style = """
QWidget#root {
    border: 1px solid #04c83b;   /* כחול עדין */
    border-radius: 20px; 
    background-color: qlineargradient(
        x1:0, y1:0, x2:0, y2:1, 
        stop:0 #e1fde7,   /* תכלת בהיר */
        stop:1 #eef2ff    /* סגול-אפור רך */
    );
}

QFrame#card {
    background: #fdfdfd; /* אפור-לבן – שונה מהרקע */
    border: 1px solid #d1d5db;
    border-radius: 14px;
    padding: 0px 22px;
    box-shadow: 0px 2px 6px rgba(0,0,0,0.06);
    transition: all 0.25s ease-in-out;
}
QFrame#card:hover {
    border: 1px solid #2861ff;   /* סגול */
    background: #faf5ff;         /* נותן הדגשה עדינה */
}

QFrame#card QLabel {
    color: #111827;
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 6px;
    padding: 5px 12px;
}

QFrame#card QLineEdit {
    background: #f9fafb;
    border: 1px solid #d1d5db;
    border-radius: 8px;
    padding: 5px 12px;
    font-size: 18px;
}
QFrame#card QLineEdit:focus {
    border: 1px solid #3b82f6;
    background: #ffffff;
}
QFrame#card QLineEdit[readOnly="true"] {
    background: #fddfdd;
    color: #9ca3af;
}


        """

        self.setObjectName("root")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)

        self.setStyleSheet(root_style)
        self.setLayout(self.btn_grid_layout)
        self.hide()

    # =============== methods =============== #
    def show_panel(self):
        # get data
        all_moves = self.data_controller.get_all_moves()
        min_green_dict = {}

        cards_in_row = 7

        # clear grid
        while self.btn_grid_layout.count():             # how many 'QLayoutItem' exist
            item = self.btn_grid_layout.takeAt(0)       # get the first 'QLayoutItem' (disconnect from parent, make independent but alive)
            if item.widget():                           # get the widget (if not exist return None)
                item.widget().deleteLater()             # remove the widget when if possible (let him finish if he in the middle of process)

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
            self.btn_grid_layout.addWidget(card, row_num, col_num)

        #
        self.btn_grid_layout.setRowStretch(self.btn_grid_layout.rowCount(), 1)
        self.btn_grid_layout.setContentsMargins(40, 40, 40, 40)
        self.btn_grid_layout.addLayout(self._create_btn_layout(min_green_dict), self.btn_grid_layout.rowCount() + 1, 0, 1, cards_in_row)  # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
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



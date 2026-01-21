from PyQt6.QtCore import Qt
from PyQt6.QtGui import QBrush
from PyQt6.QtWidgets import QWidget, QPushButton, QScrollArea, QVBoxLayout, QHBoxLayout, QLabel, QAbstractItemView, \
    QTableWidget, QTableWidgetItem, QComboBox, QCheckBox, QHeaderView

from Config.constants import gray_color, light_green_color
from Config.special import clear_widget_from_layout
from Config.style import sk_panel_style

import Config

class SkView(QWidget):

    def __init__(self):
        super().__init__()
        # Controller Methods
        self.add_sk_method = None
        self.remove_sk_method = None
        self.change_color_method = None
        self.change_name_method = None
        self.update_comment_method = None
        self.update_data_method = None

        # Data
        self.tables_list = []

        # Cards Layout
        self.cards_layout = QHBoxLayout()
        self.cards_layout.setContentsMargins(0, 0, 0, 0)
        self.cards_layout.setSpacing(16)

        # QScrollArea
        scroll_area = Config.special.init_scroll(self.cards_layout)

        # Add Buttons
        self.btn_add = QPushButton("SK הוסף")
        self.btn_add.clicked.connect(lambda: self.add_sk_method())
        self.btn_add.setObjectName("add_button")

        # Update Buttons
        self.btn_update = QPushButton("עדכן")
        self.btn_update.clicked.connect(lambda: self.update_data_method(self.tables_list))
        self.btn_update.setObjectName("update_button")

        # Layout Buttons
        button_layout = QHBoxLayout()
        button_layout.addWidget(self.btn_update)
        button_layout.addWidget(self.btn_add)

        # Root Layout
        root_layout = QVBoxLayout()
        root_layout.addWidget(scroll_area)
        root_layout.addLayout(button_layout)

        # Self
        self.setLayout(root_layout)
        self.setStyleSheet(sk_panel_style)
        self.setObjectName("RootWidget")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.hide()

    def show_view(self, sk_list, all_moves):
        self._refresh_tables(sk_list, all_moves)
        self.show()

    def hide_view(self):
        self.hide()

    # ============================== Layout ============================== #
    def _build_table_layout(self, card_number: int, all_moves, sk_list):
        """
        This method initialize the widget of the table (title, table with values and signals)

        :param card_number: number of the SK card
        :return: QTableWidget object that holds everything
        """
        # Widget That Holds a Card
        wrap = QWidget()
        wrap.setObjectName("wrap")

        # Title
        title = QLabel(f"SK_{card_number}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # Table
        tbl = QTableWidget(24, 4, wrap)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.verticalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Stretch)

        # Add Table To List
        self.tables_list.append(tbl)

        # Remove Button
        btn_remove = QPushButton("מחק SK")
        btn_remove.clicked.connect(lambda _, card_num = card_number: self.remove_sk_method(card_num))
        btn_remove.setObjectName("remove_button")

        # Set Layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addSpacing(10)
        column_layout.addWidget(btn_remove)

        # Set Layout To Warp (Card)
        wrap.setLayout(column_layout)

        # Build Table
        was_tbl = tbl.blockSignals(True)                # block signals
        self._init_table(tbl, all_moves)                # create the table with no values
        self._fill_table(tbl, card_number, sk_list)     # set values
        tbl.blockSignals(was_tbl)                       # release signals

        # col 1 (name) changes (add signal)
        for row_number in range(24):
            combo = tbl.cellWidget(row_number, 1)
            combo.currentTextChanged.connect(lambda _text, row_num = row_number: self.change_name_method(tbl, row_num, 1))

        # col 2 (color) click (add signal)
        tbl.cellClicked.connect(lambda row_num, col_num: self.change_color_method(tbl, row_num, col_num))

        return wrap

    def _init_table(self, tbl: QTableWidget, all_moves):
        """
        This method create the table with all the rows and columns (without values).

        :param tbl: The 'QTableWidget' to create.
        :return: None
        """
        # Data
        all_moves_names = ["-"] + [m.name for m in all_moves]

        # Table
        tbl.setHorizontalHeaderLabels(["#", "move", "color", "comment"])
        tbl.setFixedWidth(351)
        tbl.verticalHeader().setVisible(False)
        tbl.setColumnWidth(0, 40)
        tbl.setColumnWidth(1, 160)
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 90)

        gray = QBrush(gray_color)

        # Set columns (without values)
        for r in range(24):
            # Col 0 (display)
            col_0 = QTableWidgetItem(str(r + 1))
            col_0.setFlags(Qt.ItemFlag.NoItemFlags)
            col_0.setBackground(gray)
            col_0.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            tbl.setItem(r, 0, col_0)

            # Col 1 (combo)
            combo = QComboBox()
            combo.addItems(all_moves_names)
            combo.wheelEvent = lambda event: None # override the wheel mouse event (disable the wheel mouse)
            tbl.setCellWidget(r, 1, combo)

            # Col 2 (color)
            col_2 = QTableWidgetItem("")
            col_2.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            col_2.setFlags(col_2.flags() & ~Qt.ItemFlag.ItemIsEditable)
            tbl.setItem(r, 2, col_2)

            # Col 3 (check box)
            col_3 = QCheckBox()
            col_3.setChecked(False)
            col_3.setObjectName("checkbox_comment")
            tbl.setCellWidget(r, 3, col_3)
            col_3.stateChanged.connect(lambda state, row=r: self.update_comment_method(tbl, row, state))

    # ============================== Logic ============================== #
    def _fill_table(self, tbl: QTableWidget, card_number: int, sk_list):
        """
        This method fill the tables with values from DB.

        :param tbl: The table to fill.
        :param card_number: number of the SK card
        :return: None
        """

        all_channels_list = sk_list[card_number - 1].all_channels
        green_bg = QBrush(light_green_color)

        for ch in all_channels_list:
            # get data
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # col 3 (check box)
            it3 = tbl.cellWidget(row, 3)
            was = it3.blockSignals(True)
            it3.setChecked(True) if is_commented else it3.setChecked(False)
            it3.blockSignals(was)

            # col 2 (color)
            it2 = tbl.item(row, 2)
            it2.setText("🔴" if ch.color == "hwRed200" else "🟢" if ch.color == "hwGreen200" else "🟡" if ch.color == "hwAmber200" else "")
            if is_commented:
                it2.setBackground(green_bg)

            # col 1 (combo)
            combo = tbl.cellWidget(row, 1)
            if ch.name == '':
                combo.setCurrentIndex(0)
            else:
                combo.addItems([ch.name])
                combo.setCurrentIndex(combo.findText(ch.name))
                if is_commented:
                    combo.setStyleSheet("background-color: rgb(200,255,200);")

            # col 0 (display)
            if is_commented:
                tbl.item(row, 0).setBackground(green_bg)

    def _refresh_tables(self, sk_list, all_moves):
        """
        This method clears the main layout and build back with data from DB.

        :return: None
        """
        # refresh the table
        clear_widget_from_layout([self.cards_layout])
        self.tables_list.clear()

        # for each SK card initialize a table
        for i in range(1, len(sk_list) + 1):
            card_widget = self._build_table_layout(i, all_moves, sk_list)
            self.cards_layout.addWidget(card_widget)

        # move all tables left
        self.cards_layout.addStretch(1)







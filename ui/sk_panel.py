from PyQt6.QtCore import Qt, QSignalBlocker
from PyQt6.QtGui import QBrush
from PyQt6.QtWidgets import (
    QWidget, QScrollArea, QVBoxLayout, QHBoxLayout,
    QTableWidget, QLabel, QPushButton, QTableWidgetItem, QComboBox,
    QCheckBox, QAbstractItemView
)

from config.colors import gray_color, light_green_color, white_color
from config.style import sk_panel_style
from controllers.data_controller import DataController

class SkPanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()
        self.tables_list = []

        # =============== widget =============== #
        scroll_container = QWidget()

        self.btn_add = QPushButton("הוסף SK")
        self.btn_add.clicked.connect(self._add_sk)
        self.btn_add.setObjectName("add_button")

        self.btn_update = QPushButton("עדכן")
        self.btn_update.clicked.connect(self._update_data)
        self.btn_update.setObjectName("update_button")

        scroll_area = QScrollArea()
        scroll_area.setWidgetResizable(True)

        # =============== layout =============== #
        root_layout = QVBoxLayout()

        button_layout = QHBoxLayout()
        button_layout.addStretch()
        button_layout.addWidget(self.btn_update)
        button_layout.addWidget(self.btn_add)

        self.tables_layout = QHBoxLayout()
        self.tables_layout.setContentsMargins(0, 0, 0, 0)
        self.tables_layout.setSpacing(16)

        # =============== connect between layout and widgets =============== #
        root_layout.addWidget(scroll_area)
        root_layout.addLayout(button_layout)

        scroll_area.setWidget(scroll_container)
        scroll_container.setLayout(self.tables_layout)

        # =============== self =============== #
        self.setLayout(root_layout)
        self.setStyleSheet(sk_panel_style)
        self.hide()


    # --------------- add methods --------------- #
    def _add_sk(self):
        """
        This method add SK card in DB and then update the panel.

        :return: None
        """
        self.data_controller.add_sk()
        self._refresh_tables()

    # --------------- update methods --------------- #
    def _update_comment(self, table, row_number, state):
        """
        This method manage the logic of the comment checkbox.
        Color the row and check if the row can be in comment.

        :param table: The table.
        :param card_number: number of the SK card.
        :param row_number: row number (channel) of the SK card.
        :param state: state of the checkbox
        :return: None
        """
        # disable the option to check if there is no move
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.CheckState.Unchecked)
            return False

        gray_brush = QBrush(gray_color)
        light_green_brush = QBrush(light_green_color)
        white_brush = QBrush(white_color)

        # color the rows
        if Qt.CheckState(state) == Qt.CheckState.Checked:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(light_green_brush)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({light_green_brush.color().red()},{light_green_brush.color().green()},{light_green_brush.color().blue()}); }}")
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(light_green_brush)
        else:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(gray_brush)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({white_brush.color().red()},{white_brush.color().green()},{white_brush.color().blue()}); }}")
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(white_brush)
        return True

    def _update_color(self, table: QTableWidget, row: int, col: int, fix_color = False):
        if col != 2:
            return False

        combo = table.cellWidget(row, 1)
        item = table.item(row, 2)
        move_name = combo.currentText()

        with QSignalBlocker(combo), QSignalBlocker(table):
            if move_name == "-":
                self.data_controller.write_log("update color failed (no move was chosen)", "r")
                return False

        cur = item.text()

        if move_name.startswith("k"):
            if fix_color:
                return True
            nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        elif move_name.startswith("p"):
            if fix_color:
                if cur == "🟡":
                    item.setText("🔴")
                return True
            nxt_color = {"🔴": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        elif move_name.startswith("B"):
            if fix_color:
                item.setText("🟡")
                return True
            nxt_color = {"🟡": "🟡"}.get(cur, "🟡")

        item.setText(nxt_color)


    def _update_data(self):
        if self._is_names_valid() and self._is_color_valid():
            for idx, table in enumerate(self.tables_list):     # for each table
                for row_num in range(24):                       # for each row in table
                    # save name
                    move_name = table.cellWidget(row_num, 1).currentText()

                    # save color
                    color = table.item(row_num, 2).text()

                    # save comment
                    status = table.cellWidget(row_num, 3).isChecked()

                    self.data_controller.update_sk_name(idx + 1, row_num, move_name)
                    self.data_controller.update_sk_color(idx + 1, row_num, color)
                    self.data_controller.update_sk_comment(idx + 1, row_num, status)

                    # if self.data_controller.update_sk_color(table_num, row_num):
                    #     return True
                    # else:
                    #     return False
            pass
            # self.data_controller.update_sk()
            # return True
        return None
        # return False

        #-# update comment #-#
        # was = table.blockSignals(True) # block signals
        #
        # is_success = self.data_controller.update_sk_comment(card_number, row_number + 1) #
        # if not is_success:
        #     self.data_controller.write_log("The change of the comment state didn't succeed","r")
        #     table.blockSignals(was)     # release signal
        #     return False

        # self.data_controller.write_log("The change of the comment succeeded","g")

        #-# update name #-#
        # if not self.data_controller.update_sk_name(card_number, row, move_name):
        #     self.data_controller.write_log("update name failed in the controller", "r")
        #     return False

        #-# update color #-#
        # if self.data_controller.update_sk_color(card_number, row):
        #     self.data_controller.write_log("update color succeeded", "g")
        #     item.setText(nxt_color)
        #     return True
        # self.data_controller.write_log("update color failed in the controller", "r")
        # return False

    def _update_name(self, table: QTableWidget, row: int, col: int):
        combo = table.cellWidget(row, col)
        move_name = combo.currentText()

        white = QBrush(white_color)
        gray = QBrush(gray_color)

        with QSignalBlocker(combo), QSignalBlocker(table):
            move_name = "" if move_name == "-" else move_name

            # clear the row
            if move_name == "":
                (table.item(row, 0) or QTableWidgetItem()).setBackground(gray)
                table.cellWidget(row, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}")
                (table.item(row, 2) or QTableWidgetItem()).setBackground(white)
                table.item(row, 2).setText("")
                table.cellWidget(row, 3).setStyleSheet("margin-left:auto; margin-right:auto;")
                table.cellWidget(row, 3).setCheckState(Qt.CheckState.Unchecked)
            else:
                self._update_color(table, row, 2, True)
        return True

    # --------------- remove methods --------------- #
    def _remove_sk(self, card_number):
        if self.data_controller.get_sk_count() == 1:
            self.data_controller.write_log("you can't have 0 sk cards", "r")
            return False
        if self.data_controller.remove_sk(card_number):
            self.data_controller.write_log("remove sk succeeded", "g")
            self._refresh_tables()
            return True
        self.data_controller.write_log("remove sk failed", "r")
        return False

    # --------------- general methods --------------- #
    def show_panel(self):
        self._refresh_tables()
        self.show()

    def _refresh_tables(self):
        """
        This method clears the main layout and build back with data from DB.

        :return: None
        """
        # refresh the table
        while self.tables_layout.count():  # as long a 'QLayoutItem' exist in 'tables_layout'
            it = self.tables_layout.takeAt(0)  # disconnect the first 'QLayoutItem' (can be just another layout)
            w = it.widget()
            if w:
                w.deleteLater()

        # get how many SK cards exist
        num_cards = self.data_controller.get_sk_count()

        # for each SK card initialize a table
        for i in range(1, num_cards + 1):
            card_widget = self._init_table(i)
            self.tables_layout.addWidget(card_widget)

        # move all tables left
        self.tables_layout.addStretch(1)

    def _init_table(self, card_number: int):
        """
        This method initialize the widget of the table (title, table with values and signals)

        :param card_number: number of the SK card
        :return: QTableWidget object that holds everything
        """
        # widget that holds title and table
        wrap = QWidget()

        # set title
        title = QLabel(f"SK_{card_number}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # set table
        tbl = QTableWidget(24, 4, wrap)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)

        self.tables_list.append(tbl)

        # btn_remove = DoubleClickButton("מחק SK")
        # btn_remove.doubleClicked.connect(lambda _, c=card_number: self._remove_sk(c))
        # btn_remove.setObjectName("remove_button")

        btn_remove = QPushButton("מחק SK")
        btn_remove.clicked.connect(lambda _, card_num = card_number: self._remove_sk(card_num))
        btn_remove.setObjectName("remove_button")

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addSpacing(10)
        column_layout.addWidget(btn_remove)

        #
        wrap.setLayout(column_layout)

        # create and fill the table
        was_tbl = tbl.blockSignals(True)
        self._create_table(tbl, card_number)    # create the table with no values
        self._fill_table(tbl, card_number)      # set values
        tbl.blockSignals(was_tbl)               # release signals

        # col 1 (name) changes (add signal)
        for row_number in range(24):
            combo = tbl.cellWidget(row_number, 1)
            combo.currentTextChanged.connect(lambda _text, row_num = row_number: self._update_name(tbl, row_num, 1))

        # col 2 (color) click (add signal)
        tbl.cellClicked.connect(lambda row_num, col_num: self._update_color(tbl, row_num, col_num))

        return wrap

    def _create_table(self, tbl: QTableWidget, card_number: int):
        """
        This method create the table with all the rows and columns (without values).

        :param tbl: The 'QTableWidget' to create.
        :param card_number: number of the SK card.
        :return: None
        """
        tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
        tbl.setFixedWidth(351)
        tbl.verticalHeader().setVisible(False)
        tbl.setColumnWidth(0, 40)
        tbl.setColumnWidth(1, 160)
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 90)

        gray = QBrush(gray_color)

        all_moves = self.data_controller.get_all_moves()
        all_moves_names = ["-"] + [m.name for m in all_moves]

        # set columns (without values)
        for r in range(24):
            # col 0 (display)
            col_0 = QTableWidgetItem(str(r + 1))
            col_0.setFlags(Qt.ItemFlag.NoItemFlags)
            col_0.setBackground(gray)
            col_0.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            tbl.setItem(r, 0, col_0)

            # col 1 (combo)
            combo = QComboBox()
            combo.addItems(all_moves_names)
            combo.wheelEvent = lambda event: None # override the wheel mouse event (disable the wheel mouse)
            tbl.setCellWidget(r, 1, combo)

            # col 2 (color)
            col_2 = QTableWidgetItem("")
            col_2.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            col_2.setFlags(col_2.flags() & ~Qt.ItemFlag.ItemIsEditable)
            tbl.setItem(r, 2, col_2)

            # col 3 (check box)
            col_3 = QCheckBox()
            col_3.setChecked(False)
            col_3.setObjectName("checkbox_comment")
            tbl.setCellWidget(r, 3, col_3)
            col_3.stateChanged.connect(lambda state, row=r: self._update_comment(tbl, row, state))

    def _fill_table(self, tbl: QTableWidget, card_number: int):
        """
        This method fill the tables with values from DB.

        :param tbl: The table to fill.
        :param card_number: number of the SK card
        :return: None
        """

        all_channels_list = self.data_controller.get_all_sk_channels(card_number)
        green_bg = QBrush(light_green_color)

        for ch in all_channels_list:
            # get data
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # col 3 (check box)
            it3 = tbl.cellWidget(row, 3)
            was = it3.blockSignals(True)
            it3.setCheckState(Qt.CheckState.Checked if is_commented else Qt.CheckState.Unchecked)
            it3.blockSignals(was)

            # col 2 (color)
            it2 = tbl.item(row, 2)
            it2.setText("🔴" if ch.color == "hwRed200" else "🟢" if ch.color == "hwGreen200" else "🟡" if ch.color == "hwAmber200" else "")
            if is_commented:
                it2.setBackground(green_bg)

            # col 1 (combo)
            combo = tbl.cellWidget(row, 1)
            idx = 0 if combo.findText(ch.name) == -1 else combo.findText(ch.name)
            combo.setCurrentIndex(idx)
            if is_commented:
                combo.setStyleSheet("background-color: rgb(200,255,200);")

            # col 0 (display)
            if is_commented:
                tbl.item(row, 0).setBackground(green_bg)

    # --------------- validation methods --------------- #
    def _is_names_valid(self):
        """
        This method check if names has exactly the count instances he needs.

        :return: True if succeed, False otherwise
        """
        dict_count = {}

        for table in self.tables_list:
            for row_number in range(24):
                move_name = table.cellWidget(row_number, 1).currentText()
                if move_name != "-":
                    if move_name in dict_count:
                        dict_count[move_name] += 1
                    else:
                        dict_count[move_name] = 1

        for key, val in dict_count.items():
            if key.startswith("k"):
                if val != 3:
                    self.data_controller.write_log(f"Move '{key}' is not valid ({val})", "r")
                    return False
            elif key.startswith("p"):
                if val != 2:
                    self.data_controller.write_log(f"Move '{key}' is not valid ({val})", "r")
                    return False
            elif key.startswith("B"):
                if val != 1:
                    self.data_controller.write_log(f"Move '{key}' is not valid ({val})", "r")
                    return False
        return True

    def _is_color_valid(self):
        """
        This method check if color has exactly the count instances he needs.
        :return:
        """
        dict_count = {}

        for table in self.tables_list:
            for row_number in range(24):
                move_name = table.cellWidget(row_number, 1).currentText()
                move_color = table.item(row_number, 2).text()
                if move_name != "-":
                    if move_name in dict_count:
                        if move_color in dict_count[move_name]:
                            self.data_controller.write_log(f"Move '{move_name}' has invalid ({move_color}) count", "r")
                            return False
                        dict_count[move_name].append(move_color)
                    else:
                        dict_count[move_name] = []
                        dict_count[move_name].append(move_color)

        return True






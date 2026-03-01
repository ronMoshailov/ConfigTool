from PyQt6.QtCore import Qt, QSignalBlocker
from PyQt6.QtGui import QBrush
from PyQt6.QtWidgets import QWidget, QPushButton, QVBoxLayout, QHBoxLayout, QLabel, QAbstractItemView, \
    QTableWidget, QTableWidgetItem, QComboBox, QCheckBox, QHeaderView, QMessageBox

from Config.constants import gray_color, light_green_color
from Config.special import clear_widget_from_layout
from Config.style import sk_panel_style

import Config

class SkView(QWidget):

    def __init__(self):
        super().__init__()
        # Controller Methods
        self.add_sk_card_method     = None
        self.remove_sk_method       = None
        self.change_color_method    = None
        # self.change_name_method     = None
        # self.update_comment_method  = None
        self.set_channel_method     = None

        # Data
        self.tables_list            = []
        self.btn_add                = None
        self.btn_update             = None

        # Cards Layout
        self.cards_layout = QHBoxLayout()
        self.cards_layout.setContentsMargins(0, 0, 0, 0)
        self.cards_layout.setSpacing(16)

        # QScrollArea
        scroll_area = Config.special.init_scroll(self.cards_layout)

        self._create_buttons()

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
        """
        This method clears the main layout and build back with data from DB.
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

        self.show()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ============================== Layout ============================== #
    def _build_table_layout(self, card_number: int, all_moves, sk_list):
        """
        This method initialize the table (title, table with values and signals)
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
            combo.currentTextChanged.connect(lambda _text, row_num = row_number: self.change_name(tbl, row_num, 1))

        # col 2 (color) click (add signal)
        tbl.cellClicked.connect(lambda row_num, col_num: self.change_color(tbl, row_num, col_num))

        return wrap

    def _init_table(self, tbl: QTableWidget, all_moves):
        """
        This method create the table with all the rows and columns (without values).
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
            col_3.stateChanged.connect(lambda state, row=r: self.update_comment(tbl, row, state))

    def _create_buttons(self):
        # Add Buttons
        self.btn_add = QPushButton("הוסף כרטיס")
        self.btn_add.clicked.connect(lambda: self.add_sk_card_method())
        self.btn_add.setObjectName("add_button")

        # Update Buttons
        self.btn_update = QPushButton("עדכן")
        self.btn_update.clicked.connect(lambda: self.update_data(self.tables_list))
        self.btn_update.setObjectName("update_button")


    # ============================== Logic ============================== #
    def _fill_table(self, tbl: QTableWidget, card_number: int, sk_list):
        """
        This method fill the tables with values from DB.
        """
        all_channels_list   = sk_list[card_number - 1].all_channels
        green_bg            = QBrush(light_green_color)

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


    def change_color(self, table: QTableWidget, row: int, col: int):
            """
            This method manages the change of the color of the channel
            """
            if col != 2:
                return

            combo = table.cellWidget(row, 1)
            item = table.item(row, 2)
            move_name = combo.currentText()

            with QSignalBlocker(combo), QSignalBlocker(table):
                if move_name == "-":
                    return

            cur = item.text()

            if move_name.startswith("k"):
                nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")

            elif move_name.startswith("p"):
                nxt_color = {"🔴": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")

            elif move_name.startswith("B"):
                nxt_color = {"🟡": "🟡"}.get(cur, "🟡")

            item.setText(nxt_color)

    def fix_color(self, table: QTableWidget, row: int, col: int):
        """
        This method fixthe color of the channel
        """
        if col != 2:
            return

        combo       = table.cellWidget(row, 1)
        item        = table.item(row, 2)
        move_name   = combo.currentText()

        with QSignalBlocker(combo), QSignalBlocker(table):
            if move_name == "-":
                return

        cur = item.text()

        if move_name.startswith("k"):
            return

        elif move_name.startswith("p"):
            nxt_color = {"🟢": "🔴"}.get(cur, "🔴")

        elif move_name.startswith("B"):
            nxt_color = {"🔴": "🟡", "🟢": "🟡"}.get(cur, "🟡")

        item.setText(nxt_color)

    def change_name(self, table: QTableWidget, row: int, col: int):
        """
        This method manages the change of the name of the channel
        """
        combo       = table.cellWidget(row, col)
        move_name   = combo.currentText()

        white = QBrush(Config.constants.white_color)
        gray = QBrush(Config.constants.gray_color)

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
                self.fix_color(table, row, 2)

    def update_comment(self, table, row_number, state):
        """
        This method manage the logic of the comment checkbox.
        Color the row and check if the row can be in comment.
        """
        # disable the option to check if there is no move
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.CheckState.Unchecked)
            return False

        gray_brush = QBrush(Config.constants.gray_color)
        light_green_brush = QBrush(Config.constants.light_green_color)
        white_brush = QBrush(Config.constants.white_color)

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

    def _is_names_valid(self, tables_list):
        """
        This method check if names has exactly the count instances he needs.
        """
        dict_count = {}

        for table in tables_list:
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
                    QMessageBox.critical(self, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
            elif key.startswith("p"):
                if val != 2:
                    QMessageBox.critical(self, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
            elif key.startswith("B"):
                if val != 1:
                    QMessageBox.critical(self, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
        return True

    def _is_color_valid(self, tables_list):
        """
        This method check if color has exactly the count instances he needs.
        :return:
        """
        dict_count = {}

        for table in tables_list:
            for row_number in range(24):
                move_name = table.cellWidget(row_number, 1).currentText()
                move_color = table.item(row_number, 2).text()
                if move_name != "-":
                    if move_name in dict_count:
                        if move_color in dict_count[move_name]:
                            QMessageBox.critical(self, "שגיאה", f"המופע {move_name} בעל מספר שגוי של הצבע ה-{move_color}")
                            return False
                        dict_count[move_name].append(move_color)
                    else:
                        dict_count[move_name] = []
                        dict_count[move_name].append(move_color)
        return True

    def update_data(self, tables_list):
        if self._is_names_valid(tables_list) and self._is_color_valid(tables_list):
            for idx, table in enumerate(tables_list):     # for each table
                for row_num in range(24):                       # for each row in table

                    move_name = table.cellWidget(row_num, 1).currentText()
                    status = table.cellWidget(row_num, 3).isChecked()
                    mapping_color = {"🔴": "hwRed200", "🟡": "hwAmber200", "🟢": "hwGreen200"}
                    emoji = table.item(row_num, 2).text()
                    if emoji in mapping_color:
                        color = mapping_color[emoji]
                    else:
                        color = ""

                    if move_name == "-":
                        self.set_channel_method(idx + 1, "", "", row_num+1, False)
                        continue

                    self.set_channel_method(idx + 1, move_name, color, row_num+1, status)

            QMessageBox.information(self, "SK כרטיס", "העדכון הצליח")

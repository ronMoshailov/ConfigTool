from PyQt5.QtCore import Qt, QSignalBlocker
from PyQt5.QtGui import QBrush, QColor
from PyQt5.QtWidgets import QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy, QTableWidget, QLabel, \
    QHeaderView
from PyQt5.QtWidgets import QTableWidgetItem, QComboBox, QCheckBox, QAbstractItemView

from config.colors import gray_color, light_green_color
from controllers.data_controller import DataController
from entities.log import Log

class SkPanel(QWidget):
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self):
        super().__init__()
        self._initialized = None
        self.data_controller = DataController()
        self.hide()

    # --------------- add methods --------------- #
    None

    # -------------- get methods ------------- #
    None

    # ------------- update methods -------------- #
    def update_comment(self, row_number, state, card_number, table):
        print(f"[class] SkLayout:\t [method] update_comment\t[start] ")
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.Unchecked)
            return False

        was = table.blockSignals(True) # block signal

        is_success = self.data_controller.update_sk_comment(card_number, row_number + 1)

        if not is_success:
            Log.error("The change of the comment state didn't succeeded")
            table.blockSignals(was)
            return False

        gray = QBrush(QColor(230, 230, 230))
        green = QBrush(QColor(180, 255, 180))
        white = QBrush(QColor(255, 255, 255))

        if state == Qt.Checked:
            # col 0
            cell = table.item(row_number, 0)
            cell.setBackground(green)

            # col 1
            cell = table.cellWidget(row_number, 1)
            cell.setStyleSheet(
                f"QComboBox {{ background-color: rgb({green.color().red()},{green.color().green()},{green.color().blue()}); }}")  # set style to combo

            # col 2
            cell = table.item(row_number, 2)
            cell.setBackground(green)

            # # col 3
            # cell = table.cellWidget(row_number, 3)
            # cell.setBackground(green)

        else:
            # col 0
            cell = table.item(row_number, 0)
            cell.setBackground(gray)

            # col 1
            cell = table.cellWidget(row_number, 1)
            cell.setStyleSheet(
                f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}")  # set style to combo

            # col 2
            cell = table.item(row_number, 2)
            cell.setBackground(white)

        table.blockSignals(was)
        print(f"[class] SkLayout:\t [method] update_comment\t[end] ")

    def update_color(self, table: QTableWidget, row: int, col: int, card_number: int):
        print(f"[class] SkLayout:\t [method] update_color\t[start] ")

        combo = table.cellWidget(row, 1)
        item = table.item(row, 2)

        with QSignalBlocker(combo), QSignalBlocker(table):
            if combo.currentText() == "-":
                Log.error("no move was chosen")
                return False


        cur = item.text()
        nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")

        print(f"[class] SkLayout:\t [method] update_comment\t[end] ")
        # אם צריך לעדכן מודל:
        if self.data_controller.update_sk_color(card_number, row):
            item.setText(nxt_color)

    def update_name(self, table: QTableWidget, row: int, col: int, card_number: int):
        print(f"[class] SkLayout:\t [method] update_name\t[start] ")

        # שליפת הטקסט מתוך התא (באותו row/col)
        combo = table.cellWidget(row, col)
        white = QBrush(QColor(255, 255, 255))
        gray = QBrush(QColor(230, 230, 230))

        with QSignalBlocker(combo), QSignalBlocker(table):
            name_text = "" if combo.currentText() == "-" else combo.currentText()

            # Check if need to clean the channel
            # if name_text == "":
            #     self.data_controller.clear_channel(card_number, row)
            #     print(f"[class] SkLayout:\t [method] update_name\t[end] ")
            #     return True
            is_success = self.data_controller.update_sk_name(card_number, row, name_text)
            if is_success is False:
                Log.error("update name failed")
                return False

            is_success = self.data_controller.update_sk_color(card_number, row)
            if is_success is False:
                Log.error("update name failed")
                return False
            if name_text == "":
                # column 0
                table.item(row, 0).setBackground(gray)
                # column 1
                table.cellWidget(row, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}") # set style to combo
                # column 2
                table.item(row, 2).setBackground(white)
                table.item(row, 2).setText("")
                # column 3
                table.cellWidget(row, 3).setStyleSheet("margin-left:auto; margin-right:auto;")
                table.cellWidget(row, 3).setCheckState(Qt.Unchecked)

            # כאן לא נוגעים ב־UI – רק מודל

        print(f"[class] SkLayout:\t [method] update_name\t[end] ")

    # ------------- delete methods -------------- #
    None

    # ------------- general methods ------------- #
    def show_layout(self):
        # main layout
        root = QVBoxLayout(self) # main layout of the panel

        # scroll bar
        scroll = QScrollArea() # scroll bar
        scroll.setWidgetResizable(True) # it's needed and I don't know why and I don't, without this the scroll area size is like 0x0, fk chatGPT
        root.addWidget(scroll) # set the scroll widget as the father of the layout


        # קונטיינר פנימי שמחזיק את כל הטבלאות בשורה
        container = QWidget()
        tables_layout = QHBoxLayout()
        container.setLayout(tables_layout) # connect

        scroll.setWidget(container)

















        # create the tables
        num_cards = self.data_controller.get_sk_count()
        for i in range(1, num_cards + 1):
            card_widget = self.init_table(i)
            card_widget.setSizePolicy(QSizePolicy.Fixed, QSizePolicy.Preferred)
            tables_layout.addWidget(card_widget)

        # רווח בסוף לשיפור מראה
        spacer = QWidget()
        spacer.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Preferred)
        tables_layout.addWidget(spacer)

        self._initialized = True
        self.show()

    def init_table(self, card_number: int):
        """
        This method initializes the table for the card number.

        :param card_number: card number to initialize for the table.
        :return: widget the holds the column layout of the table.
        """
        # set QWidget that holds everything
        wrap = QWidget()

        # set layout of the label and the table
        column_layout = QVBoxLayout(wrap)

        # set label for the number card
        title = QLabel(f"SK_{card_number}", wrap)
        title.setAlignment(Qt.AlignCenter)
        column_layout.addWidget(title)
        title.setStyleSheet("""
                QLabel {
                    font-size: 36px;
                    font-weight: bold;
                    color: #2c3e50;
                    padding: 4px 6px;
                    border-radius: 6px;
                    background: #d2e1ff;
                    border: 1px solid #d0d7de;
                    min-height: 60px;
                }
                QLabel:hover {
                    background: #d6eaf8;
                    border: 1px solid #3498db;
                }
            """)

        # create table
        tbl = QTableWidget(24, 4)
        tbl.setSelectionMode(QAbstractItemView.NoSelection) # remove selection colors
        tbl.setFocusPolicy(Qt.NoFocus) # remove focus style

        # block signals
        was_tbl = tbl.blockSignals(True)

        self._create_table(tbl, card_number)
        self._fill_table(tbl, card_number)

        # release signals
        tbl.blockSignals(was_tbl)

        # connect signal - signal for col 1 (name)
        for r in range(24):
            combo = tbl.cellWidget(r, 1) # get the combo in row 'r' and column '1'
            combo.currentTextChanged.connect(lambda _text, row=r, t=tbl, card=card_number: self.update_name(t, row, 1, card))
        # connect signal - signal for col 2 (color)
        tbl.cellClicked.connect(lambda r, c, t=tbl, card=card_number: self.update_color(t, r, c, card) if c == 2 else None) # 'r' and 'c' send automatically
        # connect signal - signal for col 3 (checkBox)
        tbl.itemChanged.connect(lambda item, c=card_number, t=tbl: self.update_comment(item, c, t) if item.column() == 3 else None)

        column_layout.addWidget(tbl) # add the table to the layout
        return wrap

    def _create_table(self, tbl: QTableWidget, card_number: int):
        """
        This method create the table for the card number.

        :param tbl: Not initialized table.
        :param card_number: The card number of the table.
        :return: None
        """
        # set table
        tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
        tbl.setFixedWidth(351) # tbl.setFixedWidth(336 + tbl.verticalHeader().width())
        tbl.verticalHeader().setVisible(False) # hide vertical header, I used it with column before I knew about this and I don't want to change right now
        tbl.setColumnWidth(0, 40)
        tbl.setColumnWidth(1, 160)
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 90)

        # colors
        gray = QBrush(gray_color)

        # data
        all_moves = self.data_controller.get_all_moves()
        all_moves_names = ["-"] + [m.name for m in all_moves]

        # create the table
        for r in range(24):
            # col 0 (display)
            col_0 = QTableWidgetItem(str(r + 1))      # create the cell
            col_0.setFlags(Qt.NoItemFlags)            # the cell is just for display
            col_0.setBackground(gray)                 # set background color
            col_0.setTextAlignment(Qt.AlignCenter)    # set text in the middle
            tbl.setItem(r, 0, col_0)           # set the cell

            # col 1 (combo)
            combo = QComboBox()                     # create combo
            combo.addItems(all_moves_names)         # add options
            combo.wheelEvent = lambda event: None   # options will be change just from clicks or arrow (not scrolling)
            tbl.setCellWidget(r, 1, combo)   # set the cell

            # col 2 (color)
            col_2 = QTableWidgetItem("")                        # create the cell
            col_2.setTextAlignment(Qt.AlignCenter)              # move to text to the center
            col_2.setFlags(col_2.flags() & ~Qt.ItemIsEditable)  # disable the edit
            tbl.setItem(r, 2, col_2)                     # set the cell

            # col 3 (check box)
            col_3 = QCheckBox()                                             # create the cell
            col_3.setChecked(False)                                         # default is unchecked
            col_3.setStyleSheet("margin-left:auto; margin-right:auto;")     # move the checkBox to the center
            tbl.setCellWidget(r, 3, col_3)                           # set the cell
            col_3.stateChanged.connect(lambda state, row=r, t=tbl, card=card_number: self.update_comment(row, state, card, t))

    def _fill_table(self, tbl: QTableWidget, card_number: int):
        """
        This method fill the table with all the values from the dataController.

        :param tbl: Initialized table to fill.
        :param card_number: The card number of the table.
        :return: None
        """
        # Data
        all_channels_list = self.data_controller.get_all_sk_channels(card_number)
        green_bg = QBrush(light_green_color)

        # initialize values
        for ch in all_channels_list:
            # prepare data
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # col 3 (check box)
            it3 = tbl.cellWidget(row, 3)                                 # get the cell
            was = it3.blockSignals(True)                                        # block signal
            it3.setCheckState(Qt.Checked if is_commented else Qt.Unchecked)     # set checked/unchecked
            it3.blockSignals(was)                                               # release signal

            # col 2 (color)
            it2 = tbl.item(row, 2)                                       # get the cell
            it2.setText("🔴" if ch.color == "hwRed200" else "🟢" if ch.color == "hwGreen200" else "🟡" if ch.color == "hwAmber200" else "")    # determine color to display
            if is_commented:                    # if in comment
                it2.setBackground(green_bg)     # make green background

            # col 1 (combo)
            combo = tbl.cellWidget(row, 1)                                   # get the combo
            idx = 0 if combo.findText(ch.name) == -1 else combo.findText(ch.name)   # if name exist in data_controller get he index, else it's '-' so make it 0 instead of (-1)
            combo.setCurrentIndex(idx)                                              # set the option
            if is_commented:
                combo.setStyleSheet("background-color: rgb(200,255,200);")          # make green background

            # col 0 (display)
            if is_commented:
                tbl.item(row, 0).setBackground(green_bg)    # make green background



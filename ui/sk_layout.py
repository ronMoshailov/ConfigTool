from PyQt5.QtCore import Qt, QSignalBlocker
from PyQt5.QtGui import QBrush, QColor
from PyQt5.QtWidgets import QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy, QTableWidget, QLabel, \
    QTableWidgetItem, QComboBox, QCheckBox, QAbstractItemView

from controllers.data_controller import DataController
from entities.log import Log


class SkLayout(QWidget):
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


    # =============== scroll rows =============== #

    def init_table(self, card_number: int):
        wrap = QWidget(self)

        vbox = QVBoxLayout(wrap)
        vbox.setContentsMargins(0, 0, 0, 0)
        vbox.setSpacing(6)

        title = QLabel(f"SK_{card_number}", wrap)
        title.setAlignment(Qt.AlignCenter)

        vbox.addWidget(title)

        tbl = QTableWidget(24, 4, wrap)
        tbl.setSelectionMode(QAbstractItemView.NoSelection) # remove selection colors
        tbl.setFocusPolicy(Qt.NoFocus) # remove focus style

        # block signals
        was_tbl = tbl.blockSignals(True)

        self.create_table(tbl, card_number)
        self.fill_table(tbl, card_number)

        # שחרור חסימת הטבלה
        tbl.blockSignals(was_tbl)

        # connect signals
        # signal for col 2 (color)
        tbl.cellClicked.connect(lambda r, c, t=tbl, card=card_number: self.update_color(t, r, c, card) if c == 2 else None) # 'r' and 'c' send automatically
        # signal for col 3 (checkBox)
        tbl.itemChanged.connect(lambda item, c=card_number, t=tbl: self.update_comment(item, c, t) if item.column() == 3 else None)

        # # שינוי קומבו – לכל שורה חיבור אחד משלו
        for r in range(24):
            combo = tbl.cellWidget(r, 1)
            combo.currentTextChanged.connect(lambda _text, row=r, t=tbl, card=card_number: self.update_name(t, row, 1, card))

        vbox.addWidget(tbl)
        return wrap

    def create_table(self, tbl, card_number):
        # set table
        tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
        tbl.verticalHeader().setVisible(False)
        tbl.setColumnWidth(0, 36)
        tbl.setColumnWidth(1, 160)
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 90)

        # colors
        gray = QBrush(QColor(230, 230, 230))

        # data
        all_moves = self.data_controller.get_all_moves()
        all_moves_names = ["-"] + [m.name for m in all_moves]

        # create the table
        for r in range(24):
            # col 0 (display)
            it0 = QTableWidgetItem(str(r + 1)) # set the cell
            it0.setFlags(Qt.NoItemFlags) # the cell is just for display
            it0.setBackground(gray) # set background color
            it0.setTextAlignment(Qt.AlignCenter) # set text in the middle
            tbl.setItem(r, 0, it0) # set the cell

            # col 1 (combo)
            combo = QComboBox() # create combo
            combo.addItems(all_moves_names) # add options
            combo.wheelEvent = lambda event: None # options will be change just from clicks or arrow (not scrolling)
            tbl.setCellWidget(r, 1, combo) # set the cell

            # col 2 (color)
            it2 = QTableWidgetItem("")
            it2.setTextAlignment(Qt.AlignCenter)
            it2.setFlags(it2.flags() & ~Qt.ItemIsEditable) # disable the edit
            tbl.setItem(r, 2, it2)

            # col 3 (check box)
            it3 = QCheckBox()
            it3.setChecked(False)  # ברירת מחדל לא מסומן
            it3.setStyleSheet("margin-left:auto; margin-right:auto;")  # אופציונלי – למרכז/להפוך כיוון
            tbl.setCellWidget(r, 3, it3)

            it3.stateChanged.connect(lambda state, row=r, t=tbl, card=card_number: self.update_comment(row, state, card, t))
            # it3.setTextAlignment(Qt.AlignCenter)
            # it3.setFlags(Qt.ItemIsUserCheckable | Qt.ItemIsEnabled) # make the cell checkable and can be changed
            # it3.setCheckState(Qt.Unchecked) # init with unchecked

    def fill_table(self, tbl, card_number):
        all_channels_list = self.data_controller.get_all_sk_channels(card_number)
        green_bg = QBrush(QColor(180, 255, 180))

        # initialize values
        for ch in all_channels_list:
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # col 3 (check box)
            it3 = tbl.cellWidget(row, 3)
            was = it3.blockSignals(True)  # block signal
            it3.setCheckState(Qt.Checked if is_commented else Qt.Unchecked)
            it3.blockSignals(was) # release signal

            # col 2 (color)
            it2 = tbl.item(row, 2)
            it2.setText("🔴" if ch.color == "hwRed200" else "🟢" if ch.color == "hwGreen200" else "🟡" if ch.color == "hwAmber200" else "")
            if is_commented:
                it2.setBackground(green_bg)

            # col 1 (combo)
            combo = tbl.cellWidget(row, 1) # get the combo
            idx = 0 if combo.findText(ch.name) == -1 else combo.findText(ch.name) # get he index of the value in the combo
            combo.setCurrentIndex(idx)
            if is_commented:
                combo.setStyleSheet("background-color: rgb(200,255,200);")

            # col 0 (display)
            if is_commented:
                tbl.item(row, 0).setBackground(green_bg)

    def show_layout(self):
        # שורש: סקרול אופקי
        root = QVBoxLayout(self)
        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        scroll.setHorizontalScrollBarPolicy(Qt.ScrollBarAsNeeded) # show the scroll bar just if needed horizontally
        scroll.setVerticalScrollBarPolicy(Qt.ScrollBarAsNeeded) # show the scroll bar just if needed vertically
        root.addWidget(scroll)


        # קונטיינר פנימי שמחזיק את כל הטבלאות בשורה
        container = QWidget()
        # hbox = QHBoxLayout(container)
        hbox = QHBoxLayout()
        container.setLayout(hbox)
        hbox.setContentsMargins(8, 8, 8, 8)
        hbox.setSpacing(16)
        scroll.setWidget(container)

        # קבע כאן כמה כרטיסים לבנות:
        # למשל: כמות הכרטיסים מזוהה מקובץ → len(self.data_controller.sk_manager)
        num_cards = len(self.data_controller.sk_manager)

        for i in range(1, num_cards + 1):
            card_widget = self.init_table(i)
            hbox.addWidget(card_widget)

        # רווח בסוף לשיפור מראה
        spacer = QWidget()
        spacer.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Preferred)
        hbox.addWidget(spacer)

        self._initialized = True
        self.show()

    def update_comment(self, row_number, state, card_number, table):
        print(f"[class] SkLayout:\t [method] update_comment\t[start] ")
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.Unchecked)
            # table.cellWidget(row_number, 3).setChecked(False)
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
            is_success = self.data_controller.update_sk_name(card_number, row, name_text)
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

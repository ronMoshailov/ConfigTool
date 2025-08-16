from PyQt5.QtCore import Qt, QSignalBlocker
from PyQt5.QtGui import QBrush, QColor
from PyQt5.QtWidgets import QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy, QTableWidget, QLabel, \
    QTableWidgetItem, QComboBox

from controllers.data_controller import DataController



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


    def _build_card_table(self, card_number: int):
        #
        wrap = QWidget(self)
        vbox = QVBoxLayout()
        wrap.setLayout(vbox)
        vbox.setContentsMargins(0, 0, 0, 0)
        vbox.setSpacing(6)

        # title
        title = QLabel(f"card number {card_number}", wrap)
        title.setAlignment(Qt.AlignCenter)
        vbox.addWidget(title)

        tbl = QTableWidget(24, 4, wrap)
        was = tbl.blockSignals(True)
        tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
        tbl.verticalHeader().setVisible(False)
        tbl.itemChanged.connect(lambda item: self.update_comment(item, card_number))
        tbl.cellClicked.connect(lambda r, c, t=tbl, card=card_number: self.on_cell_clicked(t, r, c, card))

        # מידות עמודות (אופציונלי, כוונן כרצונך)
        tbl.setColumnWidth(0, 36)
        tbl.setColumnWidth(1, 60)
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 60)
        # tbl.setSizePolicy(QSizePolicy.Fixed, QSizePolicy.Preferred)
        # tbl.setMinimumWidth(tbl.columnWidth(0) + tbl.columnWidth(1) + tbl.columnWidth(2) + tbl.columnWidth(3) + 20)

        # color
        gray = QBrush(QColor(230, 230, 230))
        green = QColor(180,255,180)

        all_moves = self.data_controller.get_all_moves()
        all_moves_names = ["-"] + list((m.name for m in all_moves))
        all_channels_list = self.data_controller.get_all_sk_channels(card_number)

        # create table
        for r in range(24):
            # column 0, number channel
            col_0 = QTableWidgetItem(str(r + 1))
            col_0.setFlags(Qt.ItemIsSelectable | Qt.ItemIsEnabled)
            col_0.setBackground(gray)
            col_0.setTextAlignment(Qt.AlignCenter)
            tbl.setItem(r, 0, col_0)

            # column 1, name of the move
            combo = QComboBox()
            for name in all_moves_names:
                combo.addItem(name)
            tbl.setCellWidget(r, 1, combo)


            # column 2, color
            col_2 = QTableWidgetItem("")
            col_2.setTextAlignment(Qt.AlignCenter)
            tbl.setItem(r, 2, col_2)

            # column 3, check box
            col_3 = QTableWidgetItem("")
            col_3.setFlags(Qt.ItemIsUserCheckable | Qt.ItemIsEnabled)   # הפוך את התא לצ’קבוקס
            col_3.setCheckState(Qt.Unchecked)                           # מצב התחלתי
            tbl.setItem(r, 3, col_3)

        # set values
        for channel in all_channels_list:
            is_colored = False

            # column 3, check box
            item = tbl.item(channel.channel - 1, 3)
            if channel.is_comment:
                item.setCheckState(Qt.Checked)
                is_colored = True
                item.setBackground(QBrush(green))

            # column 0, number channel
            item = tbl.item(channel.channel - 1, 0)
            if is_colored:
                item.setBackground(QBrush(green))


            # column 1, name of the move
            item = tbl.cellWidget(channel.channel - 1, 1)
            if is_colored:
                item.setStyleSheet("background-color: rgb(200,255,200);")
            index = item.findText(channel.name)
            item.setCurrentIndex(index)

            # for name in all_moves_names:
            #     combo.addItem(name)
            # tbl.setCellWidget(r, 1, combo)


            # column 2, color
            item = tbl.item(channel.channel - 1, 2)
            if channel.color == "hwRed200":
                item.setText("🔴")
            elif channel.color == "hwGreen200":
                item.setText("🟢")
            elif channel.color == "hwAmber200":
                item.setText("🟡")
            if is_colored:
                item.setBackground(QBrush(green))


            # col_3 = QTableWidgetItem("")
            # col_3.setFlags(Qt.ItemIsUserCheckable | Qt.ItemIsEnabled)   # הפוך את התא לצ’קבוקס
            # col_3.setCheckState(Qt.Unchecked)                           # מצב התחלתי
            # tbl.setItem(r, 3, col_3)

            is_colored = False
        tbl.blockSignals(was)
        vbox.addWidget(tbl)  # ← חשוב!
        return wrap

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
            card_widget = self._build_card_table(i)
            hbox.addWidget(card_widget)

        # רווח בסוף לשיפור מראה
        spacer = QWidget()
        spacer.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Preferred)
        hbox.addWidget(spacer)

        self._initialized = True
        self.show()

    def update_comment(self, item, card_number):
        print(f"[class] SkLayout:\t [method] update_comment\t[start] ")
        table = item.tableWidget()
        was = table.blockSignals(True)

        if item.column() != 3:
            table.blockSignals(was)
            return None

        row_number = item.row()
        brush = QBrush(QColor(180, 255, 180)) if item.checkState() == Qt.Checked else QBrush(QColor(255, 255, 255))

        for col in range(table.columnCount()):
            print(f"-- row: {row_number<5}, col: {col<5}")
            cell = table.cellWidget(row_number, col)
            if col == 0:
                cell = table.item(row_number, col)
                print(f"card_number: {card_number}")
                self.data_controller.update_sk_comment(card_number, cell.text())
                continue
            if cell is not None:
                c = brush.color()
                cell.setStyleSheet(f"background-color: rgb({c.red()},{c.green()},{c.blue()});")
            else:
                it = table.item(row_number, col)
                if it is None:
                    it = QTableWidgetItem("")
                    table.setItem(row_number, col, it)
                it.setBackground(brush)
        table.blockSignals(was)
        print(f"[class] SkLayout:\t [method] update_comment\t[end] ")

    def update_color(self, table: QTableWidget, row: int, col: int, card_number: int):
        print(f"[class] SkLayout:\t [method] update_color\t[start] ")

        COL_COLOR = 2  # העמודה של האימוג'י
        if col != COL_COLOR:
            return

        # נחסום סיגנלים כדי שלא יופעל itemChanged לשווא
        blocker = QSignalBlocker(table)

        item = table.item(row, COL_COLOR)
        if item is None:
            item = QTableWidgetItem("")
            item.setTextAlignment(Qt.AlignCenter)
            table.setItem(row, COL_COLOR, item)

        cur = item.text()
        nxt = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        item.setText(nxt)

        # (אופציונלי) לצבוע רקע לפי הצבע
        bg = {
            "🔴": QColor(255, 210, 210),
            "🟡": QColor(255, 250, 200),
            "🟢": QColor(210, 255, 210),
        }.get(nxt, QColor(255, 255, 255))
        item.setBackground(QBrush(bg))
        print(f"[class] SkLayout:\t [method] update_comment\t[end] ")

        # אם צריך לעדכן מודל:
        # self.data_controller.update_sk_color(card_number, row+1, nxt)

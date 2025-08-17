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
        wrap = QWidget(self)
        vbox = QVBoxLayout(wrap)
        vbox.setContentsMargins(0, 0, 0, 0)
        vbox.setSpacing(6)

        title = QLabel(f"card number {card_number}", wrap)
        title.setAlignment(Qt.AlignCenter)
        vbox.addWidget(title)

        tbl = QTableWidget(24, 4, wrap)

        # חסימת סיגנלים של הטבלה בזמן בנייה
        was_tbl = tbl.blockSignals(True)

        tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
        tbl.verticalHeader().setVisible(False)
        tbl.setColumnWidth(0, 36)
        tbl.setColumnWidth(1, 160)  # נתתי קצת רוחב לקומבו שיהיה נוח
        tbl.setColumnWidth(2, 50)
        tbl.setColumnWidth(3, 60)

        gray = QBrush(QColor(230, 230, 230))
        green_bg = QBrush(QColor(180, 255, 180))

        all_moves = self.data_controller.get_all_moves()
        all_moves_names = ["-"] + [m.name for m in all_moves]
        all_channels_list = self.data_controller.get_all_sk_channels(card_number)

        # נבנה את השורות והווידג'טים
        for r in range(24):
            # עמודה 0: מספר ערוץ (לא ניתן לעריכה)
            it0 = QTableWidgetItem(str(r + 1))
            it0.setFlags(Qt.ItemIsSelectable | Qt.ItemIsEnabled)
            it0.setBackground(gray)
            it0.setTextAlignment(Qt.AlignCenter)
            tbl.setItem(r, 0, it0)

            # עמודה 1: קומבו של השם
            combo = QComboBox()
            combo.addItems(all_moves_names)
            tbl.setCellWidget(r, 1, combo)

            # עמודה 2: אימוג'י צבע (לא ניתן לעריכה ידנית)
            it2 = QTableWidgetItem("")
            it2.setTextAlignment(Qt.AlignCenter)
            it2.setFlags(it2.flags() & ~Qt.ItemIsEditable)
            tbl.setItem(r, 2, it2)

            # עמודה 3: צ'קבוקס להערה
            it3 = QTableWidgetItem("")
            it3.setFlags(Qt.ItemIsUserCheckable | Qt.ItemIsEnabled)
            it3.setCheckState(Qt.Unchecked)
            tbl.setItem(r, 3, it3)

        # אתחול ערכים מהמודל (כולל סט בחירה לקומבו) – נחסום סיגנלים מהקומבו עצמו בזמן setCurrentIndex
        for ch in all_channels_list:
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # עמודה 3: צ'קבוקס
            it3 = tbl.item(row, 3)
            it3.setCheckState(Qt.Checked if is_commented else Qt.Unchecked)

            # עמודה 0: רקע אם מסומן
            if is_commented:
                tbl.item(row, 0).setBackground(green_bg)

            # עמודה 1: קומבו – סט ערך ללא סיגנלים
            combo = tbl.cellWidget(row, 1)
            if isinstance(combo, QComboBox):
                idx = combo.findText(ch.name)
                if idx < 0:
                    idx = 0  # או להוסיף את הערך אם חשוב שיתווסף
                if idx != combo.currentIndex():
                    blocker = QSignalBlocker(combo)
                    combo.setCurrentIndex(idx)
                if is_commented:
                    combo.setStyleSheet("background-color: rgb(200,255,200);")

            # עמודה 2: אימוג'י צבע + רקע אם מסומן
            it2 = tbl.item(row, 2)
            it2.setText("🔴" if ch.color == "hwRed200"
                        else "🟢" if ch.color == "hwGreen200"
            else "🟡" if ch.color == "hwAmber200"
            else "")
            if is_commented:
                it2.setBackground(green_bg)

        # שחרור חסימת הטבלה
        tbl.blockSignals(was_tbl)

        # חיבורים לסיגנלים – רק אחרי שהכל מאותחל
        # שינוי צבע בלחיצה על תא (עמודה 2)
        tbl.cellClicked.connect(lambda r, c, t=tbl, card=card_number: self.update_color(t, r, c, card))
        # שינוי צ'קבוקס (עמודה 3)
        tbl.itemChanged.connect(lambda it, c=card_number: self.update_comment(it, c))
        # שינוי קומבו – לכל שורה חיבור אחד משלו
        for r in range(24):
            combo = tbl.cellWidget(r, 1)
            if isinstance(combo, QComboBox):
                combo.currentTextChanged.connect(
                    lambda _text, row=r, t=tbl, card=card_number: self.update_name(t, row, 1, card)
                )

        vbox.addWidget(tbl)
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
        if col != 2:
            return

        item = table.item(row, COL_COLOR)

        cur = item.text()
        nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        item.setText(nxt_color)

        # (אופציונלי) לצבוע רקע לפי הצבע
        bg = {
            "🔴": QColor(255, 210, 210),
            "🟡": QColor(255, 250, 200),
            "🟢": QColor(210, 255, 210),
        }.get(nxt_color, QColor(255, 255, 255))
        print(f"[class] SkLayout:\t [method] update_comment\t[end] ")
        # אם צריך לעדכן מודל:
        self.data_controller.update_sk_color(card_number, row)

    def update_name(self, table: QTableWidget, row: int, col: int, card_number: int):
        print(f"[class] SkLayout:\t [method] update_name\t[start] ")
        if col != 1:
            print(f"The column is not 1, it was {col}")
            return

        # שליפת הטקסט מתוך התא (באותו row/col)
        combo = table.cellWidget(row, col)
        with QSignalBlocker(combo), QSignalBlocker(table):
            name_text = combo.currentText()
            # כאן לא נוגעים ב־UI – רק מודל
            self.data_controller.update_sk_name(card_number, row, name_text)

        print(f"[class] SkLayout:\t [method] update_name\t[end] ")

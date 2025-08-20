from PyQt6.QtCore import Qt, QSignalBlocker
from PyQt6.QtGui import QBrush, QColor
from PyQt6.QtWidgets import (
    QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy,
    QTableWidget, QLabel, QPushButton, QTableWidgetItem, QComboBox,
    QCheckBox, QAbstractItemView
)

from config.colors import gray_color, light_green_color
from controllers.data_controller import DataController
from entities.log import Log


def clear_layout(layout):
    """ניקוי בטוח של לייאאוט: מוחק ווידג'טים ותתי־לייאאוטים, מתעלם מ־spacers."""
    while layout.count():
        it = layout.takeAt(0)
        w = it.widget()
        if w:
            w.setParent(None)
            w.deleteLater()
            continue
        child = it.layout()
        if child:
            clear_layout(child)
            child.setParent(None)
            child.deleteLater()
            continue
        # spacer: כלום


class SkPanel(QWidget):
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self):
        super().__init__()
        self.data_controller = DataController()

        # --- Build UI once ---
        self.root = QVBoxLayout(self)

        self.scroll = QScrollArea(self)
        self.scroll.setWidgetResizable(True)
        self.root.addWidget(self.scroll)

        self.container = QWidget(self)
        self.tables_layout = QHBoxLayout(self.container)
        self.tables_layout.setContentsMargins(0, 0, 0, 0)
        self.tables_layout.setSpacing(16)
        self.scroll.setWidget(self.container)

        # כפתור לדוגמה (הוספת SK)
        self.btn_add = QPushButton("הוסף SK", self)
        self.btn_add.clicked.connect(self.add_sk)
        self.root.addWidget(self.btn_add, 0, Qt.AlignRight)

        self.hide()

    # -------- add / refresh -------- #
    def add_sk(self):
        self.data_controller.add_sk()
        self.refresh_tables()

    def show_layout(self):
        self.refresh_tables()
        self.show()

    def refresh_tables(self):
        clear_layout(self.tables_layout)

        num_cards = self.data_controller.get_sk_count()
        for i in range(1, num_cards + 1):
            card_widget = self.init_table(i)
            card_widget.setSizePolicy(QSizePolicy.Fixed, QSizePolicy.Preferred)
            self.tables_layout.addWidget(card_widget)

        self.tables_layout.addStretch(1)
        self.container.adjustSize()
        self.container.updateGeometry()

    # -------- update handlers -------- #
    def update_comment(self, row_number, state, card_number, table):
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.Unchecked)
            return False

        was = table.blockSignals(True)
        is_success = self.data_controller.update_sk_comment(card_number, row_number + 1)
        if not is_success:
            Log.error("The change of the comment state didn't succeed")
            table.blockSignals(was)
            return False

        gray = QBrush(QColor(230, 230, 230))
        green = QBrush(QColor(180, 255, 180))
        white = QBrush(QColor(255, 255, 255))

        if state == Qt.Checked:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(green)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(
                f"QComboBox {{ background-color: rgb({green.color().red()},{green.color().green()},{green.color().blue()}); }}"
            )
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(green)
        else:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(gray)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(
                f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}"
            )
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(white)

        table.blockSignals(was)
        return True

    def update_color(self, table: QTableWidget, row: int, col: int, card_number: int):
        if col != 2:
            return
        combo = table.cellWidget(row, 1)
        item = table.item(row, 2)
        with QSignalBlocker(combo), QSignalBlocker(table):
            if combo.currentText() == "-":
                Log.error("no move was chosen")
                return False

        cur = item.text()
        nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")

        if self.data_controller.update_sk_color(card_number, row):
            item.setText(nxt_color)
        return True

    def update_name(self, table: QTableWidget, row: int, col: int, card_number: int):
        combo = table.cellWidget(row, col)
        white = QBrush(QColor(255, 255, 255))
        gray = QBrush(QColor(230, 230, 230))

        with QSignalBlocker(combo), QSignalBlocker(table):
            name_text = "" if combo.currentText() == "-" else combo.currentText()

            if not self.data_controller.update_sk_name(card_number, row, name_text):
                Log.error("update name failed")
                return False

            if not self.data_controller.update_sk_color(card_number, row):
                Log.error("update color after name failed")
                return False

            if name_text == "":
                (table.item(row, 0) or QTableWidgetItem()).setBackground(gray)
                table.cellWidget(row, 1).setStyleSheet(
                    f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}"
                )
                (table.item(row, 2) or QTableWidgetItem()).setBackground(white)
                table.item(row, 2).setText("")
                table.cellWidget(row, 3).setStyleSheet("margin-left:auto; margin-right:auto;")
                table.cellWidget(row, 3).setCheckState(Qt.Unchecked)

        return True

    # -------- table build/fill -------- #
    def init_table(self, card_number: int) -> QWidget:
        wrap = QWidget()
        column_layout = QVBoxLayout(wrap)

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

        tbl = QTableWidget(24, 4, wrap)
        tbl.setSelectionMode(QAbstractItemView.NoSelection)
        tbl.setFocusPolicy(Qt.NoFocus)

        was_tbl = tbl.blockSignals(True)
        self._create_table(tbl, card_number)
        self._fill_table(tbl, card_number)
        tbl.blockSignals(was_tbl)

        # col 1 (name) changes
        for r in range(24):
            combo = tbl.cellWidget(r, 1)
            combo.currentTextChanged.connect(
                lambda _text, row=r, t=tbl, card=card_number: self.update_name(t, row, 1, card)
            )

        # col 2 (color) click
        tbl.cellClicked.connect(
            lambda r, c, t=tbl, card=card_number: self.update_color(t, r, c, card)
        )

        # אין itemChanged על עמודה 3 (יש CheckBox כ־cellWidget עם stateChanged)
        column_layout.addWidget(tbl)
        return wrap

    def _create_table(self, tbl: QTableWidget, card_number: int):
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

        for r in range(24):
            # col 0 (display)
            col_0 = QTableWidgetItem(str(r + 1))
            col_0.setFlags(Qt.NoItemFlags)
            col_0.setBackground(gray)
            col_0.setTextAlignment(Qt.AlignCenter)
            tbl.setItem(r, 0, col_0)

            # col 1 (combo)
            combo = QComboBox()
            combo.addItems(all_moves_names)
            combo.wheelEvent = lambda event: None
            tbl.setCellWidget(r, 1, combo)

            # col 2 (color)
            col_2 = QTableWidgetItem("")
            col_2.setTextAlignment(Qt.AlignCenter)
            col_2.setFlags(col_2.flags() & ~Qt.ItemIsEditable)
            tbl.setItem(r, 2, col_2)

            # col 3 (check box)
            col_3 = QCheckBox()
            col_3.setChecked(False)
            col_3.setStyleSheet("margin-left:auto; margin-right:auto;")
            tbl.setCellWidget(r, 3, col_3)
            col_3.stateChanged.connect(
                lambda state, row=r, t=tbl, card=card_number: self.update_comment(row, state, card, t)
            )

    def _fill_table(self, tbl: QTableWidget, card_number: int):
        all_channels_list = self.data_controller.get_all_sk_channels(card_number)
        green_bg = QBrush(light_green_color)

        for ch in all_channels_list:
            row = ch.channel - 1
            is_commented = bool(ch.is_comment)

            # col 3 (check box)
            it3 = tbl.cellWidget(row, 3)
            was = it3.blockSignals(True)
            it3.setCheckState(Qt.Checked if is_commented else Qt.Unchecked)
            it3.blockSignals(was)

            # col 2 (color)
            it2 = tbl.item(row, 2)
            it2.setText(
                "🔴" if ch.color == "hwRed200"
                else "🟢" if ch.color == "hwGreen200"
                else "🟡" if ch.color == "hwAmber200"
                else ""
            )
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

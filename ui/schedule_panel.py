from PyQt6.QtCore import Qt, QSignalBlocker
from PyQt6.QtGui import QBrush, QColor
from PyQt6.QtWidgets import (
    QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy,
    QTableWidget, QLabel, QPushButton, QTableWidgetItem, QComboBox,
    QCheckBox, QAbstractItemView, QTimeEdit
)

from config.colors import gray_color, light_green_color
from controllers.data_controller import DataController
from entities.DoubleClickButton import DoubleClickButton
from entities.log import Log

class SchedulePanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== style =============== #
        root_style = """
        /* ********************************* title ********************************* */
        QLabel#title {
                font-size: 36px;
                font-weight: bold;
                color: #2c3e50;
                padding: 4px 6px;
                border-radius: 6px;
                background: #d2e1ff;
                border: 1px solid #d0d7de;
                min-height: 60px;
            }
            QLabel#title:hover {
                background: #d6eaf8;
                border: 1px solid #3498db;
            }
            /* ********************************* QCheckBox ********************************* */
            QCheckBox#checkbox_comment{
                margin-left:auto; 
                margin-right:auto;
            }
            /* ********************************* QPushButton ********************************* */
            QPushButton#remove_button {
                background-color: #e74c3c;       /* אדום */
                color: white;                    /* טקסט לבן */
                border: none;                    /* בלי גבול */
                border-radius: 12px;             /* פינות מעוגלות */
                padding: 6px 14px;               /* ריווח פנימי */
                font-size: 14px;                 /* גודל טקסט */
                font-weight: bold;               /* טקסט מודגש */
            }
            QPushButton#remove_button:hover {
                background-color: #c0392b;       /* אדום כהה יותר ב-hover */
            }
            QPushButton#remove_button:pressed {
                background-color: #a93226;       /* עוד כהה יותר בלחיצה */
            }
            
            QPushButton#add_button {
                background-color: #27ae60;       /* ירוק */
                color: white;                    /* טקסט לבן */
                border: none;                    /* בלי גבול */
                border-radius: 12px;             /* פינות מעוגלות */
                padding: 6px 14px;               /* ריווח פנימי */
                font-size: 14px;                 /* גודל טקסט */
                font-weight: bold;               /* טקסט מודגש */
            }
            QPushButton#add_button:hover {
                background-color: #1e8449;       /* ירוק כהה יותר ב-hover */
            }
            QPushButton#add_button:pressed {
                background-color: #196f3d;       /* עוד כהה יותר בלחיצה */
            }

        """

        # =============== layout =============== #
        root_layout = QVBoxLayout()
        self.schedule_layout = QHBoxLayout()

        # =============== widget =============== #
        self.btn_add = QPushButton("עדכן")

        root_layout.addLayout(self.schedule_layout)
        root_layout.addWidget(self.btn_add)


        #
        # scroll_container = QWidget()
        #
        # # self.btn_add.clicked.connect(self._add_sk)
        # # self.btn_add.setObjectName("add_button")
        #
        # scroll_area = QScrollArea()
        # scroll_area.setWidgetResizable(True)
        #
        # # =============== layout =============== #
        # root_layout = QVBoxLayout()
        #
        # self.tables_layout = QHBoxLayout()
        # self.tables_layout.setContentsMargins(0, 0, 0, 0)
        # self.tables_layout.setSpacing(16)
        #
        # # =============== connect between layout and widgets =============== #
        # root_layout.addWidget(scroll_area)
        # root_layout.addWidget(self.btn_add, 0, Qt.AlignmentFlag.AlignRight)
        #
        # scroll_area.setWidget(scroll_container)
        # scroll_container.setLayout(self.tables_layout)
        #
        # # =============== self =============== #
        # self.setLayout(root_layout)
        # self.setStyleSheet(root_style)
        self.hide()

    #
    # # --------------- add methods --------------- #
    # def _add_sk(self):
    #     """
    #     This method add SK card in DB and then update the panel.
    #
    #     :return: None
    #     """
    #     self.data_controller.add_sk()
    #     self._refresh_tables()
    #
    # # --------------- update methods --------------- #
    # def _update_comment(self, table, card_number, row_number, state):
    #     """
    #     This method update the comment in DB and if succeeded color the row.
    #
    #     :param table: The table.
    #     :param card_number: number of the SK card.
    #     :param row_number: row number (channel) of the SK card.
    #     :param state: state of the checkbox
    #     :return: None
    #     """
    #     # disable the option to check if there is no move
    #     if table.cellWidget(row_number, 1).currentText() == "-":
    #         table.cellWidget(row_number, 3).setCheckState(Qt.CheckState.Unchecked)
    #         return False
    #
    #     was = table.blockSignals(True) # block signals
    #
    #     is_success = self.data_controller.update_sk_comment(card_number, row_number + 1) #
    #     if not is_success:
    #         self.data_controller.write_log("The change of the comment state didn't succeed","r")
    #         table.blockSignals(was)     # release signal
    #         return False
    #
    #     self.data_controller.write_log("The change of the comment succeeded","g")
    #
    #     gray = QBrush(QColor(230, 230, 230))
    #     green = QBrush(QColor(180, 255, 180))
    #     white = QBrush(QColor(255, 255, 255))
    #
    #     if Qt.CheckState(state) == Qt.CheckState.Checked:
    #         # col 0
    #         (table.item(row_number, 0) or QTableWidgetItem()).setBackground(green)
    #         # col 1
    #         table.cellWidget(row_number, 1).setStyleSheet(
    #             f"QComboBox {{ background-color: rgb({green.color().red()},{green.color().green()},{green.color().blue()}); }}"
    #         )
    #         # col 2
    #         (table.item(row_number, 2) or QTableWidgetItem()).setBackground(green)
    #     else:
    #         # col 0
    #         (table.item(row_number, 0) or QTableWidgetItem()).setBackground(gray)
    #         # col 1
    #         table.cellWidget(row_number, 1).setStyleSheet(
    #             f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}"
    #         )
    #         # col 2
    #         (table.item(row_number, 2) or QTableWidgetItem()).setBackground(white)
    #
    #     table.blockSignals(was)
    #     return True
    #
    # def _update_color(self, table: QTableWidget, card_number: int, row: int, col: int):
    #     if col != 2:
    #         return False
    #
    #     combo = table.cellWidget(row, 1)
    #     item = table.item(row, 2)
    #     move_name = combo.currentText()
    #
    #     with QSignalBlocker(combo), QSignalBlocker(table):
    #         if move_name == "-":
    #             self.data_controller.write_log("update color failed (no move was chosen)", "r")
    #             return False
    #
    #     cur = item.text()
    #     nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
    #
    #     if self.data_controller.update_sk_color(card_number, row):
    #         self.data_controller.write_log("update color succeeded", "g")
    #         item.setText(nxt_color)
    #         return True
    #     self.data_controller.write_log("update color failed in the controller", "r")
    #     return False
    #
    # def _update_name(self, table: QTableWidget, card_number: int, row: int, col: int):
    #     combo = table.cellWidget(row, col)
    #     move_name = combo.currentText()
    #
    #     white = QBrush(QColor(255, 255, 255))
    #     gray = QBrush(QColor(230, 230, 230))
    #
    #     with QSignalBlocker(combo), QSignalBlocker(table):
    #         move_name = "" if move_name == "-" else move_name
    #
    #         if not self.data_controller.update_sk_name(card_number, row, move_name):
    #             self.data_controller.write_log("update name failed in the controller", "r")
    #             return False
    #
    #         if not self.data_controller.update_sk_color(card_number, row):
    #             self.data_controller.write_log("update color after name failed in the controller", "r")
    #             return False
    #
    #         if move_name == "":
    #             (table.item(row, 0) or QTableWidgetItem()).setBackground(gray)
    #             table.cellWidget(row, 1).setStyleSheet(
    #                 f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}"
    #             )
    #             (table.item(row, 2) or QTableWidgetItem()).setBackground(white)
    #             table.item(row, 2).setText("")
    #             table.cellWidget(row, 3).setStyleSheet("margin-left:auto; margin-right:auto;")
    #             table.cellWidget(row, 3).setCheckState(Qt.CheckState.Unchecked)
    #     return True
    #
    # # --------------- remove methods --------------- #
    # def _remove_sk(self, card_number):
    #     if self.data_controller.get_sk_count() == 1:
    #         self.data_controller.write_log("you can't have 0 sk cards", "r")
    #         return False
    #     if self.data_controller.remove_sk(card_number):
    #         self.data_controller.write_log("remove sk succeeded", "g")
    #         self._refresh_tables()
    #         return True
    #     self.data_controller.write_log("remove sk failed", "r")
    #     return False

    # --------------- general methods --------------- #
    def show_panel(self):
        # clear the table
        while self.schedule_layout.count():  # as long a 'QLayoutItem' exist in 'tables_layout'
            print("A")
            it = self.schedule_layout.takeAt(0)  # disconnect the first 'QLayoutItem' (can be just another layout)
            w = it.widget()
            if w:
                w.deleteLater()

        # for each SK card initialize a table
        for i in range(1, 8):
            table_widget = self._init_table(i)
            self.schedule_layout.addWidget(table_widget)

        # move all tables left
        self.schedule_layout.addStretch(1)

        self.show()
    #
    #
    def _init_table(self, table_num: int):
        # widget that holds title and table
        wrap = QWidget()
        day = {1: "ראשון", 2: "שני", 3: "שלישי", 4: "רביעי", 5: "חמישי", 6: "שישי", 7: "שבת"}.get(table_num, "לא קיים")

        # set title
        title = QLabel(f"{day}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        btn_remove = DoubleClickButton("❌")
        # btn_remove.doubleClicked.connect(lambda _, c=card_number: self._remove_sk(c))
        btn_remove.setObjectName("remove_button")

        time_edit = QTimeEdit()
        time_edit.setDisplayFormat("HH:mm")  # שעות:דקות בלבד

        combo_num_prog = QComboBox()
        combo_num_prog.addItems([str(i) for i in range(1, 33)])

        # set table
        tbl = QTableWidget(1, 3)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.setHorizontalHeaderLabels(["", "שעה", "מס' תוכנית"])
        tbl.setCellWidget(0, 0, btn_remove)
        tbl.setCellWidget(0, 1, time_edit)
        tbl.setCellWidget(0, 2, combo_num_prog)


        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)

        #
        wrap.setLayout(column_layout)

        # create and fill the table
        was_tbl = tbl.blockSignals(True)
        # self._create_table(tbl, card_number)    # create the table with no values
        # self._fill_table(tbl, table_num)      # set values
        tbl.blockSignals(was_tbl)               # release signals
        #
        # # col 1 (name) changes (add signal)
        # for row_number in range(24):
        #     combo = tbl.cellWidget(row_number, 1)
        #     combo.currentTextChanged.connect(lambda _text, row_num = row_number: self._update_name(tbl, card_number, row_num, 1))
        #
        # # col 2 (color) click (add signal)
        # tbl.cellClicked.connect(lambda row_num, col_num: self._update_color(tbl, card_number, row_num, col_num))

        return wrap
    #
    # def _create_table(self, tbl: QTableWidget, card_number: int):
    #     """
    #     This method create the table with all the rows and columns (without values).
    #
    #     :param tbl: The 'QTableWidget' to create.
    #     :param card_number: number of the SK card.
    #     :return: None
    #     """
    #     tbl.setHorizontalHeaderLabels(["#", "value", "color", "comment"])
    #     tbl.setFixedWidth(351)
    #     tbl.verticalHeader().setVisible(False)
    #     tbl.setColumnWidth(0, 40)
    #     tbl.setColumnWidth(1, 160)
    #     tbl.setColumnWidth(2, 50)
    #     tbl.setColumnWidth(3, 90)
    #
    #     gray = QBrush(gray_color)
    #
    #     all_moves = self.data_controller.get_all_moves()
    #     all_moves_names = ["-"] + [m.name for m in all_moves]
    #
    #     # set columns (without values)
    #     for r in range(24):
    #         # col 0 (display)
    #         col_0 = QTableWidgetItem(str(r + 1))
    #         col_0.setFlags(Qt.ItemFlag.NoItemFlags)
    #         col_0.setBackground(gray)
    #         col_0.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
    #         tbl.setItem(r, 0, col_0)
    #
    #         # col 1 (combo)
    #         combo = QComboBox()
    #         combo.addItems(all_moves_names)
    #         combo.wheelEvent = lambda event: None # override the wheel mouse event (disable the wheel mouse)
    #         tbl.setCellWidget(r, 1, combo)
    #
    #         # col 2 (color)
    #         col_2 = QTableWidgetItem("")
    #         col_2.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
    #         col_2.setFlags(col_2.flags() & ~Qt.ItemFlag.ItemIsEditable)
    #         tbl.setItem(r, 2, col_2)
    #
    #         # col 3 (check box)
    #         col_3 = QCheckBox()
    #         col_3.setChecked(False)
    #         col_3.setObjectName("checkbox_comment")
    #         tbl.setCellWidget(r, 3, col_3)
    #         col_3.stateChanged.connect(lambda state, row=r: self._update_comment(tbl, card_number, row, state))
    #
    # def _fill_table(self, tbl: QTableWidget, table_num: int):
    #
    #     all_channels_list = self.data_controller.get_all_sk_channels(card_number)
    #     green_bg = QBrush(light_green_color)
    #
    #     for ch in all_channels_list:
    #         # get data
    #         row = ch.channel - 1
    #         is_commented = bool(ch.is_comment)
    #
    #         # col 3 (check box)
    #         it3 = tbl.cellWidget(row, 3)
    #         was = it3.blockSignals(True)
    #         it3.setCheckState(Qt.CheckState.Checked if is_commented else Qt.CheckState.Unchecked)
    #         it3.blockSignals(was)
    #
    #         # col 2 (color)
    #         it2 = tbl.item(row, 2)
    #         it2.setText("🔴" if ch.color == "hwRed200" else "🟢" if ch.color == "hwGreen200" else "🟡" if ch.color == "hwAmber200" else "")
    #         if is_commented:
    #             it2.setBackground(green_bg)
    #
    #         # col 1 (combo)
    #         combo = tbl.cellWidget(row, 1)
    #         idx = 0 if combo.findText(ch.name) == -1 else combo.findText(ch.name)
    #         combo.setCurrentIndex(idx)
    #         if is_commented:
    #             combo.setStyleSheet("background-color: rgb(200,255,200);")
    #
    #         # col 0 (display)
    #         if is_commented:
    #             tbl.item(row, 0).setBackground(green_bg)


from PyQt6.QtCore import Qt, QSignalBlocker, QTime
from PyQt6.QtGui import QBrush, QColor
from PyQt6.QtWidgets import (
    QWidget, QScrollArea, QVBoxLayout, QHBoxLayout, QSizePolicy,
    QTableWidget, QLabel, QPushButton, QTableWidgetItem, QComboBox,
    QCheckBox, QAbstractItemView, QTimeEdit, QAbstractSpinBox, QHeaderView
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

        # =============== widget =============== #

        # =============== layout =============== #
        self.root_layout = QHBoxLayout()
        self.setLayout(self.root_layout)

        # =============== style =============== #

        self.hide()

    #
    # # --------------- add methods --------------- #

    # # --------------- update methods --------------- #

    # # --------------- remove methods --------------- #

    # --------------- general methods --------------- #
    def show_panel(self):
        # clear the table
        self.clear_root_layout()

        for idx in range (0, 7):
            schedule_list = self.data_controller.get_schedule_list(idx)

            # for each SK card initialize a table
            schedule_column = self._init_schedule_column(idx + 1)
            self._fill_table(idx, schedule_column.table)
            self.root_layout.addWidget(schedule_column, stretch=1)
        self.show()


    def _init_schedule_column(self, table_num: int):
        # widget that holds title and table
        wrap = QWidget()
        wrap.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        #
        day = {1: "ראשון", 2: "שני", 3: "שלישי", 4: "רביעי", 5: "חמישי", 6: "שישי", 7: "שבת"}.get(table_num, "לא קיים")

        # set title
        title = QLabel(f"{day}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # set table
        tbl = self._init_table()

        btn_add = DoubleClickButton("➕")

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addWidget(btn_add)

        # set root
        wrap.setLayout(column_layout)

        # create and fill the table
        was_tbl = tbl.blockSignals(True)
        tbl.blockSignals(was_tbl)               # release signals
        wrap.table = tbl
        return wrap

    def clear_root_layout(self):
        while self.root_layout.count():  # as long a 'QLayoutItem' exist in 'tables_layout'
            it = self.root_layout.takeAt(0)  # disconnect the first 'QLayoutItem' (can be just another layout)
            w = it.widget()
            if w:
                w.deleteLater()

    def _fill_table(self,table_num, table):
        schedule_list = self.data_controller.get_schedule_list(table_num)

        for schedule in schedule_list:
            row = table.rowCount()
            table.insertRow(row)

            btn_remove = DoubleClickButton("❌")
            # Add connect
            btn_remove.setObjectName("remove_button")

            time_edit = QTimeEdit()
            time_edit.setDisplayFormat("HH:mm")
            time_edit.setButtonSymbols(QAbstractSpinBox.ButtonSymbols.NoButtons)
            time_edit.setTime(QTime(schedule.hour, schedule.minute))

            combo_num_prog = QComboBox()
            combo_num_prog.addItems([str(i) for i in range(1, 33)])
            combo_num_prog.setCurrentText(str(schedule.program_num))

            table.setCellWidget(row, 0, btn_remove)
            table.setCellWidget(row, 1, time_edit)
            table.setCellWidget(row, 2, combo_num_prog)

    def _init_table(self):
        tbl = QTableWidget(0, 3)
        tbl.setObjectName("tbl")
        tbl.setColumnCount(3)
        tbl.setRowCount(0)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.verticalHeader().setVisible(False)
        tbl.setHorizontalHeaderLabels(["", "שעה", "מס' תוכנית"])
        tbl.setColumnWidth(0, 10)  # עמודה 0 ברוחב 60px
        tbl.setColumnWidth(1, 45)  # עמודה 1 ברוחב 60px
        tbl.horizontalHeader().setSectionResizeMode(2, QHeaderView.ResizeMode.Stretch)
        return tbl
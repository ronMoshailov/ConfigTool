from PyQt6.QtCore import Qt, QTime
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QSizePolicy, QTableWidget, QLabel, QComboBox, \
    QAbstractItemView, QTimeEdit, QAbstractSpinBox, QHeaderView, QPushButton

from controllers.data_controller import DataController
from entities.DoubleClickButton import DoubleClickButton

class SchedulePanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== layout =============== #
        self.main_layout = QVBoxLayout()
        self.root_layout = QHBoxLayout()

        self.main_layout.addLayout(self.root_layout)
        self.setLayout(self.main_layout)

        # =============== ****** =============== #
        self.table_list = []
        self.btn_add = QPushButton("עדכן")

        # =============== style =============== #
        root_style = """
        /* ********************************* panel ********************************* */
        #schedulePanel {
            border-radius: 20px;
            border: 1px solid #1a98ff;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #94cfff,
                stop:1 #f0f8ff
            );
        }
        
        /* ********************************* column wrap ********************************* */
        #column_wrap {
            border-radius: 15px;
            border: 1px solid #0077d7;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #cce7ff,
                stop:1 #ffffff
            );
            padding: 6px;
        }
        
        /* ********************************* title ********************************* */
        QLabel#title {
            font-size: 20px;
            font-weight: bold;
            color: #2c3e50;
            padding: 6px 10px;
            border-radius: 10px;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #d2e1ff,
                stop:1 #f0f8ff
            );
            border: 1px solid #1a98ff;
        }
        QLabel#title:hover {
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #eaf2ff,
                stop:1 #ffffff
            );
            border: 1px solid #3498db;
        }
        
        /* ********************************* table ********************************* */
        QTableWidget#tbl {
            border: 1px solid #a6c8ff;
            border-radius: 8px;
            gridline-color: #d0e6ff;
            background: #ffffff;
            selection-background-color: #cce0ff;
            selection-color: #2c3e50;
        }
        QHeaderView::section {
            background: #d6eaff;
            border: 1px solid #a6c8ff;
            padding: 4px;
            font-weight: bold;
            color: #2c3e50;
        }
        
        /* ********************************* buttons ********************************* */
        QPushButton#remove_button {
            background-color: #e74c3c;   /* אדום */
            color: white;
            border: none;
            border-radius: 8px;
            padding: 2px 6px;
            font-size: 28px;
        }
        QPushButton#remove_button:hover {
            background-color: #c0392b;
        }
        
        QPushButton {
            background-color: #1a98ff;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 6px 10px;
            font-size: 14px;
        }
        QPushButton:hover {
            background-color: #0077d7;
        }
        
        /* ********************************* editors ********************************* */
        QTimeEdit#edit_time {
            border: 1px solid #a6c8ff;
            border-radius: 6px;
            padding: 2px 4px;
            background: #f9fcff;
            selection-background-color: #1a98ff;
        }
        
        QComboBox#combo_num_prog {
            border: 1px solid #a6c8ff;
            border-radius: 6px;
            padding: 2px 6px;
            background: #ffffff;
        }
        QComboBox#combo_num_prog::drop-down {
            border-left: 1px solid #a6c8ff;
        }
        QComboBox#combo_num_prog QAbstractItemView {
            border: 1px solid #a6c8ff;
            selection-background-color: #cce0ff;
            background: #ffffff;
        }
        """
        self.setObjectName("schedulePanel")
        self.setStyleSheet(root_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.hide()

    # --------------- add methods --------------- #
    def _add(self, table_num):
        self.data_controller.add_schedule_row(table_num)
        self.show_panel()

    # --------------- update methods --------------- #
    def _update(self):
        if not self._check_time():
            return False
        self.data_controller.update_schedule(self.table_list)
        return True

    # --------------- remove methods --------------- #
    def _remove(self, table_num, table, btn):
        for row_index in range(table.rowCount()):
            if table.cellWidget(row_index, 0) is btn:
                if self.data_controller.remove_schedule_row(table_num, row_index):
                    table.removeRow(row_index)
                    break


    # --------------- general methods --------------- #
    def show_panel(self):
        """
        This method clear the root layout from all the tables and recreate it again from DB.

        :return: None
        """
        # clear the table
        self.clear_root_layout()
        self.table_list.clear()

        self.btn_add.clicked.connect(self._update)
        # self.btn_add.setObjectName("add_button")
        self.main_layout.addWidget(self.btn_add)
        for idx in range (0, 7):
            schedule_column, table = self._init_schedule_column(idx + 1)    # create schedule_column
            self.table_list.append(table)
            self._fill_table(idx, table)                                    # fill table with values
            self.root_layout.addWidget(schedule_column, stretch=1)          # add schedule_column to layout
        self.show()                                                         # show panel


    def _init_schedule_column(self, table_num: int):
        """
        This method initialize (no values) column that contain the elements.

        :param table_num: number of the table
        :return: column widget, table widget
        """
        # widget that holds title and table
        wrap = QWidget()
        wrap.setObjectName("column_wrap")
        day = {1: "ראשון", 2: "שני", 3: "שלישי", 4: "רביעי", 5: "חמישי", 6: "שישי", 7: "שבת"}.get(table_num, "לא קיים")

        # title
        title = QLabel(f"{day}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # set table
        tbl = self._init_table()

        #  button
        btn_add = QPushButton("+")
        btn_add.clicked.connect(lambda: self._add(table_num))

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addWidget(btn_add)

        # set root
        wrap.setLayout(column_layout)

        # create and fill the table
        # was_tbl = tbl.blockSignals(True)
        # tbl.blockSignals(was_tbl)               # release signals
        return wrap, tbl

    def clear_root_layout(self):
        """
        Clear the root layout.

        :return: None
        """
        while self.root_layout.count():  # as long a 'QLayoutItem' exist in 'tables_layout'
            it = self.root_layout.takeAt(0)  # disconnect the first 'QLayoutItem' (can be just another layout)
            w = it.widget()
            if w:
                w.deleteLater()

    def _fill_table(self,table_num, table):
        """
        This method fill the table with all the schedules of this day

        :param table_num: number of the table
        :param table: Table QTableWidget
        :return: None
        """
        schedule_list = self.data_controller.get_schedule_list(table_num) # get schedule list of this table number

        for schedule in schedule_list:
            row = table.rowCount() # get the number row that empty
            table.insertRow(row)   # add new row in the end

            btn_remove = QPushButton("x")
            btn_remove.setObjectName("remove_button")
            btn_remove.clicked.connect(lambda row_num=row, tbl_num=table_num, btn=btn_remove: self._remove(tbl_num + 1, table, btn))

            time_edit = QTimeEdit()
            time_edit.setObjectName("edit_time")
            time_edit.setDisplayFormat("HH:mm")
            time_edit.setButtonSymbols(QAbstractSpinBox.ButtonSymbols.NoButtons)
            time_edit.setTime(QTime(schedule.hour, schedule.minute))

            combo_num_prog = QComboBox()
            combo_num_prog.setObjectName("combo_num_prog")
            combo_num_prog.addItems([str(i) for i in range(1, 33)])
            combo_num_prog.setCurrentText(str(schedule.program_num))

            table.setCellWidget(row, 0, btn_remove)
            table.setCellWidget(row, 1, time_edit)
            table.setCellWidget(row, 2, combo_num_prog)

    def _init_table(self):
        """
        Initialize the table with no values

        :return: Table QTableWidget
        """
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

    def _check_time(self):
        prev = None
        current = None

        for idx, table in enumerate (self.table_list):
            prev = None
            current = None
            row_count = table.rowCount()
            for row in range(row_count):
                prev = current
                current = table.cellWidget(row, 1).time()
                if prev is None:
                    continue
                if current is None:
                    continue
                if current <= prev:
                    self.data_controller.write_log(f"in table number {idx + 1} there is a problem with the time", "r")
                    return False
        self.data_controller.write_log(f"עודכן", "g")
        return True



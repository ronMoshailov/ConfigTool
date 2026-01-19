from PyQt6.QtCore import Qt, QTime
from PyQt6.QtGui import QFont
from PyQt6.QtWidgets import QWidget, QPushButton, QCheckBox, QHBoxLayout, QVBoxLayout, QLabel, QTimeEdit, \
    QAbstractSpinBox, QComboBox, QAbstractItemView, QHeaderView, QTableWidget, QMessageBox

from Config.special import clear_widget_from_layout
from Config.style import schedule_panel_style


class ScheduleView(QWidget):
    def __init__(self):
        super().__init__()

        # =============== Controller Methods =============== #
        self.get_all_channels_method    = None
        self.remove_row_method          = None
        self.add_row_method             = None
        self.update_schedule_method     = None

        # =============== QPushButton =============== #
        self.btn_add = QPushButton("עדכן")
        self.btn_add.setObjectName("update_button")
        self.btn_add.clicked.connect(lambda: self.update_schedule_method(self.check_box.isChecked(), self.table_list))

        # =============== CheckBox =============== #
        self.check_box = QCheckBox("ראשון עד חמישי")
        self.check_box.setObjectName("check_box")
        self.check_box.clicked.connect(lambda: self._enable_mon_thu())
        self.check_box.setChecked(True)

        # =============== Button Layout =============== #
        self.bottom_layout = QHBoxLayout()
        self.bottom_layout.addWidget(self.check_box, alignment=Qt.AlignmentFlag.AlignCenter)
        self.bottom_layout.addWidget(self.btn_add)

        # =============== Schedule Layout =============== #
        self.schedule_layout = QHBoxLayout()

        # =============== Root Layout =============== #
        self.root_layout = QVBoxLayout()
        self.root_layout.addLayout(self.schedule_layout)
        self.root_layout.addLayout(self.bottom_layout)

        # =============== Data =============== #
        self.table_list = []
        self.add_row_btn_list = []

        # =============== Self =============== #
        self.setLayout(self.root_layout)
        self.hide()

        # =============== Style =============== #
        self.setObjectName("schedulePanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setStyleSheet(schedule_panel_style)

    def show_view(self):
        # clear the table
        clear_widget_from_layout([self.schedule_layout])
        self.table_list.clear()
        self.add_row_btn_list.clear()

        # self.btn_add.setObjectName("add_button")
        for idx in range (0, 7):
            schedule_column, table = self._initialize_schedule_column(idx + 1)      # create schedule_column
            self.table_list.append(table)
            self._fill_table(idx+1, table)                                            # fill table with values
            self.schedule_layout.addWidget(schedule_column, stretch=1)              # add schedule_column to layout
        self._enable_mon_thu()
        self.show()

    def hide_view(self):
        self.hide()

    # ============================== CRUD ============================== #
    def _create_table(self):
        """
        Create the table with no values

        :return: Table QTableWidget
        """
        tbl = QTableWidget(0, 3)
        tbl.setObjectName("tbl")
        tbl.setColumnCount(3)                                                   # set 3 columns
        tbl.setRowCount(0)                                                      # reset rows
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)       # disable visual elements when clicked on table
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)                              #
        tbl.verticalHeader().setVisible(False)                                  #
        tbl.setHorizontalHeaderLabels(["", "שעה", "מס' תוכנית"])                # set headers
        tbl.setColumnWidth(0, 10)                                  # עמודה 0 ברוחב 60px
        tbl.setColumnWidth(1, 65)                                  # עמודה 1 ברוחב 60px
        tbl.horizontalHeader().setSectionResizeMode(2, QHeaderView.ResizeMode.Stretch)
        return tbl

    # ============================== Layout ============================== #

    # ============================== Logic ============================== #
    def _fill_table(self,table_num, table):
        """
        This method fill the table with all the schedules of this day

        :param table_num: number of the table
        :param table: Table QTableWidget
        :return: None
        """
        schedule_list = self.get_all_channels_method(table_num)
        for schedule in schedule_list:
            row = table.rowCount() # get the number of rows
            table.insertRow(row)   # add new row in the end

            font = QFont("Roboto")

            btn_remove = QPushButton("x")
            btn_remove.setObjectName("remove_button")
            btn_remove.clicked.connect(lambda _, tbl_num_arg=table_num, tbl=table, btn=btn_remove: self.remove_row_method(tbl_num_arg, tbl, btn))
            btn_remove.setFont(font)

            time_edit = QTimeEdit()
            time_edit.setObjectName("edit_time")
            time_edit.setDisplayFormat("HH:mm")
            time_edit.setButtonSymbols(QAbstractSpinBox.ButtonSymbols.NoButtons)
            time_edit.setTime(QTime(schedule.hour, schedule.minute))
            time_edit.setFont(font)

            combo_num_prog = QComboBox()
            combo_num_prog.setObjectName("combo_num_prog")
            combo_num_prog.addItems([str(i) for i in range(1, 33)])
            combo_num_prog.setCurrentText(str(schedule.prog_num))
            combo_num_prog.setFont(font)

            table.setCellWidget(row, 0, btn_remove)
            table.setCellWidget(row, 1, time_edit)
            table.setCellWidget(row, 2, combo_num_prog)

    def _enable_mon_thu(self):
        for table in self.table_list[1:5]:
            table.setDisabled(self.check_box.isChecked())

        for btn in self.add_row_btn_list[1:5]:
            btn.setDisabled(self.check_box.isChecked())

    def _initialize_schedule_column(self, table_num: int):
        """
        This method initialize column that contain the elements (no values of the elements).

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

        # Set Table
        tbl = self._create_table()

        # Add Row Button
        btn_add = QPushButton("הוסף שורה")
        btn_add.clicked.connect(lambda: self.add_row_method(table_num))
        btn_add.setProperty("class", "add_row_button")
        self.add_row_btn_list.append(btn_add)

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addWidget(btn_add, alignment=Qt.AlignmentFlag.AlignHCenter)

        # set root
        wrap.setLayout(column_layout)

        return wrap, tbl


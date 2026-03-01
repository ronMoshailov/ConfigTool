from PyQt6.QtCore import Qt, QTime
from PyQt6.QtGui import QFont
from PyQt6.QtWidgets import QWidget, QPushButton, QCheckBox, QHBoxLayout, QVBoxLayout, QLabel, QTimeEdit, \
    QAbstractSpinBox, QComboBox, QAbstractItemView, QHeaderView, QTableWidget, QMessageBox

from Config.special import clear_widget_from_layout
from Config.style import schedule_panel_style


class ScheduleView(QWidget):
    def __init__(self):
        super().__init__()

        # Controller Methods
        self.fetch_all_channels_method  = None
        self.remove_row_method          = None
        self.add_row_method             = None
        # self.update_schedule_method     = None
        self.toggle_copy_sunday_method  = None
        self.set_new_cells_method       = None

        # QPushButton Update
        self.btn_add = QPushButton("עדכן")
        self.btn_add.setObjectName("update_button")
        self.btn_add.clicked.connect(lambda: self.update_schedule(self.check_box.isChecked(), self.table_list))

        # CheckBox
        self.check_box = QCheckBox("ראשון עד חמישי")
        self.check_box.setObjectName("check_box")
        self.check_box.clicked.connect(lambda: self._toggle_button())

        # Button Layout
        self.bottom_layout = QHBoxLayout()
        self.bottom_layout.addWidget(self.check_box, alignment=Qt.AlignmentFlag.AlignCenter)
        self.bottom_layout.addWidget(self.btn_add)

        # Button Widget
        bottom_widget = QWidget()
        bottom_widget.setLayout(self.bottom_layout)
        bottom_widget.setObjectName("bottom_widget")

        # Schedule Layout
        self.schedule_layout = QHBoxLayout()

        # Root Layout
        self.root_layout = QVBoxLayout()
        self.root_layout.addLayout(self.schedule_layout)
        # self.root_layout.addLayout(self.bottom_layout)
        self.root_layout.addWidget(bottom_widget)

        # Data
        self.table_list = []
        self.add_row_btn_list = []
        self.is_copy_sunday = True

        # Style
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setStyleSheet(schedule_panel_style)
        self.setObjectName("RootWidget")

        # Self
        self.setLayout(self.root_layout)
        self.hide()

    def show_view(self):
        # clear the table
        clear_widget_from_layout([self.schedule_layout])
        self.table_list.clear()
        self.add_row_btn_list.clear()

        # Build the tables
        for idx in range (0, 7):
            schedule_column, table = self._initialize_schedule_column(idx + 1)      # create schedule_column
            self.table_list.append(table)
            self._fill_table(idx+1, table)                                            # fill table with values
            self.schedule_layout.addWidget(schedule_column, stretch=1)              # add schedule_column to layout
        self._enable_mon_thu()
        self.show()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ============================== CRUD ============================== #
    def _create_table(self):
        """
        Create the table with no values

        :return: Table QTableWidget
        """
        tbl = QTableWidget(0, 3)                                                # Set 0 rows and 3 columns
        tbl.setObjectName("tbl")                                                # Set object name
        tbl.setColumnCount(3)                                                   # set 3 columns
        tbl.setRowCount(0)                                                      # reset rows
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)       # disable visual elements when clicked on table
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)                              #
        tbl.verticalHeader().setVisible(False)                                  # Remove the vertical headers
        tbl.setHorizontalHeaderLabels(["", "שעה", "מס' תוכנית"])                # Set headers
        tbl.setColumnWidth(0, 10)                                  # Set width
        tbl.setColumnWidth(1, 65)                                  # Set width
        tbl.horizontalHeader().setSectionResizeMode(2, QHeaderView.ResizeMode.Stretch)
        return tbl

    # ============================== Layout ============================== #
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
        btn_add.setObjectName("add_row_button")
        self.add_row_btn_list.append(btn_add)

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addWidget(btn_add, alignment=Qt.AlignmentFlag.AlignHCenter)

        # set root
        wrap.setLayout(column_layout)

        return wrap, tbl

    # ============================== Logic ============================== #
    def _fill_table(self,table_num, table):
        """
        This method fill the table with all the schedules of this day

        :param table_num: number of the table
        :param table: Table QTableWidget
        :return: None
        """
        schedule_list = self.fetch_all_channels_method(table_num)
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
            time_edit.wheelEvent = lambda event: None

            combo_num_prog = QComboBox()
            combo_num_prog.setObjectName("combo_num_prog")
            combo_num_prog.addItems([str(i) for i in range(1, 33)])
            combo_num_prog.setCurrentText(str(schedule.prog_num))
            combo_num_prog.setFont(font)
            combo_num_prog.wheelEvent = lambda event: None

            table.setCellWidget(row, 0, btn_remove)
            table.setCellWidget(row, 1, time_edit)
            table.setCellWidget(row, 2, combo_num_prog)

    def _toggle_button(self):
        self.toggle_copy_sunday_method()
        self._enable_mon_thu()

    def _enable_mon_thu(self):
        self.check_box.setChecked(self.is_copy_sunday)

        for table in self.table_list[1:5]:
            table.setDisabled(self.check_box.isChecked())

        for btn in self.add_row_btn_list[1:5]:
            btn.setDisabled(self.check_box.isChecked())

    def update_schedule(self, is_copy_sunday, table_list):
        """
        This method update the model with the new data
        """
        # check data
        if not self._check_time(table_list):
            return

        # save data
        self.is_copy_sunday     = is_copy_sunday
        data_list               = []

        for idx, table in enumerate(table_list):
            if 1 <= idx <= 4 and self.is_copy_sunday:
                for row_num in range(table_list[0].rowCount()):
                    # get time
                    time_edit = table_list[0].cellWidget(row_num, 1)
                    hours = time_edit.time().hour()
                    minutes = time_edit.time().minute()

                    # get number program
                    combo = table_list[0].cellWidget(row_num, 2)
                    num_prog = int(combo.currentText())
                    data_list.append((hours, minutes, num_prog))
                self.set_new_cells_method(idx, data_list)
                data_list.clear()
            else:
                for row_num in range(table_list[idx].rowCount()):
                    # get time
                    time_edit = table_list[idx].cellWidget(row_num, 1)
                    hours = time_edit.time().hour()
                    minutes = time_edit.time().minute()

                    # get number program
                    combo = table_list[idx].cellWidget(row_num, 2)
                    num_prog = int(combo.currentText())
                    data_list.append((hours, minutes, num_prog))
                self.set_new_cells_method(idx, data_list)
                data_list.clear()

        self.show_view()
        QMessageBox.information(self, "הודעה", "העדכון הצליח")

    def _check_time(self, table_list):
        """
        This method check if the logic of the time is valid
        """
        for idx, table in enumerate (table_list):      # for each table

            prev = None                     # reset
            current = None                  # reset
            row_count = table.rowCount()    # get row count

            for row in range(row_count):                    # for each row in table
                prev = current
                current = table.cellWidget(row, 1).time()

                # if first iteration or if last iteration
                if prev is None or current is None:
                    continue

                # compare
                if current <= prev:
                    QMessageBox.critical(self, "שגיאה", f"יש בעיה עם הזמנים בטבלה מספר {idx + 1}")
                    return False
        return True


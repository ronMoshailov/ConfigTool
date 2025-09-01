from PyQt6.QtCore import Qt, QTime
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QSizePolicy, QTableWidget, QLabel, QComboBox, \
    QAbstractItemView, QTimeEdit, QAbstractSpinBox, QHeaderView, QPushButton, QScrollArea

from controllers.data_controller import DataController
from entities.DoubleClickButton import DoubleClickButton

class SchedulePanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()     # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")

        self.scroll_layout = QHBoxLayout()

        # =============== self =============== #
        self.root_layout = QVBoxLayout()

        # =============== ****** =============== #
        self.btn_add = QPushButton("עדכן")

        # =============== style =============== #
        self.setObjectName("imagePanel")
        self.hide()

    # --------------- add methods --------------- #

    # --------------- update methods --------------- #
    def _update(self):
        if not self._check_time():
            return False
        self.data_controller.update_schedule(self.table_list)
        return True

    # --------------- remove methods --------------- #


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

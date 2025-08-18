from PyQt5.QtCore import Qt
from PyQt5.QtGui import QIcon
from PyQt5.QtWidgets import QSizePolicy, QComboBox, QApplication, QPushButton, QVBoxLayout, QHBoxLayout
from PyQt5.QtWidgets import QSpacerItem

from config.constants import ROW_SPACING, COLUMN_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT, MIN_GREEN_IMAGE_PATH
from config.special import create_window, set_blue_button_white_text_style, make_checkable, set_btn_disable, build_combo
from config.debug import displayAllMoves

from controllers.data_controller import DataController
from controllers.ui_controller import UIController

import sys


class MainWindow:
    def __init__(self):
        app = QApplication(sys.argv)
        window = create_window(app, "Config Tool")

        # =============== layouts =============== #
        main_layout = QHBoxLayout()
        buttons_layout = QVBoxLayout()

        # =============== controller =============== #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        # =============== rows =============== #
        row0 = QHBoxLayout()
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        row5 = QHBoxLayout()
        row6 = QHBoxLayout()
        row7 = QHBoxLayout()

        # =============== buttons =============== #
        btn_set_path    = QPushButton("הגדר נתיב"   )
        btn_new_node    = QPushButton("צומת חדש"    )
        btn_set_moves   = QPushButton("🚦 ניהול מופעים 🚦" )
        btn3            = QPushButton("הפעל סלייב"  )
        btn_set_min     = QPushButton("הגדר מינימום")
        btn5            = QPushButton("הפעל מאסטר"  )
        btn_matrix      = QPushButton("הגדר מטריצה" )
        btn7            = QPushButton("dx הפעל"     )
        btn_sk          = QPushButton("SK24 כרטיסי" )
        btn8            = QPushButton("הגדר פרמטרים")
        btn9            = QPushButton("הגדר מעברים" )
        btn10           = QPushButton("IO24 כרטיסי" )
        debug_print_btn = QPushButton("הדפס הכל"    )
        btn11           = QPushButton("-----------" )
        btn_detector    = QPushButton("גלאים"    )

        btn_set_path    .clicked.connect(lambda: self.data_controller.   initialize_app(disable_btn_list)   )
        btn_set_moves   .clicked.connect(lambda: self.ui_controller.     show_set_move_layout()             )
        btn_set_min     .clicked.connect(lambda: self.ui_controller.     show_min_green_layout()            )
        btn_matrix      .clicked.connect(lambda: self.ui_controller.     show_matrix_layout()               )
        btn_sk          .clicked.connect(lambda: self.ui_controller.     show_sk_layout()                   )
        debug_print_btn .clicked.connect(lambda:                         displayAllMoves()                  )

        # =============== combo =============== #
        combo_employee = QComboBox()
        # combo_authority = QComboBox()

        # =============== lists =============== #
        buttons_list                    = [btn_set_path, btn_new_node, btn_set_moves, btn3, btn_set_min, btn5, btn_matrix, btn7, btn_sk, btn9, btn10, btn8, debug_print_btn, btn11, btn_detector]
        disable_btn_list                = [btn_new_node, btn_set_moves, btn3, btn_set_min, btn5, btn_matrix, btn7, btn_sk, btn9, btn10, btn8, debug_print_btn, btn11, btn_detector]
        buttons_checkable_list          = [btn_new_node, btn3, btn5, btn7]
        self.rows_list                  = [row0, row1, row2, row3, row4, row5, row6, row7]
        employee_list                   = ["דוד", "אליה", "לנה", "לירוי", "רון", "סרגיי", "קטיה", "שחר"]
        # authority_list                   = ["תל אביב", "חיפה", "ירושלים", "נתיבי ישראל", "נתיבי איילון"]

        # =============== special methods =============== #
        self.build_row(row0, combo_employee, btn_set_path)  # set row
        self.build_row(row1, btn_new_node, btn_set_moves)   # set row
        self.build_row(row2, btn3, btn_set_min)             # set row
        self.build_row(row3, btn5, btn_matrix)              # set row
        self.build_row(row4, btn7, btn_sk)                  # set row
        self.build_row(row5, btn9, btn10)                   # set row
        self.build_row(row6, btn8, debug_print_btn)         # set row
        self.build_row(row7, btn11, btn_detector)           # set row
        self.add_rows_to_layout(buttons_layout)             # add the button layout

        set_btn_disable(disable_btn_list)                   # Disable buttons
        build_combo(combo_employee, employee_list)          # add employees to combo box
        # build_combo(combo_authority, authority_list)        # add employees to combo box
        make_checkable(buttons_checkable_list)              # make button checkable
        set_blue_button_white_text_style(buttons_list)      # set style on buttons

        # =============== add layouts =============== #
        main_layout.addWidget(self.ui_controller.get_set_move_layout(), 20)
        main_layout.addWidget(self.ui_controller.get_min_green_layout(), 20)
        main_layout.addWidget(self.ui_controller.get_matrix_layout(), 20)
        main_layout.addWidget(self.ui_controller.get_sk_layout(), 20)
        main_layout.addStretch(1)
        main_layout.addLayout(buttons_layout, 0)

        # =============== show window =============== #
        window.setLayout(main_layout)
        window.show()
        print("** main window was set successfully")

        sys.exit(app.exec_())

    def build_row(self, row, arg_btn1, arg_btn2):
        """
        This method set the row with all the arguments.

        :param row: row to set
        :param arg_btn1: first button in the row
        :param arg_btn2: second button in the row
        :return: None
        """
        row.addStretch()
        row.addWidget(arg_btn1)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(arg_btn2)

    def add_rows_to_layout(self, buttons_layout):
        """
        This method set all the rows to the layout.

        :param buttons_layout: layout that will contain all the buttons
        :return: None
        """
        for idx, row in enumerate(self.rows_list):
            buttons_layout.addLayout(row)
            buttons_layout.addSpacing(ROW_SPACING * 3 if idx == 0 else ROW_SPACING)
        buttons_layout.addSpacerItem(QSpacerItem(0, 0, QSizePolicy.Minimum, QSizePolicy.Expanding))


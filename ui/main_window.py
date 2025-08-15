from PyQt5.QtCore                   import Qt
from PyQt5.QtWidgets import QSizePolicy, QComboBox, QApplication, QWidget, QPushButton, QVBoxLayout, QHBoxLayout, \
    QSpacerItem, QLabel
from config.constants               import ROW_SPACING, COLUMN_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT
from config.style                   import combo_style, button_style
from controllers.data_controller    import DataController
from controllers.ui_controller      import UIController
from data.debug                     import displayAllMoves

import sys


class MainWindow:
    def __init__(self):
        app = QApplication(sys.argv)

        window = QWidget()
        window.setWindowTitle("ConfigTool")
        screen = app.primaryScreen().availableGeometry().center()
        print(f"screen.x(): {screen.x()}")
        window.setGeometry(screen.x(), screen.y() // 2, 800, 600)  # (X, Y, Width, Height)

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

        # =============== buttons =============== #
        btn_set_path    = QPushButton("הגדר נתיב"   )
        btn_new_node    = QPushButton("צומת חדש"    )
        btn_set_moves   = QPushButton("ניהול מופעים" )
        btn3            = QPushButton("הפעל סלייב"  )
        btn_set_min     = QPushButton("הגדר מינימום")
        btn5            = QPushButton("הפעל מאסטר"  )
        btn_matrix      = QPushButton("הגדר מטריצה" )
        btn7            = QPushButton("dx הפעל"     )
        btn8            = QPushButton("הגדר פרמטרים")
        btn9            = QPushButton("הגדר מעברים" )
        btn10           = QPushButton("-----------" )
        btn11           = QPushButton("הגדר מיפוי"  )
        debug_print_btn = QPushButton("הדפס הכל"    )

        btn_set_path    .clicked.connect(lambda: self.data_controller.   initialize(self.disable_btns)      )
        btn_set_moves   .clicked.connect(lambda: self.ui_controller.     show_set_move_layout()             )
        btn_set_min     .clicked.connect(lambda: self.ui_controller.     show_min_green_layout()            )
        btn_matrix      .clicked.connect(lambda: self.ui_controller.     show_matrix_layout()               )
        debug_print_btn .clicked.connect(lambda:                         displayAllMoves()                    )

        # =============== combo =============== #
        self.combo = QComboBox()

        # =============== lists =============== #
        self.buttons_list             = [btn_set_path, btn_new_node, btn_set_moves, btn3, btn_set_min, btn5, btn_matrix, btn7, btn8, btn9, btn10, btn11, debug_print_btn]
        self.disable_btns             = [btn_new_node, btn_set_moves, btn3, btn_set_min, btn5, btn_matrix, btn7, btn8, btn9, btn10, btn11, debug_print_btn]
        self.buttons_checkable_list   = [btn_new_node, btn3, btn5, btn7]
        self.rows_list                = [row0, row1, row2, row3, row4, row5, row6]

        # =============== special methods =============== #
        self.set_row(row0, self.combo, btn_set_path)    # set row
        self.set_row(row1, btn_new_node, btn_set_moves) # set row
        self.set_row(row2, btn3, btn_set_min)           # set row
        self.set_row(row3, btn5, btn_matrix)            # set row
        self.set_row(row4, btn7, btn8)                  # set row
        self.set_row(row5, btn9, btn10)                 # set row
        self.set_row(row6, btn11, debug_print_btn)      # set row
        self.add_rows_to_layout(buttons_layout)         # add the button layout
        self.set_btn_disable()                          # Disable buttons
        self.add_employees()                            # add employees to combo box
        self.make_checkable()                           # make the buttons checkable
        self.set_btn_style()                            # Set style to buttons

        main_layout.addWidget(self.ui_controller.get_set_move_layout())
        main_layout.addWidget(self.ui_controller.get_min_green_layout())
        main_layout.addWidget(self.ui_controller.get_matrix_layout())
        main_layout.addLayout(buttons_layout)

        # =============== show window =============== #
        window.setLayout(main_layout)
        window.show()
        print("** main window was set successfully")

        sys.exit(app.exec_())

    def set_btn_disable(self):
        """
        This method make all the buttons that need to disable at the beginning.

        :return:None
        """
        for btn in self.disable_btns:
            btn.setDisabled(True)

    def set_row(self, row, btn1, btn_set_phase):
        """
        This method set the row with all the arguments.

        :param row: row to set
        :param btn1: first button in the row
        :param btn_set_phase: second button in the row
        :return: None
        """
        btn1.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn_set_phase.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addStretch()
        row.addWidget(btn1)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn_set_phase)

    def add_rows_to_layout(self, buttons_layout):
        """
        This method set all the rows to the layout.

        :param buttons_layout: layout that will contain all the buttons
        :return: None
        """
        for idx, row in enumerate(self.rows_list):
            buttons_layout.addLayout(row)
            buttons_layout.addSpacing(ROW_SPACING)
            if idx == 0:
                buttons_layout.addSpacing(ROW_SPACING * 2)
        buttons_layout.addSpacerItem(QSpacerItem(0, 0, QSizePolicy.Minimum, QSizePolicy.Expanding))

    def add_employees(self):
        """
        This method add all the employees to the combo box
        :return:
        """
        self.combo.addItem("אליה")
        self.combo.addItem("דוד")
        self.combo.addItem("לנה")
        self.combo.addItem("לירוי")
        self.combo.addItem("רון")
        self.combo.addItem("סרגיי")
        self.combo.addItem("קטיה")
        self.combo.addItem("שחר")
        self.combo.setLayoutDirection(Qt.RightToLeft)
        self.combo.setStyleSheet(combo_style)

    def set_btn_style(self):
        """
        This method set the style to the buttons
        :return:
        """
        for btn in self.buttons_list:
            btn.setStyleSheet(button_style)

    def make_checkable(self):
        """
        This method make buttons checkable.

        :return: None
        """
        for btn in self.buttons_checkable_list:
            btn.setCheckable(True)

# ------------------------------------------------------------------------- #
# QHBoxLayout → Horizontal Box Layout
# QVBoxLayout → Vertical Box Layout
# QApplication → The main engine of the application, responsible for running the graphics loop
# QWidget → Base for every graphical element (window, box, area, etc.)
# QScrollArea → Allows you to display scrollable content when the content is larger than the available space.
# ------------------------------------------------------------------------- #


import math

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QGridLayout, QPushButton, QLineEdit, QVBoxLayout, QSizePolicy, QSpacerItem

from config.debug import displayAllMoves
from config.style import navigator_panel_style
from controllers.data_controller import DataController
from controllers.ui_controller import UIController


class NavigatorPanel(QWidget):
    """
    Navigator panel with command buttons and a log textbox.
    """

    def __init__(self):
        super().__init__()

        # =============== Controller =============== #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        # =============== QLineEdit =============== #
        # self.name_textbox = QLineEdit()
        # self.name_textbox.setPlaceholderText("שם")
        # self.name_textbox.setObjectName("name_textbox")

        self.log_textbox = QLineEdit()
        self.log_textbox.setEnabled(False)
        self.log_textbox.setReadOnly(True)
        self.log_textbox.setAlignment(Qt.AlignmentFlag.AlignLeft)
        self.log_textbox.setObjectName("log_textbox")

        # =============== QPushButton =============== #
        self.buttons_list = self._initialize_buttons()

        # =============== Grid Layout =============== #
        self.grid_layout = self._build_grid_layout()
        self.grid_layout.setContentsMargins(16, 16, 16, 16)
        self.grid_layout.setHorizontalSpacing(12)
        self.grid_layout.setVerticalSpacing(12)

        # =============== Grid QWidget =============== #
        self.grid_container = QWidget()
        self.grid_container.setLayout(self.grid_layout)
        self.grid_container.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.grid_container.setObjectName("btn_container")

        # =============== Root Layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.grid_container)
        root_layout.addWidget(self.log_textbox)

        # =============== Self =============== #
        self.setLayout(root_layout)

        # =============== Style =============== #
        self.setMaximumWidth(420)
        self.setMinimumWidth(300)
        self.setObjectName("root_panel")
        self.setStyleSheet(navigator_panel_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)

    def _initialize_buttons(self):
        # =============== create buttons =============== #
        new_node_btn = QPushButton("הגדרות")                    #  0
        new_node_btn.setObjectName("MainButton")
        set_paths_btn = QPushButton("הגדר נתיב")                #  1
        set_paths_btn.setObjectName("MainButton")
        active_slave_btn = QPushButton("-----------")           #  2
        display_move_panel_btn = QPushButton("מופעים")          #  3
        active_master_btn = QPushButton("-----------")          #  4
        display_min_green_panel_btn = QPushButton("מינימום")    #  5
        active_dx_btn = QPushButton("-----------")              #  6
        display_matrix_panel_btn = QPushButton("מטריצה")        #  7
        a_btn = QPushButton("-----------")                      #  8
        b_btn = QPushButton("SK24")                             #  9
        c_btn = QPushButton("-----------")                      # 10
        d_btn = QPushButton("IO24")                             # 11
        e_btn = QPushButton("-----------")                      # 12
        f_btn = QPushButton("גלאים")                            # 13
        g_btn = QPushButton("-----------")                      # 14
        h_btn = QPushButton("לו\"ז")                            # 15
        i_btn = QPushButton("-----------")                      # 16
        j_btn = QPushButton("תמונות")                           # 17
        k_btn = QPushButton("-----------")                      # 18
        l_btn = QPushButton("מעברים")                           # 19
        m_btn = QPushButton("-----------")                      # 20
        n_btn = QPushButton("-----------")                      # 21
        o_btn = QPushButton("-----------")                      # 22
        p_btn = QPushButton("-----------")                      # 23
        display_all_btn = QPushButton("הדפס הכל")               # 24
        r_btn = QPushButton("הגדר פרמטרים")                     # 25

        # =============== create button list =============== #
        buttons = [
            new_node_btn,                    #  0
            set_paths_btn,                   #  1
            active_slave_btn,                #  2
            display_move_panel_btn,          #  3
            active_master_btn,               #  4
            display_min_green_panel_btn,     #  5
            active_dx_btn,                   #  6
            display_matrix_panel_btn,        #  7
            a_btn,                           #  8
            b_btn,                           #  9
            c_btn,                           # 10
            d_btn,                           # 11
            e_btn,                           # 12
            f_btn,                           # 13
            g_btn,                           # 14
            h_btn,                           # 15
            i_btn,                           # 16
            j_btn,                           # 17
            k_btn,                           # 18
            l_btn,                           # 19
            m_btn,                           # 20
            n_btn,                           # 21
            o_btn,                           # 22
            p_btn,                           # 23
            display_all_btn,                 # 24
            r_btn                            # 25
        ]

        # =============== connect listener =============== #
        buttons[ 0].clicked.connect(lambda: self.ui_controller.show_settings_panel())
        buttons[ 1].clicked.connect(lambda: self.data_controller.initialize_app([buttons[0]] + buttons[2:]))
        buttons[ 3].clicked.connect(lambda: self.ui_controller.show_set_move_layout())
        buttons[ 5].clicked.connect(lambda: self.ui_controller.show_min_green_layout())
        buttons[ 7].clicked.connect(lambda: self.ui_controller.show_matrix_layout())
        buttons[ 9].clicked.connect(lambda: self.ui_controller.show_sk_layout())
        buttons[13].clicked.connect(lambda: self.ui_controller.show_detector_panel())
        buttons[15].clicked.connect(lambda: self.ui_controller.show_schedule_panel())
        buttons[17].clicked.connect(lambda: self.ui_controller.show_image_panel())
        buttons[19].clicked.connect(lambda: self.ui_controller.show_program_panel())
        buttons[24].clicked.connect(lambda: displayAllMoves())

        # =============== special methods =============== #
        self._set_btn_disable([buttons[0]] + buttons[2:])                         # Disable buttons

        # =============== set class =============== #
        for btn in buttons:
            btn.setProperty("class", "navigator_button")

        return buttons

    def _build_grid_layout(self):
        grid_layout = QGridLayout()

        # calc how many rows needed
        rows_num = math.ceil(len(self.buttons_list)/2)      # rows for buttons
        rows_num += 1                                       # row for 'textbox'

        # add 'textbox' to grid_layout
        # grid_layout.addWidget(self.name_textbox, 0, 0, 1, 2)      # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)

        # add buttons to grid_layout
        for i, btn in enumerate(self.buttons_list, start=0):
            r = i // 2
            c = i % 2
            grid_layout.addWidget(btn, r, c)

        spacer = QSpacerItem(0, 60, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Fixed)
        grid_layout.addItem(spacer, 0, 0, 1, 2)  # שם את זה בשורה "1" כדי שידחוף את השורה הבאה למטה

        # add space after the buttons
        grid_layout.setRowStretch(rows_num, 1)

        return grid_layout

    def _set_btn_disable(self, btn_list):
        """
        This method make all the buttons that need to disable at the beginning.

        :return:None
        """
        for btn in btn_list:
            btn.setDisabled(True)


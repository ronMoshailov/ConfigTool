from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QPushButton, QVBoxLayout

from config.debug import displayAllMoves
from config.special import set_btn_disable, set_property
from config.style import navigator_panel_style
from controllers.data_controller import DataController
from controllers.ui_controller import UIController


class NavigatorPanel(QWidget):
    def __init__(self):
        super().__init__()

        # =============== Controller =============== #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        # =============== QPushButton =============== #
        self.buttons_list = self._initialize_buttons()

        buttons_layout = QVBoxLayout()
        for idx, btn in enumerate (self.buttons_list):
            buttons_layout.addWidget(btn)
            if idx == 1:
                buttons_layout.addSpacing(20)
        buttons_layout.addStretch()

        # =============== Grid QWidget =============== #
        self.grid_container = QWidget()
        self.grid_container.setLayout(buttons_layout)
        self.grid_container.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.grid_container.setObjectName("btn_container")

        # =============== Root Layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.grid_container)

        # =============== Self =============== #
        self.setLayout(root_layout)

        # =============== Style =============== #
        self.setFixedWidth(300)
        self.setObjectName("root_panel")
        self.setStyleSheet(navigator_panel_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)

    def _initialize_buttons(self):
        # =============== create buttons =============== #
        set_paths_btn = QPushButton("👨🏻‍🦯‍➡️הגדר נתיב🧑🏻‍🦯")            #  0
        set_paths_btn.setObjectName("MainButton")
        settings_btn = QPushButton("⚙️ הגדרות ⚙️")                #  1
        settings_btn.setObjectName("MainButton")
        move_panel_btn = QPushButton("🚦 מופעים 🚦")              #  2
        min_green_panel_btn = QPushButton("🕰️ מינימום 🕰️")        #  3
        matrix_panel_btn = QPushButton("🔢 מטריצה 🔢")            #  4
        sk24_panel_btn = QPushButton("🧑‍💻   SK24   👩‍💻")                #  5
        io24_panel_btn = QPushButton("IO24")                #  6
        detector_panel_btn = QPushButton("👩🏻‍🔧גלאים👨🏻‍🔧")           #  7
        schedule_panel_btn = QPushButton("🕛   לו\"ז   🕛")           #  8
        image_panel_btn = QPushButton("🖼️ תמונות 🖼️")             #  9
        program_panel_btn = QPushButton(" תוכניות ")          # 10
        inter_stage_panel_btn = QPushButton("➡️ מעברים ➡️")       # 11
        display_all_btn = QPushButton("🖨️ הדפס הכל 🖨️")           # 12
        parameters_panel_btn = QPushButton("הגדר פרמטרים")  # 13

        # =============== create button list =============== #
        buttons = [
            set_paths_btn,           #  0
            settings_btn,            #  1
            move_panel_btn,          #  2
            min_green_panel_btn,     #  3
            matrix_panel_btn,        #  4
            sk24_panel_btn,          #  5
            io24_panel_btn,          #  6
            detector_panel_btn,      #  7
            schedule_panel_btn,      #  8
            image_panel_btn,         #  9
            program_panel_btn,       # 10
            inter_stage_panel_btn,   # 11
            display_all_btn,         # 12
            parameters_panel_btn     # 13
        ]

        # =============== connect listener =============== #
        buttons[ 0].clicked.connect(lambda: self.data_controller.initialize_app(buttons[1:]))
        buttons[ 1].clicked.connect(lambda: self.ui_controller.show_panel_by_name("settings"))
        buttons[ 2].clicked.connect(lambda: self.ui_controller.show_panel_by_name("set_move"))
        buttons[ 3].clicked.connect(lambda: self.ui_controller.show_panel_by_name("min_green"))
        buttons[ 4].clicked.connect(lambda: self.ui_controller.show_panel_by_name("matrix"))
        buttons[ 5].clicked.connect(lambda: self.ui_controller.show_panel_by_name("sk"))
        # buttons[ 6].clicked.connect(lambda: self.ui_controller.show_panel_by_name("io"))
        buttons[ 7].clicked.connect(lambda: self.ui_controller.show_panel_by_name("detector"))
        buttons[ 8].clicked.connect(lambda: self.ui_controller.show_panel_by_name("schedule"))
        buttons[ 9].clicked.connect(lambda: self.ui_controller.show_panel_by_name("image"))
        buttons[10].clicked.connect(lambda: self.ui_controller.show_panel_by_name("program"))
        buttons[11].clicked.connect(lambda: self.ui_controller.show_panel_by_name("inter_stage"))
        buttons[12].clicked.connect(lambda: displayAllMoves())
        # buttons[13].clicked.connect(lambda: self.ui_controller.show_panel_by_name("parameters"))

        # =============== special methods =============== #
        set_btn_disable(buttons[1:])                         # Disable buttons
        set_property("class", "navigator_button", buttons)

        return buttons

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QPushButton, QVBoxLayout

from Config.special import set_property
from Config.style import navigator_panel_style


class NavigatorView(QWidget):
    def __init__(self, show_view_method, print_all_method):
        super().__init__()
        self.show_view_method = show_view_method
        self.print_all_method = print_all_method
        self.write_to_code_method = None

    #     # =============== QPushButton =============== #
        self.buttons_list = self._initialize_buttons()
    #
        buttons_layout = QVBoxLayout()
        save_btn = QPushButton("💾 עדכן קוד 💾")  # 13
        save_btn.setProperty("class", "navigator_button")
        save_btn.clicked.connect(lambda: self.write_to_code_method())

        for idx, btn in enumerate (self.buttons_list):
            buttons_layout.addWidget(btn)
            if idx == 1:
                buttons_layout.addSpacing(20)
        buttons_layout.addStretch()

        buttons_layout.addWidget(save_btn)
    #
    #     # =============== Grid QWidget =============== #
        self.container = QWidget()
        self.container.setLayout(buttons_layout)
        self.container.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.container.setObjectName("btn_container")
    #
    #     # =============== Root Layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.container)
    #
    #     # =============== Self =============== #
        self.setLayout(root_layout)
    #
    #     # =============== Style =============== #
        self.setFixedWidth(300)
        self.setObjectName("root_panel")
        self.setStyleSheet(navigator_panel_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
    #
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
        detector_panel_btn = QPushButton("👩🏻‍🔧  גלאים  👨🏻‍🔧")           #  7
        schedule_panel_btn = QPushButton("🕛    לו\"ז    🕛")           #  8
        image_panel_btn = QPushButton("🖼️ תמונות 🖼️")             #  9
        # program_panel_btn = QPushButton(" תוכניות ")          # 10
        inter_stage_panel_btn = QPushButton("➡️ מעברים ➡️")       # 11
        display_all_btn = QPushButton("🖨️ הדפס הכל 🖨️")           # 12
        parameters_panel_btn = QPushButton("📐פרמטרים📐")  # 13

    #
    #     # =============== create button list =============== #
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
            inter_stage_panel_btn,   # 10
            parameters_panel_btn,    # 11
            display_all_btn,         # 12
        ]
    #
    #     # =============== connect listener =============== #
        buttons[ 0].clicked.connect(lambda: self.show_view_method("init"))
        buttons[ 1].clicked.connect(lambda: self.show_view_method("settings"))
        buttons[ 2].clicked.connect(lambda: self.show_view_method("move"))
        buttons[ 3].clicked.connect(lambda: self.show_view_method("min_green"))
        buttons[ 4].clicked.connect(lambda: self.show_view_method("matrix"))
        buttons[ 5].clicked.connect(lambda: self.show_view_method("sk"))
    #     # buttons[ 6].clicked.connect(lambda: self.ui_controller.show_panel_by_name("io"))
        buttons[ 7].clicked.connect(lambda: self.show_view_method("detector"))
        buttons[ 8].clicked.connect(lambda: self.show_view_method("schedule"))
        buttons[ 9].clicked.connect(lambda: self.show_view_method("image"))
        buttons[10].clicked.connect(lambda: self.show_view_method("phue"))
        buttons[11].clicked.connect(lambda: self.show_view_method("parameters_ta"))
        buttons[12].clicked.connect(lambda: self.print_all_method())
    #
    #     # =============== special methods =============== #
    #     set_btn_disable(buttons[1:])                         # Disable buttons
        set_property("class", "navigator_button", buttons)
    #
        return buttons

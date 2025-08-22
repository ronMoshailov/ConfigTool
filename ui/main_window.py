from PyQt6.QtWidgets import QPushButton, QHBoxLayout, QMainWindow, QLineEdit, QWidget, QGridLayout, QScrollArea, \
    QSpacerItem, QSizePolicy

from config.special import set_blue_button_white_text_style, make_checkable, set_btn_disable
from config.debug import displayAllMoves

from controllers.data_controller import DataController
from controllers.ui_controller import UIController

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Config Tool")

        # --- controllers --- #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        # --- style --- #
        btn_container_style = """ 
        /* text box */
        QLineEdit{
            background-color: white; 
            border-radius: 10px; 
            min-height: 30px; 
            font-weight: bold; 
            font-size: 18px;
        }
        """
        root_style = """
            background-color: #D4D6FF;
        """

        # =============== widgets =============== #
        root = QWidget()

        set_move_panel = self.ui_controller.get_set_move_layout()

        min_green_panel = self.ui_controller.get_min_green_layout()

        matrix_panel = self.ui_controller.get_matrix_layout()

        sk_panel = self.ui_controller.get_sk_layout()

        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        scroll.setStyleSheet("background-color: #F2F2FF; border-radius: 10px;")
        scroll.setFixedWidth(400)

        name_edit = QLineEdit()
        name_edit.setPlaceholderText("שם")

        btn_container = QWidget()
        btn_container.setStyleSheet(btn_container_style)

        buttons = [
            QPushButton("צומת חדש"), QPushButton("הגדר נתיב"),
            QPushButton("הפעל סלייב"), QPushButton("🚦ניהול מופעים🚦"),
            QPushButton("הפעל מאסטר"), QPushButton("הגדר מינימום"),
            QPushButton("הגדר פרמטרים"), QPushButton("הגדר מטריצה"),
            QPushButton("הפעל סלייב"), QPushButton("SK24 כרטיסי"),
            QPushButton("dx הפעל"), QPushButton("IO24 כרטיסי"),
            QPushButton("הגדר מעברים"), QPushButton("הדפס הכל"),
        ]
        # buttons[0]       checkAble
        buttons[1].clicked.connect(lambda: self.data_controller.initialize_app([buttons[0]] + buttons[2:]))
        # buttons[2]       checkAble
        buttons[3].clicked.connect(lambda: self.ui_controller.show_set_move_layout())
        # buttons[4]       checkAble
        buttons[5].clicked.connect(lambda: self.ui_controller.show_min_green_layout())
        # buttons[6]       TODO
        buttons[7].clicked.connect(lambda: self.ui_controller.show_matrix_layout())
        # buttons[8]       checkAble
        buttons[9].clicked.connect(lambda: self.ui_controller.show_sk_layout())
        # buttons[10]      checkAble
        # buttons[11]      TODO
        # buttons[12]      TODO
        buttons[13].clicked.connect(lambda: displayAllMoves())

        # =============== layouts =============== #
        root_layout     = QHBoxLayout()

        btn_grid_layout = QGridLayout()
        btn_grid_layout.setContentsMargins(16, 16, 16, 16)
        btn_grid_layout.setHorizontalSpacing(12)
        btn_grid_layout.setVerticalSpacing(12)
        rows = (len(buttons) + 1) // 2 + 1  # +1 בגלל השורה של name_edit
        btn_grid_layout.setRowStretch(rows, 1)  # “רווח גמיש” אחרי השורה האחרונה

        # =============== widgets to layout =============== #
        root_layout.addSpacerItem(QSpacerItem(40, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum))
        root_layout.addWidget(set_move_panel    , 20)
        root_layout.addWidget(min_green_panel   , 20)
        root_layout.addWidget(matrix_panel      , 20)
        root_layout.addWidget(sk_panel          , 20)
        root_layout.addWidget(scroll)
        root.setLayout(root_layout)

        scroll.setWidget(btn_container)

        btn_container.setLayout(btn_grid_layout)

        btn_grid_layout.addWidget(name_edit, 0, 0, 1, 2) # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
        for i, btn in enumerate(buttons, start=1):
            r = (i - 1) // 2 + 1
            c = (i - 1) % 2
            btn_grid_layout.addWidget(btn, r, c)

        # =============== root =============== #
        self.setCentralWidget(root)  # --- central root ---
        self.setStyleSheet(root_style)

        # =============== special methods =============== #
        set_btn_disable([buttons[0]] + buttons[2:])                                     # Disable buttons
        make_checkable([buttons[0], buttons[2], buttons[4], buttons[8], buttons[10]])   # make button checkable
        set_blue_button_white_text_style(buttons)                                       # set style on buttons

        # =============== show window =============== #
        print("** main window was set successfully")

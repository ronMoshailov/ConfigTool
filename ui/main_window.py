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

        self.data_controller = DataController()
        self.ui_controller = UIController()

        # --- central root --- #
        root = QWidget()
        self.setCentralWidget(root)
        root_layout = QHBoxLayout(root)

        # --- right panel with grid (scrollable) ---
        btn_container = QWidget(root)
        btn_grid = QGridLayout(btn_container)

        # components
        name_edit = QLineEdit()
        name_edit.setPlaceholderText("שם")
        name_edit.setStyleSheet("background-color: white; border-radius: 10px; min-height: 30px; font-weight: bold; font-size: 18px;")

        buttons = [ QPushButton("צומת חדש")     ,  QPushButton("הגדר נתיב"),
                    QPushButton("הפעל סלייב")   ,  QPushButton("🚦ניהול מופעים🚦"),
                    QPushButton("הפעל מאסטר")   ,  QPushButton("הגדר מינימום"),
                    QPushButton("הגדר פרמטרים") ,  QPushButton("הגדר מטריצה"),
                    QPushButton("הפעל סלייב")   ,  QPushButton("SK24 כרטיסי"),
                    QPushButton("dx הפעל")      ,  QPushButton("IO24 כרטיסי"),
                    QPushButton("הגדר מעברים")  ,  QPushButton("הדפס הכל"),
        ]

      # buttons[0]       checkAble
        buttons[1]      .clicked.connect(lambda: self.data_controller.initialize_app([buttons[0]] + buttons[2:]))
      # buttons[2]       checkAble
        buttons[3]      .clicked.connect(lambda: self.ui_controller.show_set_move_layout())
      # buttons[4]       checkAble
        buttons[5]      .clicked.connect(lambda: self.ui_controller.show_min_green_layout())
      # buttons[6]       TODO
        buttons[7]      .clicked.connect(lambda: self.ui_controller.show_matrix_layout())
      # buttons[8]       checkAble
        buttons[9]      .clicked.connect(lambda: self.ui_controller.show_sk_layout())
      # buttons[10]      checkAble
      # buttons[11]      TODO
      # buttons[12]      TODO
        buttons[13]     .clicked.connect(lambda: displayAllMoves())

        # create grid
        btn_grid.addWidget(name_edit, 0, 0, 1, 2) # row_num, col_num, how many rows use, how many columns to use
        for i, btn in enumerate(buttons, start=1):
            r = (i - 1) // 2 + 1
            c = (i - 1) % 2
            btn_grid.addWidget(btn, r, c)

        # גלילה אם יש המון כפתורים
        scroll = QScrollArea(root)

        scroll.setWidget(btn_container)
        scroll.setStyleSheet("background-color: #F2F2FF; border-radius: 10px;")
        scroll.setFixedWidth(400)  # תבחר רוחב שמתאים לכל הטקסט בכפתורים

        # צרף ללייאאוט הראשי
        root_layout.addWidget(scroll, 0)  # עמודת כפתורים קבועה יחסית

        # =============== add layouts =============== #
        root_layout.addWidget(self.ui_controller.get_set_move_layout(), 20)
        root_layout.addWidget(self.ui_controller.get_min_green_layout(), 20)
        root_layout.addWidget(self.ui_controller.get_matrix_layout(), 20)
        root_layout.addWidget(self.ui_controller.get_sk_layout(), 20)
        root_layout.addSpacerItem(QSpacerItem(40, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum))
        root_layout.addWidget(scroll)

        btn_grid.setContentsMargins(16, 16, 16, 16)
        btn_grid.setHorizontalSpacing(12)
        btn_grid.setVerticalSpacing(12)

        rows = (len(buttons) + 1) // 2 + 1  # +1 בגלל השורה של name_edit
        btn_grid.setRowStretch(rows, 1)  # “רווח גמיש” אחרי השורה האחרונה
        scroll.setWidgetResizable(True)

        set_btn_disable([buttons[0]] + buttons[2:])                                     # Disable buttons
        make_checkable([buttons[0], buttons[2], buttons[4], buttons[8], buttons[10]])   # make button checkable
        set_blue_button_white_text_style(buttons)                                       # set style on buttons

        # =============== show window =============== #
        self.setStyleSheet("background-color: #D4D6FF;")
        print("** main window was set successfully")


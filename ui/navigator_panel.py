from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QGridLayout, QPushButton, QLineEdit, QVBoxLayout

from config.debug import displayAllMoves
from config.special import make_checkable, set_btn_disable
from controllers.data_controller import DataController
from controllers.ui_controller import UIController


class NavigatorPanel(QWidget):
    """
    Navigator panel with command buttons and a log textbox.
    """

    def __init__(self):
        """
            Initialize the main window, create controllers, panels, and layout.
        """
        super().__init__()

        # --- style --- #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        root_style = """
            /* ********************************* root ********************************* */
            #RootPanel {
                background: qlineargradient(x1:0, y1:0, x2:1, y2:1, stop:0 #7ea1ff, stop:1 #d6c4fd);
                border: 1px solid #793ff8;
                border-radius: 20px;
            }
            
            /* ********************************* Button container ********************************* */
            QWidget#BtnContainer {
                background: qlineargradient(x1:0, y1:0, x2:1, y2:1, stop:0 #7ea1ff, stop:1 #b069f1);
                border-radius: 20px;
            }

            /* ********************************* QLineEdit ********************************* */
            QLineEdit{
                background-color: white; 
                border-radius: 10px; 
                min-height: 30px; 
                font-weight: bold; 
                font-size: 18px;
            }
        
            /* ********************************* QPushButton ********************************* */
            QPushButton {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
                color: white;
                border: 2px solid #2471a3;
                border-radius: 10px;
                padding: 0px;
                font-weight: bold;
                font-size: 18px;
                min-width: 150px;
                min-height: 36px;
            }

            QPushButton:hover {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
                border: 2px solid #1b4f72;
            }
    
            QPushButton:checked {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #58d68d, stop:1 #28b463);
                border: 2px solid #239b56;
            }

            QPushButton:disabled {
                background-color: #d5d8dc;
                border: 2px solid #a6acaf;
                color: #7f8c8d;
            }
        """

        # =============== QLineEdit =============== #
        name_edit = QLineEdit()
        name_edit.setPlaceholderText("שם")

        # =============== buttons =============== #
        buttons = self._init_buttons()

        # =============== textbox =============== #
        self.log_textbox = QLineEdit()
        self.log_textbox.setEnabled(False)
        self.log_textbox.setReadOnly(True)
        self.log_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)

        # =============== grid =============== #
        # widget
        grid_container = QWidget()
        grid_container.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        grid_container.setObjectName("BtnContainer")

        # layout
        grid_layout = QGridLayout()
        grid_layout.setContentsMargins(16, 16, 16, 16)
        grid_layout.setHorizontalSpacing(12)
        grid_layout.setVerticalSpacing(12)

        # create the grid layout
        rows = (len(buttons) + 1) // 2 + 1              # +1 before the 'name_edit'
        grid_layout.addWidget(name_edit, 0, 0, 1, 2)    # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
        grid_layout.setRowStretch(rows, 1)       # add space after the buttons

        # add buttons to the grid
        for i, btn in enumerate(buttons, start=1):
            r = (i - 1) // 2 + 1
            c = (i - 1) % 2
            grid_layout.addWidget(btn, r, c)

        # add the layout to widget
        grid_container.setLayout(grid_layout)

        # =============== root layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addWidget(grid_container)
        root_layout.addWidget(self.log_textbox)

        # =============== root =============== #
        self.setLayout(root_layout)
        self.setMaximumWidth(420)
        self.setMinimumWidth(300)
        self.setObjectName("RootPanel")
        self.setStyleSheet(root_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)


    def _init_buttons(self):
        buttons = [
            QPushButton("צומת חדש"), QPushButton("הגדר נתיב"),
            QPushButton("הפעל סלייב"), QPushButton("🚦ניהול מופעים🚦"),
            QPushButton("הפעל מאסטר"), QPushButton("הגדר מינימום"),
            QPushButton("הגדר פרמטרים"), QPushButton("הגדר מטריצה"),
            QPushButton("-----------"), QPushButton("SK24 כרטיסי"),
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

        # =============== special methods =============== #
        set_btn_disable([buttons[0]] + buttons[2:])  # Disable buttons
        make_checkable([buttons[0], buttons[2], buttons[4], buttons[8], buttons[10]])  # make button checkable

        return buttons


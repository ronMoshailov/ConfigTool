from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QSizePolicy, QComboBox, QFileDialog
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QVBoxLayout, QHBoxLayout, QSpacerItem

# configure imports
from config.constants import ROW_SPACING, COLUMN_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT
from config.style import combo_style, button_style
from controllers.main_controller import MainController

# imports
import sys

from ui.set_phase_panel import SetPhaseLayout


class MainWindow:
    def __init__(self):
        app = QApplication(sys.argv)

        window = QWidget()
        window.setWindowTitle("ConfigTool")
        window.setGeometry(1300, 300, 800, 600)  # (X, Y, Width, Height)

        # =============== layouts =============== #
        main_layout = QHBoxLayout()
        buttons_layout = QVBoxLayout()

        # =============== controller =============== #
        self.main_controller = MainController()

        # =============== rows =============== #
        row0 = QHBoxLayout()
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        row5 = QHBoxLayout()
        row6 = QHBoxLayout()

        self.rows_list = [row0, row1, row2, row3, row4, row5]

        # =============== combo =============== #
        self.combo = QComboBox()

        # =============== buttons =============== #
        btn_set_path = QPushButton("הגדר נתיב")
        btn1 = QPushButton("צומת חדש")
        btn_set_phase = QPushButton("הגדר מופעים")
        btn3 = QPushButton("הפעל סלייב")
        btn4 = QPushButton("הגדר מינימום")
        btn5 = QPushButton("הפעל מאסטר")
        btn6 = QPushButton("הגדר מטריצה")
        btn7 = QPushButton("dx הפעל")
        btn8 = QPushButton("הגדר פרמטרים")
        btn9 = QPushButton("הגדר מעברים")
        btn10 = QPushButton("-----------")
        btn11 = QPushButton("הגדר מיפוי")
        btn12 = QPushButton("--------------------------------------------")

        self.buttons_list = [btn_set_path, btn1, btn_set_phase, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12]
        self.disable_btns = [btn1, btn_set_phase, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12]
        self.buttons_checkable_list = [btn1, btn3, btn5, btn7]

        # =============== button → add actions =============== #
        btn_set_path.clicked.connect(lambda: self.main_controller.initialize(self.disable_btns))
        btn_set_phase.clicked.connect(self.main_controller.show_phase_panel)

        # =============== set rows =============== #
        self.set_row(row0, self.combo , btn_set_path )
        self.set_row(row1, btn1 , btn_set_phase )
        self.set_row(row2, btn3 , btn4 )
        self.set_row(row3, btn5 , btn6 )
        self.set_row(row4, btn7 , btn8 )
        self.set_row(row5, btn9 , btn10)
        self.set_row(row6, btn11, btn12)

        # =============== special methods =============== #
        self.set_buttons_layout(buttons_layout)                         # set the button layout
        self.set_buttons_status(self.disable_btns, True)      # Disable buttons
        self.add_employees()                                            # add employees to combo box
        self.make_checkable()                                           # make the buttons checkable
        self.set_btn_style()                                            # Set style to buttons

        main_layout.addWidget(self.main_controller.get_phase_layout())
        main_layout.addLayout(buttons_layout)

        # =============== show window =============== #
        window.setLayout(main_layout)
        window.show()
        sys.exit(app.exec_())


        # ------------------------------------------------------------------------- #
        # QHBoxLayout → Horizontal Box Layout
        # QVBoxLayout → Vertical Box Layout
        # QApplication – The main engine of the application, responsible for running the graphics loop
        # QWidget - Base for every graphical element (window, box, area, etc.)
        # ------------------------------------------------------------------------- #

    def set_buttons_status(self, btn_list, is_disabled):
        for btn in btn_list:
            btn.setDisabled(is_disabled)

    def set_row(self, row, btn1, btn_set_phase):
        btn1.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn_set_phase.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addStretch()
        row.addWidget(btn1)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn_set_phase)

    def set_buttons_layout(self, main_layout):
        for idx, row in enumerate(self.rows_list):
            main_layout.addLayout(row)
            main_layout.addSpacing(ROW_SPACING)
            if idx == 0:
                main_layout.addSpacing(ROW_SPACING * 2)
        main_layout.addSpacerItem(QSpacerItem(0, 0, QSizePolicy.Minimum, QSizePolicy.Expanding))

    def set_main_layout(self, main_layout, layouts):
        for layout in layouts:
            main_layout.addLayout(layout)


    def add_employees(self):
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
        for btn in self.buttons_list:
            btn.setStyleSheet(button_style)

    def make_checkable(self):
        for btn in self.buttons_checkable_list:
            btn.setCheckable(True)

    def open_folder_dialog(self):
        self.folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if self.folder_path:
            print("Selected path:", self.folder_path)


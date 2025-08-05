from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QPushButton, QVBoxLayout, QHBoxLayout, QSpacerItem, \
    QSizePolicy, QFrame, QComboBox
import sys

from ConfigTool.config.constants import ROW_SPACING, COLUMN_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT

class MainWindow:
    def __init__(self):
        app = QApplication(sys.argv)

        window = QWidget()
        window.setWindowTitle("ConfigTool")
        window.setGeometry(300, 200, 300, 600)  # (X, Y, Width, Height)

        # =============== layouts =============== #
        main_layout = QHBoxLayout()
        config_layout = QVBoxLayout()
        buttons_layout = QVBoxLayout()

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
        btn0 = QPushButton("------")
        btn1 = QPushButton("צומת חדש")
        btn2 = QPushButton("הגדר מופעים")
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

        self.buttons_list = [btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12]
        self.buttons_checkable_list = [btn1, btn3, btn5, btn7]
        self.make_checkable()

        # =============== set rows =============== #
        self.set_row(row0, self.combo , btn0 )
        self.set_row(row1, btn1 , btn2 )
        self.set_row(row2, btn3 , btn4 )
        self.set_row(row3, btn5 , btn6 )
        self.set_row(row4, btn7 , btn8 )
        self.set_row(row5, btn9 , btn10)
        self.set_row(row6, btn11, btn12)

        # =============== special methods =============== #
        self.set_buttons_layout(buttons_layout)
        self.start_up(btn4, btn6, btn8, btn9)
        self.set_btn_style()
        self.add_employees()

        # main_layout.addWidget(config_widget)
        # main_layout.addWidget(line)
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

    def start_up(self, btn4, btn6, btn8, btn9):
        btn4.setDisabled(True)
        btn6.setDisabled(True)
        btn8.setDisabled(True)
        btn9.setDisabled(True)

    def set_row(self, row, btn1, btn2):
        btn1.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn2.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addStretch()
        row.addWidget(btn1)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn2)

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

        self.combo.setLayoutDirection(Qt.RightToLeft)  # טקסט יופיע מימין לשמאל

        self.combo.setStyleSheet("""
            QComboBox {
                qproperty-alignment: 'AlignRight | AlignVCenter';  /* ליישר טקסט */
                background-color: #ecf0f1;
                border: 2px solid #3498db;
                border-radius: 8px;
                padding: 6px 12px;
                font-size: 14px;
                font-weight: bold;
                color: #2c3e50;
            }
            QComboBox:hover {
                border: 2px solid #2980b9;
            }
            QComboBox::drop-down {
                subcontrol-origin: padding;
                subcontrol-position: top left;
                width: 25px;
                border-right: 1px solid #3498db;
            }
            QComboBox::down-arrow {
                image: url(:/qt-project.org/styles/commonstyle/images/arrowdown-16.png);
                width: 12px;
                height: 12px;
            }
            QComboBox QAbstractItemView {
                background-color: #ffffff;
                selection-background-color: #3498db;
                selection-color: white;
                border: 1px solid #2980b9;
            }
        """)

    def set_btn_style(self):
        for btn in self.buttons_list:
            btn.setStyleSheet("""
                QPushButton {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #5dade2, stop:1 #2e86c1);
                    color: white;
                    border: 2px solid #2471a3;
                    border-radius: 10px;
                    padding: 10px;
                    font-weight: bold;
                    font-size: 14px;
                }

                QPushButton:hover {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #5499c7, stop:1 #21618c);
                    border: 2px solid #1b4f72;
                }

                QPushButton:checked {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #58d68d, stop:1 #28b463);
                    border: 2px solid #239b56;
                }

                QPushButton:disabled {
                    background-color: #d5d8dc;
                    border: 2px solid #a6acaf;
                    color: #7f8c8d;
                }
            """)

    def make_checkable(self):
        for btn in self.buttons_checkable_list:
            btn.setCheckable(True)

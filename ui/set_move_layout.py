import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea, \
    QCheckBox

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'classes')))


class SetMoveLayout(QWidget):
    def __init__(self):
        super().__init__()

        # =============== labels =============== #
        main_phase_label        = QLabel("מופע ראשי"    )
        not_main_phase_label    = QLabel("לא מופע ראשי" )
        d_detectors_label       = QLabel("גלאי דרישה"   )
        e_detectors_label       = QLabel("גלאי הארכה"   )
        inter_stage_label       = QLabel("מעברים"       )

        # =============== textbox =============== #
        main_phase_textbox      = QLineEdit()
        not_main_phase_textbox  = QLineEdit()
        d_detectors_textbox     = QLineEdit()
        e_detectors_textbox     = QLineEdit()
        inter_stage_textbox     = QLineEdit()

        # =============== plus buttons =============== #
        main_phase_btn          = QPushButton("➕")
        not_main_phase_btn      = QPushButton("➕")
        d_detectors_btn         = QPushButton("➕")
        e_detectors_btn         = QPushButton("➕")
        inter_stage_btn         = QPushButton("➕")

        # =============== active buttons =============== #
        main_phase_btn.clicked.connect(lambda: self.add_move(main_phase_label.text(), main_phase_textbox.text()))
        not_main_phase_btn.clicked.connect(lambda: self.add_move(not_main_phase_label.text(), not_main_phase_textbox.text()))
        # d_detectors_btn         = QPushButton("➕")
        # e_detectors_btn         = QPushButton("➕")
        # inter_stage_btn         = QPushButton("➕")

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        row5 = QHBoxLayout()

        # =============== rows =============== #
        self.set_row(row1  , main_phase_label        , main_phase_textbox        , main_phase_btn      )
        self.set_row(row2  , not_main_phase_label    , not_main_phase_textbox    , not_main_phase_btn  )
        self.set_row(row3  , d_detectors_label       , d_detectors_textbox       , d_detectors_btn     )
        self.set_row(row4  , e_detectors_label       , e_detectors_textbox       , e_detectors_btn     )
        self.set_row(row5  , inter_stage_label       , inter_stage_textbox       , inter_stage_btn     )

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()                            # create the container of the scroll bar. (get only widget)
        self.scroll_content = QWidget()                             # create the widget that will be in the layout.
        self.scroll_layout = QVBoxLayout()

        self.scroll_area.setWidgetResizable(True)

        self.scroll_content.setLayout(self.scroll_layout)
        self.scroll_area.setWidget(self.scroll_content)

        #
        self.phase_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()]
        self.scroll_layout.addLayout(self.phase_rows[0])
        self.scroll_layout.addLayout(self.phase_rows[1])
        self.scroll_layout.addLayout(self.phase_rows[2])

        # =============== layout =============== #
        main_layout              = QVBoxLayout()

        # =============== create the layout =============== #
        main_layout.addLayout(row1)
        main_layout.addLayout(row2)
        main_layout.addLayout(row3)
        main_layout.addLayout(row4)
        main_layout.addLayout(row5)
        main_layout.addStretch()
        main_layout.addWidget(self.scroll_area)

        self.setLayout(main_layout)
        self.hide()

        # =============== scroll rows =============== #

    def set_row(self, row, label, textbox, btn):
        label.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        textbox.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addWidget(label)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(textbox)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn)
        row.addSpacing(COLUMN_SPACING)
        row.addStretch()

    def show_panel(self, moves_list):
        print("show_panel")
        self.display_moves(moves_list)
        self.show()

    def display_moves(self, moves_list):
        # נקה את השורות הקיימות
        for row in self.phase_rows:
            while row.count():
                item = row.takeAt(0)        # get the first QLayoutItem of the layout
                widget = item.widget()      # get the widget
                if widget:                  #
                    widget.deleteLater()

        for move in moves_list:
            phase = move.name
            move_type = move.type

            # קבע את האינדקס של השורה לפי ה-type
            if move_type in ["Traffic", "Traffic_Flashing"]:
                row_idx = 0
            elif move_type == "Pedestrian":
                row_idx = 1
            elif move_type in ["Blinker", "Blinker_Conditional"]:
                row_idx = 2
            else:
                continue  # אם ה-type לא תואם – דלג

            label = QLabel(phase)
            label.setFixedHeight(30)

            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(30, 30)
            btn_remove.clicked.connect(lambda _, p=phase: self.remove_move(p))

            item_layout = QVBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            container = QFrame()
            container.setLayout(item_layout)
            container.setStyleSheet("background-color: #f0f0f0; border-radius: 5px; padding: 5px;")

            self.phase_rows[row_idx].addWidget(container)
        self.phase_rows[0].addStretch()
        self.phase_rows[1].addStretch()
        self.phase_rows[2].addStretch()

    def remove_move(self, move_name):
        self.main_controller.remove_move(move_name)
        self.main_controller.show_phase_panel()

    def add_move(self, label, value):
        if label == "מופע ראשי":
            self.main_controller.add_move(value, True)
        elif label == "לא מופע ראשי":
            self.main_controller.add_move(value, False)
        self.main_controller.show_phase_panel()
        print("not built yet")
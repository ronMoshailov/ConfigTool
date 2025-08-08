import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'classes')))


class PhasePanelWindow(QWidget):
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

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        row5 = QHBoxLayout()
        row6 = QHBoxLayout()

        # =============== rows =============== #
        self.set_row(row1  , main_phase_label        , main_phase_textbox        , main_phase_btn      )
        self.set_row(row2  , not_main_phase_label    , not_main_phase_textbox    , not_main_phase_btn  )
        self.set_row(row3  , d_detectors_label       , d_detectors_textbox       , d_detectors_btn     )
        self.set_row(row4  , e_detectors_label       , e_detectors_textbox       , e_detectors_btn     )
        self.set_row(row5  , inter_stage_label       , inter_stage_textbox       , inter_stage_btn     )

        # =============== layout =============== #
        layout              = QVBoxLayout()
        self.moves_layout   = QVBoxLayout()

        layout.addLayout(row1)
        layout.addLayout(row2)
        layout.addLayout(row3)
        layout.addLayout(row4)
        layout.addLayout(row5)
        layout.addStretch()

        self.setLayout(layout)
        self.hide()  # מוסתר כברירת מחדל

        layout.addLayout(self.moves_layout)

        self.scroll_area = QScrollArea()
        self.scroll_area.setWidgetResizable(True)
        self.scroll_content = QWidget()
        self.scroll_layout = QVBoxLayout(self.scroll_content)

        # שלוש שורות (אופקיות) של מופעים
        self.phase_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()]
        for row in self.phase_rows:
            self.scroll_layout.addLayout(row)

        self.scroll_area.setWidget(self.scroll_content)
        layout.addWidget(self.scroll_area)

    def set_row(self, row, label, textbox, btn):
        label.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        textbox.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addWidget(label)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(textbox)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn)
        row.addStretch()

    def show_panel(self, phase_list):
        self.display_moves(phase_list)
        self.show()

    def display_moves(self, moves_list):
        # נקה את השורות הקיימות
        for row in self.phase_rows:
            while row.count():
                item = row.takeAt(0)
                widget = item.widget()
                if widget:
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

            btn_remove = QPushButton("➖")
            btn_remove.setFixedSize(30, 30)
            btn_remove.clicked.connect(lambda _, p=phase: self.remove_phase(p))

            item_layout = QHBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            container = QFrame()
            container.setLayout(item_layout)
            container.setStyleSheet("background-color: #f0f0f0; border-radius: 5px; padding: 5px;")

            self.phase_rows[row_idx].addWidget(container)

    def remove_phase(self, phase_name):
        print(f"הוסר: {phase_name}")  # תחליף בלוגיקה שלך

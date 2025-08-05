import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout

from ConfigTool.config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'classes')))


class SetPhaseLayout(QWidget):
    def __init__(self):
        # =============== labels =============== #
        super().__init__()
        main_phase_label = QLabel("מופע ראשי")
        not_main_phase_label = QLabel("לא מופע ראשי")
        d_detectors_label = QLabel("גלאי דרישה")
        e_detectors_label = QLabel("גלאי הארכה")
        inter_stage_label = QLabel("מעברים")

        # =============== textbox =============== #
        main_phase_textbox = QLineEdit()
        not_main_phase_textbox = QLineEdit()
        d_detectors_textbox = QLineEdit()
        e_detectors_textbox = QLineEdit()
        inter_stage_textbox = QLineEdit()

        # =============== plus buttons =============== #
        main_phase_btn = QPushButton("➕")
        not_main_phase_btn = QPushButton("➕")
        d_detectors_btn = QPushButton("➕")
        e_detectors_btn = QPushButton("➕")
        inter_stage_btn = QPushButton("➕")

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        row5 = QHBoxLayout()
        row6 = QHBoxLayout()

        self.set_row_of_set_phase(row1, main_phase_label, main_phase_textbox, main_phase_btn)
        self.set_row_of_set_phase(row2, not_main_phase_label, not_main_phase_textbox, not_main_phase_btn)
        self.set_row_of_set_phase(row3, d_detectors_label, d_detectors_textbox, d_detectors_btn)
        self.set_row_of_set_phase(row4, e_detectors_label, e_detectors_textbox, e_detectors_btn)
        self.set_row_of_set_phase(row5, inter_stage_label, inter_stage_textbox, inter_stage_btn)


        layout = QVBoxLayout()

        layout.addLayout(row1)
        layout.addLayout(row2)
        layout.addLayout(row3)
        layout.addLayout(row4)
        layout.addLayout(row5)
        layout.addStretch()

        self.setLayout(layout)
        self.hide()  # מוסתר כברירת מחדל

    def set_row_of_set_phase(self, row, label, textbox, btn):
        label.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        textbox.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)
        btn.setFixedSize(BUTTON_WIDTH, BUTTON_HEIGHT)

        row.addWidget(label)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(textbox)
        row.addSpacing(COLUMN_SPACING)
        row.addWidget(btn)
        row.addStretch()

    def show_left_panel(self):
        self.show()

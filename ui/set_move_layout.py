import os
import sys

from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea, \
    QCheckBox, QRadioButton

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT


class SetMoveLayout(QWidget):
    def __init__(self, add_move_ref):
        super().__init__()

        # =============== labels =============== #
        label_layout = QHBoxLayout()

        main_phase_label = QLabel("שם המופע")
        not_main_phase_label = QLabel("סוג המופע")
        d_detectors_label = QLabel("מופע ראשי")

        # =============== textbox =============== #
        main_phase_textbox = QLineEdit()

        # =============== button =============== #
        run_button = QPushButton("הוסף")

        # =============== type move =============== #
        type_radio_layout = QHBoxLayout()

        self.traffic_radio = QRadioButton("🚗")
        self.traffic_flashing_radio = QRadioButton("🚛")
        self.pedestrian_radio = QRadioButton("🏃‍♂️")
        self.blinker_radio = QRadioButton("🟡")
        self.blinker_conditional_radio = QRadioButton("⚠️")
        self.traffic_radio.setChecked(True)  # ברירת מחדל

        type_radio_layout.addWidget(self.traffic_radio)
        type_radio_layout.addWidget(self.traffic_flashing_radio)
        type_radio_layout.addWidget(self.pedestrian_radio)
        type_radio_layout.addWidget(self.blinker_radio)
        type_radio_layout.addWidget(self.blinker_conditional_radio)

        # =============== main =============== #
        main_radio_layout = QHBoxLayout()

        self.main_radio = QRadioButton("כן")
        self.not_main_radio = QRadioButton("לא")
        self.main_radio.setChecked(True)

        main_radio_layout.addWidget(self.main_radio)
        main_radio_layout.addWidget(self.not_main_radio)

        # # inter_stage_btn         = QPushButton("➕")

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        # row5 = QHBoxLayout()

        row1.addWidget(main_phase_label)
        row1.addWidget(main_phase_textbox)

        row2.addWidget(not_main_phase_label)
        row2.addLayout(type_radio_layout)

        row3.addWidget(d_detectors_label)
        row3.addLayout(main_radio_layout)

        row4.addWidget(run_button)

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()  # create the container of the scroll bar. (get only widget)
        self.scroll_content = QWidget()  # create the widget that will be in the layout.
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
        main_layout = QVBoxLayout()

        # =============== create the layout =============== #
        main_layout.addLayout(row1)
        main_layout.addLayout(row2)
        main_layout.addLayout(row3)
        main_layout.addLayout(row4)

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
        self.show_scroll_bar(moves_list)
        self.show()

    def show_scroll_bar(self, moves_list):
        # נקה את השורות הקיימות
        for row in self.phase_rows:
            while row.count():
                item = row.takeAt(0)  # get the first QLayoutItem of the layout
                widget = item.widget()  # get the widget
                if widget:  #
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

    def add_move(self, label, value, add_move_ref):
        if label == "מופע ראשי":
            add_move_ref(value, True)
        elif label == "לא מופע ראשי":
            add_move_ref(value, False)

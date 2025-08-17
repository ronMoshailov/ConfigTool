from PyQt5.QtGui import QIcon
from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt5.QtWidgets import QRadioButton, QButtonGroup

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, \
    BLINKER_CONDITIONAL_IMAGE_PATH

from config.special import clean_text
from controllers.data_controller import DataController


class SetMoveLayout(QWidget):

    def __init__(self):
        super().__init__()

        self.data_controller = DataController()

        # =============== labels =============== #
        move_name_label = QLabel("שם המופע")
        move_type_label = QLabel("סוג המופע")
        is_main_label = QLabel("מופע ראשי")

        # =============== textbox =============== #
        main_phase_textbox = QLineEdit()

        # =============== button =============== #
        run_button = QPushButton("הוסף")

        # =============== type move =============== #
        type_radio_layout = QHBoxLayout()

        self.traffic_radio = QRadioButton()
        self.traffic_radio.setIcon(QIcon(GREEN_IMAGE_PATH))
        self.traffic_radio.setChecked(True)  # ברירת מחדל

        self.traffic_flashing_radio = QRadioButton()
        self.traffic_flashing_radio.setIcon(QIcon(GREEN_BLINKER_IMAGE_PATH))

        self.pedestrian_radio = QRadioButton()
        self.pedestrian_radio.setIcon(QIcon(PEDESTRIAN_IMAGE_PATH))

        self.blinker_radio = QRadioButton()
        self.blinker_radio.setIcon(QIcon(BLINKER_IMAGE_PATH))

        self.blinker_conditional_radio = QRadioButton()
        self.blinker_conditional_radio.setIcon(QIcon(BLINKER_CONDITIONAL_IMAGE_PATH))

        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.traffic_radio)
        type_radio_group.addButton(self.traffic_flashing_radio)
        type_radio_group.addButton(self.pedestrian_radio)
        type_radio_group.addButton(self.blinker_radio)
        type_radio_group.addButton(self.blinker_conditional_radio)

        type_radio_layout.addWidget(self.traffic_radio)
        type_radio_layout.addStretch()
        type_radio_layout.addWidget(self.traffic_flashing_radio)
        type_radio_layout.addStretch()
        type_radio_layout.addWidget(self.pedestrian_radio)
        type_radio_layout.addStretch()
        type_radio_layout.addWidget(self.blinker_radio)
        type_radio_layout.addStretch()
        type_radio_layout.addWidget(self.blinker_conditional_radio)

        # =============== main =============== #
        main_radio_layout = QHBoxLayout()

        self.main_radio = QRadioButton("כן")
        self.not_main_radio = QRadioButton("לא")
        self.main_radio.setChecked(True)

        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)

        main_radio_layout.addStretch()
        main_radio_layout.addWidget(self.main_radio)
        main_radio_layout.addStretch()
        main_radio_layout.addWidget(self.not_main_radio)
        main_radio_layout.addStretch()
        # # inter_stage_btn         = QPushButton("➕")

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()
        # row5 = QHBoxLayout()

        row1.addWidget(main_phase_textbox)
        row1.addWidget(move_name_label)

        row2.addLayout(type_radio_layout)
        row2.addWidget(move_type_label)

        row3.addLayout(main_radio_layout)
        row3.addWidget(is_main_label)

        row4.addWidget(run_button)
        run_button.clicked.connect(lambda: self.add_move(main_phase_textbox.text()))

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
        self.scroll_layout.addStretch()

        # =============== layout =============== #
        main_layout = QVBoxLayout()

        # =============== create the layout =============== #
        main_layout.addLayout(row1)
        main_layout.addStretch(1)
        main_layout.addLayout(row2)
        main_layout.addStretch(1)
        main_layout.addLayout(row3)
        main_layout.addStretch(1)
        main_layout.addLayout(row4)

        main_layout.addStretch(10)
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

    def show_panel(self):
        self.show_scroll_bar()
        self.show()

    def show_scroll_bar(self):
        moves_list = self.data_controller.get_all_moves()

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
            # print(f"move name: {move.name}, move main: {move.is_main}")
            label = QLabel(phase + ("⭐" if move.is_main is True else ""))
            label.setFixedHeight(30)

            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(30, 30)

            btn_remove.clicked.connect(
                lambda _=False, phase_clean=clean_text(phase):  # קיבוע הערך
                self.remove_move(phase_clean)
            )

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
        self.data_controller.remove_move(move_name)
        self.show_scroll_bar()

    def add_move(self, name):

        is_main = True if self.main_radio.isChecked() else False
        move_name = ""
        move_type = None

        if self.traffic_radio.isChecked():
            move_type = "Traffic"
            move_name = "k" + name
        elif self.traffic_flashing_radio.isChecked():
            move_type = "Traffic_Flashing"
            move_name = "k" + name
        elif self.pedestrian_radio.isChecked():
            move_type = "Pedestrian"
            move_name = "p" + name
        elif self.blinker_radio.isChecked():
            move_type = "Blinker"
            move_name = "B" + name
        elif self.blinker_conditional_radio.isChecked():
            move_type = "Blinker_Conditional"
            move_name = "B" + name

        if move_type is None or move_name == "":
            print("Error with type in \"add_move\"")
            return
        # print(f"Go to \"add move\" ref with: move_name: {move_name}, move_type: {move_type}, is_main: {is_main}, min_green: 0")
        self.data_controller.add_move(move_name, move_type, is_main, "0")
        self.show_scroll_bar()



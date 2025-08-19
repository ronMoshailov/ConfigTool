from PyQt5.QtCore import Qt
from PyQt5.QtGui import QIcon
from PyQt5.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt5.QtWidgets import QRadioButton, QButtonGroup

from config.constants import BUTTON_WIDTH, COLUMN_SPACING, BUTTON_HEIGHT
from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, \
    BLINKER_CONDITIONAL_IMAGE_PATH

from config.special import clean_text, set_blue_button_white_text_style
from config.style import text_field_style, radio_tile_style, scroll_style, gray_label_style, scroll_label_style, \
    btn_remove_style, blue_button_white_text_style, dark_button_style
from controllers.data_controller import DataController
from entities.log import Log


class SetMoveLayout(QWidget):

    def __init__(self):
        super().__init__()

        self.data_controller = DataController()

        # =============== labels =============== #
        #                      index 0             index 1              index 2
        label_list = [  QLabel("שם המופע"), QLabel("סוג המופע"), QLabel("מופע ראשי")]

        label_list[0] = QLabel("שם המופע")
        label_list[1] = QLabel("סוג המופע")
        label_list[2] = QLabel("מופע ראשי")

        # set style
        label_list[0].setStyleSheet(gray_label_style)
        label_list[1].setStyleSheet(gray_label_style)
        label_list[2].setStyleSheet(gray_label_style)

        # center the text
        label_list[0].setAlignment(Qt.AlignCenter)
        label_list[1].setAlignment(Qt.AlignCenter)
        label_list[2].setAlignment(Qt.AlignCenter)

        # =============== textbox =============== #
        main_phase_textbox = QLineEdit()                    # create textbox
        main_phase_textbox.setAlignment(Qt.AlignRight)      # center the text
        main_phase_textbox.setMaximumWidth(350)             # set min-width
        main_phase_textbox.setFixedHeight(32)               # set fixed height
        main_phase_textbox.setStyleSheet(text_field_style)  # set style

        # =============== button =============== #
        run_button = QPushButton("הוסף")                # create button
        run_button.setStyleSheet(blue_button_white_text_style + dark_button_style)
        run_button.clicked.connect(lambda: self.add_move(main_phase_textbox.text()))

        # =============== type move =============== #
        type_radio_layout = QHBoxLayout()                       # create horizontal layout

        self.traffic_radio = QRadioButton()                     # create radio button
        self.traffic_radio.setIcon(QIcon(GREEN_IMAGE_PATH))     # set icon
        self.traffic_radio.setChecked(True)                     # default checked

        self.traffic_flashing_radio = QRadioButton()                            # create radio button
        self.traffic_flashing_radio.setIcon(QIcon(GREEN_BLINKER_IMAGE_PATH))    # set icon

        self.pedestrian_radio = QRadioButton()                          # create radio button
        self.pedestrian_radio.setIcon(QIcon(PEDESTRIAN_IMAGE_PATH))     # set icon

        self.blinker_radio = QRadioButton()                     # create radio button
        self.blinker_radio.setIcon(QIcon(BLINKER_IMAGE_PATH))   # set icon

        self.blinker_conditional_radio = QRadioButton()                                 # create radio button
        self.blinker_conditional_radio.setIcon(QIcon(BLINKER_CONDITIONAL_IMAGE_PATH))   # set icon

        # create radio button group
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.traffic_radio)
        type_radio_group.addButton(self.traffic_flashing_radio)
        type_radio_group.addButton(self.pedestrian_radio)
        type_radio_group.addButton(self.blinker_radio)
        type_radio_group.addButton(self.blinker_conditional_radio)

        # add radio to button
        type_radio_layout.addStretch() # move the elements right
        type_radio_layout.addWidget(self.traffic_radio)
        type_radio_layout.addSpacing(20)
        type_radio_layout.addWidget(self.traffic_flashing_radio)
        type_radio_layout.addSpacing(20)
        type_radio_layout.addWidget(self.pedestrian_radio)
        type_radio_layout.addSpacing(20)
        type_radio_layout.addWidget(self.blinker_radio)
        type_radio_layout.addSpacing(20)
        type_radio_layout.addWidget(self.blinker_conditional_radio)

        # ============== is main ============== #
        main_radio_layout = QHBoxLayout() # create layout

        # radio button
        self.main_radio = QRadioButton("כן")
        self.not_main_radio = QRadioButton("לא")
        self.main_radio.setChecked(True) # checked is default

        # radio group
        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)

        # add to main layout
        main_radio_layout.addStretch() # move the elements right
        main_radio_layout.addWidget(self.main_radio)
        main_radio_layout.addSpacing(10)
        main_radio_layout.addWidget(self.not_main_radio)
        main_radio_layout.addSpacing(30)

        # =============== rows =============== #
        row1 = QHBoxLayout()
        row2 = QHBoxLayout()
        row3 = QHBoxLayout()
        row4 = QHBoxLayout()

        row1.addStretch()
        row1.addWidget(main_phase_textbox)
        row1.addWidget(label_list[0])

        row2.addLayout(type_radio_layout)
        row2.addWidget(label_list[1])

        row3.addLayout(main_radio_layout)
        row3.addWidget(label_list[2])

        row4.addStretch()
        row4.addWidget(run_button)

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()    # create the container of the scroll bar. (get only widget)
        self.scroll_content = QWidget()     # create the widget that will be in the layout.
        self.scroll_layout = QVBoxLayout()

        self.scroll_area.setWidgetResizable(True) # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content.setLayout(self.scroll_layout) # set the widget as the father of the layout
        self.scroll_area.setWidget(self.scroll_content)   # set 'scroll_area' as father of 'scroll_content'

        #
        self.phase_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()] # list of layout that each one is a row in the scroll bar
        self.scroll_layout.addLayout(self.phase_rows[0])    # add row 1 to the layout
        self.scroll_layout.addLayout(self.phase_rows[1])    # add row 2 to the layout
        self.scroll_layout.addLayout(self.phase_rows[2])    # add row 3 to the layout
        self.scroll_layout.addStretch()                     # move all up
        self.scroll_area.setStyleSheet(scroll_style)        # set style to scroll bar

        for row in self.phase_rows: # add space between each element
            row.setSpacing(40)      # add space between each element

        # =============== layout =============== #
        main_layout = QVBoxLayout()

        # add style to each radio button
        for rb in (
                self.traffic_radio,
                self.traffic_flashing_radio,
                self.pedestrian_radio,
                self.blinker_radio,
                self.blinker_conditional_radio,
                self.main_radio,
                self.not_main_radio,
        ):
            rb.setStyleSheet(radio_tile_style)
            rb.setCursor(Qt.PointingHandCursor)

        # =============== create the layout =============== #
        main_layout.addLayout(row1)
        main_layout.addSpacing(30)
        main_layout.addLayout(row2)
        main_layout.addSpacing(30)
        main_layout.addLayout(row3)
        main_layout.addSpacing(30)
        main_layout.addLayout(row4)

        main_layout.addSpacing(50)
        main_layout.addWidget(self.scroll_area)

        self.setLayout(main_layout)
        self.hide()

        # =============== scroll rows =============== #

    def show_panel(self):
        """
        Show the 'set move' panel.

        :return: None
        """
        self.show_scroll_bar()
        self.show()

    def show_scroll_bar(self):
        """
        Show the scroll bar of the 'set move' panel.

        :return: None
        """
        moves_list = self.data_controller.get_all_moves() # get all moves

        # delete the existing rows
        for row in self.phase_rows:
            while row.count():
                item = row.takeAt(0)  # get the first QLayoutItem of the layout
                widget = item.widget()  # get the widget
                if widget:  #
                    widget.deleteLater() # remove widget (when widget remove -> all him layouts removed also)

        for move in moves_list:
            phase = move.name
            move_type = move.type

            if move_type == "Traffic":
                row_idx = 0
                icon = GREEN_IMAGE_PATH
            elif move_type == "Traffic_Flashing":
                row_idx = 0
                icon = GREEN_BLINKER_IMAGE_PATH
            elif move_type == "Pedestrian":
                row_idx = 1
                icon = PEDESTRIAN_IMAGE_PATH
            elif move_type == "Blinker":
                row_idx = 2
                icon = BLINKER_IMAGE_PATH
            elif move_type == "Blinker_Conditional":
                row_idx = 2
                icon = BLINKER_CONDITIONAL_IMAGE_PATH
            else:
                Log.warning(f"Move type '{move_type}' is not supported.")
                continue

            label = QLabel()
            move_txt = phase + ("⭐" if move.is_main else "")
            label.setText(f"{move_txt} <img src='{icon}' width='20' height='25'/>")
            label.setStyleSheet(scroll_label_style)
            label.setFixedHeight(30)

            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(30, 30)
            btn_remove.setStyleSheet(btn_remove_style)
            btn_remove.clicked.connect(lambda _=False, phase_clean=clean_text(phase): self.remove_move(phase_clean))

            item_layout = QVBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            container = QFrame()
            container.setLayout(item_layout)

            self.phase_rows[row_idx].addWidget(container)
        self.phase_rows[0].addStretch() # move the row left
        self.phase_rows[1].addStretch() # move the row left
        self.phase_rows[2].addStretch() # move the row left


    def remove_move(self, move_name):
        """
        This method removes the move and refresh the scroll bar.
        :param move_name:
        :return:
        """
        if self.data_controller.remove_move(move_name):
            self.show_scroll_bar()
            Log.success("Removed successfully!")

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
            Log.error("Error with type in \"add_move\"")
            return False
        if self.data_controller.add_move(move_name, move_type, is_main, "0"):
            self.show_scroll_bar()
            return True
        return False



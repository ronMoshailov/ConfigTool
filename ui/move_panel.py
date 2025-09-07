from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt6.QtWidgets import QRadioButton, QButtonGroup

from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, \
    BLINKER_CONDITIONAL_IMAGE_PATH

from config.special import clean_text
from config.style import move_panel_style
from controllers.data_controller import DataController
from entities.log import Log


class MovePanel(QWidget):
    """
        A panel widget that allows adding and removing move buttons.
    """

    def __init__(self):
        super().__init__()

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== Name Layout =============== #
        self.name_layout = QHBoxLayout()    # name layout

        # =============== Type Layout =============== #
        self.type_layout = QHBoxLayout()    # type radio layout

        # =============== Main Layout =============== #
        self.main_layout = QHBoxLayout()    # main radio layout

        # =============== Button Layout =============== #
        self.btn_layout = QHBoxLayout()     # button 'run' layout

        # =============== Moves Layouts List =============== #
        self.moves_layout_list = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()] # list of layouts that each one is a row in the scroll bar
        for row in self.moves_layout_list: # add space between each element
            row.setSpacing(10)      # add space between each element

        # =============== Scroll Layout =============== #
        scroll_layout = QVBoxLayout()       # scroll layout
        scroll_layout.addLayout(self.moves_layout_list[0])      # add traffic layout
        scroll_layout.addLayout(self.moves_layout_list[1])      # add pedestrians layout
        scroll_layout.addLayout(self.moves_layout_list[2])      # add blinker layout
        scroll_layout.addStretch()                              # move all up

        # =============== Scroll =============== #
        self.scroll_area = QScrollArea()        # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()         # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")   #

        self.scroll_area.setWidget(self.scroll_content)   # set 'scroll_area' as father of 'scroll_content'
        self.scroll_content.setLayout(scroll_layout) # set the widget as the father of the layout

        # =============== Root Layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addLayout(self.name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.main_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(self.scroll_area)

        # =============== build layouts =============== #
        self._build_name_layout()

        self._init_type_radio()
        self._build_type_radio_layout()

        self._init_main_radio()
        self._build_main_radio_layout()

        self._build_button_layout()

        # =============== self =============== #
        self.setLayout(root_layout)
        self.setObjectName("movePanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(move_panel_style)
        self.hide()

    # =============== Add methods =============== #
    def _add_move(self, name):

        if name == "":
            self.data_controller.write_log("The textbox is empty", "r")
            return False

        if any(c.isalpha() for c in name) and any(c.isdigit() for c in name):
            self.data_controller.write_log("The textbox value is incorrect", "r")
            return False

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

        if self.data_controller.add_move(move_name, move_type, is_main, "0"):
            self._show_scroll_bar()
            self.data_controller.write_log(f"The move '{move_name}' added successfully", "g")
            return True
        return False

    # =============== Remove methods =============== #
    def _clear_move_layouts(self):
        for row in self.moves_layout_list:
            # Warning: 'row.takeAt(0)' remove from first item in the 'row' list, I added just widgets so I just remove them, it someone will add in the future any layout they will be removed from 'row' but still exist in Qt and because it belong to Qt the garbage collector will not remove them
            while row.count():  # return how many 'QLayoutItem' exist in the list of 'QLayoutItem'
                item = row.takeAt(0)  # get the first QLayoutItem of the layout 'row' and remove him from the list of the 'QLayoutItem'
                widget = item.widget()  # get the widget
                if widget:
                    widget.deleteLater()  # remove widget (when widget remove -> all his layouts and children removed also), safer not remove now so he will be removed safely later by Qt

    def _remove_move(self, move_name):
        """
        This method removes the move and refresh the scroll bar.

        :param move_name:
        :return:
        """
        if self.data_controller.remove_move(move_name):
            self._show_scroll_bar()
            self.data_controller.write_log(f"The move \"{move_name}\" has been removed successfully.", "g")
            return True
        self.data_controller.write_log(f"The move \"{move_name}\" has not been removed.", "r")
        return True

    # =============== General methods =============== #
    def show_panel(self):
        self._show_scroll_bar()
        self.data_controller.clear_log()
        self.show()

    # =============== Inner methods =============== #
    def _show_scroll_bar(self):
        # =============== Data =============== #
        moves_list = self.data_controller.get_all_moves()

        # =============== Remove All Widgets =============== #
        self._clear_move_layouts()
        self.main_phase_textbox.clear()

        for move in moves_list:
            phase = move.name
            move_type = move.type

            # =============== Determine icon and index =============== #
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
                Log.warning(f"יש בעיה בקוד ב-'_show_scroll_bar'")
                continue

            # =============== QLabel =============== #
            label = QLabel()
            label.setText(f"{phase} <img src='{icon}' width='25' height='25'/> {"⭐" if move.is_main else ""}")
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            # =============== QPushButton =============== #
            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(18, 18)
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.setCursor(Qt.CursorShape.PointingHandCursor)
            btn_remove.clicked.connect(lambda _=False, phase_clean=clean_text(phase): self._remove_move(phase_clean))

            # =============== Layout =============== #
            card_layout = QVBoxLayout()
            card_layout.addWidget(label)
            card_layout.addWidget(btn_remove)

            # =============== QFrame =============== #
            card_widget = QFrame()
            card_widget.setLayout(card_layout)

            # =============== add to layout =============== #
            self.moves_layout_list[row_idx].addWidget(card_widget)

        # =============== move rows left =============== #
        self.moves_layout_list[0].addStretch()
        self.moves_layout_list[1].addStretch()
        self.moves_layout_list[2].addStretch()

    def _build_name_layout(self):
        # =============== QLabel =============== #
        label = QLabel("שם המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # =============== QLineEdit =============== #
        self.main_phase_textbox = QLineEdit()                                # create textbox
        self.main_phase_textbox.setObjectName("text_box")
        self.main_phase_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)    # center the text
        self.main_phase_textbox.setMaximumWidth(350)                         # set max-width
        self.main_phase_textbox.setFixedHeight(32)                           # set fixed height

        # =============== Name Layout =============== #
        self.name_layout.addStretch()
        self.name_layout.addWidget(self.main_phase_textbox)
        self.name_layout.addWidget(label)
        self.name_layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom

    def _init_type_radio(self):
        # =============== QRadioButton Traffic =============== #
        self.traffic_radio = QRadioButton()                     # create radio button
        self.traffic_radio.setIcon(QIcon(GREEN_IMAGE_PATH))     # set icon
        self.traffic_radio.setChecked(True)                     # default checked

        # =============== QRadioButton Traffic Flashing =============== #
        self.traffic_flashing_radio = QRadioButton()  # create radio button
        self.traffic_flashing_radio.setIcon(QIcon(GREEN_BLINKER_IMAGE_PATH))  # set icon

        # =============== QRadioButton Pedestrian =============== #
        self.pedestrian_radio = QRadioButton()  # create radio button
        self.pedestrian_radio.setIcon(QIcon(PEDESTRIAN_IMAGE_PATH))  # set icon

        # =============== QRadioButton Blinker =============== #
        self.blinker_radio = QRadioButton()  # create radio button
        self.blinker_radio.setIcon(QIcon(BLINKER_IMAGE_PATH))  # set icon

        # =============== QRadioButton Blinker Conditional =============== #
        self.blinker_conditional_radio = QRadioButton()  # create radio button
        self.blinker_conditional_radio.setIcon(QIcon(BLINKER_CONDITIONAL_IMAGE_PATH))  # set icon

        # =============== QButtonGroup =============== #
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.traffic_radio)
        type_radio_group.addButton(self.traffic_flashing_radio)
        type_radio_group.addButton(self.pedestrian_radio)
        type_radio_group.addButton(self.blinker_radio)
        type_radio_group.addButton(self.blinker_conditional_radio)

    def _build_type_radio_layout(self):
        # =============== QLabel =============== #
        label = QLabel("סוג המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # =============== Type Layout =============== #
        self.type_layout.addStretch() # move the elements right
        self.type_layout.addWidget(self.traffic_radio)
        self.type_layout.addSpacing(20)
        self.type_layout.addWidget(self.traffic_flashing_radio)
        self.type_layout.addSpacing(20)
        self.type_layout.addWidget(self.pedestrian_radio)
        self.type_layout.addSpacing(20)
        self.type_layout.addWidget(self.blinker_radio)
        self.type_layout.addSpacing(20)
        self.type_layout.addWidget(self.blinker_conditional_radio)

        self.type_layout.addWidget(label)
        self.type_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _init_main_radio(self):
        # =============== QRadioButton Main =============== #
        self.main_radio = QRadioButton("כן")
        self.main_radio.setChecked(True)  # checked is default

        # =============== QRadioButton Not Main =============== #
        self.not_main_radio = QRadioButton("לא")

        # =============== QButtonGroup =============== #
        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)

    def _build_main_radio_layout(self):
        # =============== QLabel =============== #
        label = QLabel("מופע ראשי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # =============== Main Layout =============== #
        self.main_layout.addStretch() # move the elements right
        self.main_layout.addWidget(self.main_radio)
        self.main_layout.addSpacing(10)
        self.main_layout.addWidget(self.not_main_radio)
        self.main_layout.addSpacing(30)

        self.main_layout.addWidget(label)
        self.main_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _build_button_layout(self):
        # =============== QPushButton =============== #
        run_button = QPushButton("הוסף")
        run_button.setCursor(Qt.CursorShape.PointingHandCursor)
        run_button.setObjectName("add_button")
        run_button.clicked.connect(lambda: self._add_move(self.main_phase_textbox.text()))

        # =============== Button Layout =============== #
        self.btn_layout.addStretch()
        self.btn_layout.addWidget(run_button)
        self.btn_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

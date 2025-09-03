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

        # =============== layouts =============== #
        root_layout = QVBoxLayout()         # panel layout
        self.name_layout = QHBoxLayout()    # name layout
        self.type_layout = QHBoxLayout()    # type radio layout
        self.main_layout = QHBoxLayout()    # main radio layout
        self.btn_layout = QHBoxLayout()     # button 'run' layout
        scroll_layout = QVBoxLayout()       # scroll layout
        traffic_layout = QHBoxLayout()
        pedestrian_layout = QHBoxLayout()
        blinker_layout = QHBoxLayout()
        self.scroll_rows_list = [traffic_layout, pedestrian_layout, blinker_layout] # list of layouts that each one is a row in the scroll bar

        # =============== widgets =============== #
        scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        scroll_content = QWidget()                      # create the widget that will be in the layout.
        scroll_content.setObjectName("scrollContent")   #

        # =============== build layouts =============== #
        self._build_name_layout()

        self._init_type_radio()
        self._build_type_radio_layout()

        self._init_main_radio()
        self._build_main_radio_layout()

        self._build_button_layout()

        scroll_layout.addLayout(self.scroll_rows_list[0])    # add row 1 to the layout
        scroll_layout.addLayout(self.scroll_rows_list[1])    # add row 2 to the layout
        scroll_layout.addLayout(self.scroll_rows_list[2])    # add row 3 to the layout
        scroll_layout.addStretch()                     # move all up

        root_layout.addLayout(self.name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.main_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(self.btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(scroll_area)

        # =============== x =============== #
        scroll_area.setWidget(scroll_content)   # set 'scroll_area' as father of 'scroll_content'
        scroll_content.setLayout(scroll_layout) # set the widget as the father of the layout

        for row in self.scroll_rows_list: # add space between each element
            row.setSpacing(10)      # add space between each element

        # =============== self =============== #
        self.setObjectName("movePanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(move_panel_style)
        self.setLayout(root_layout)
        self.hide()

        # =============== scroll rows =============== #

    def show_panel(self):
        self._show_scroll_bar()
        self.show()

    def _show_scroll_bar(self):
        moves_list = self.data_controller.get_all_moves() # get all moves

        # remove all widgets
        for row in self.scroll_rows_list:
            # Warning: 'row.takeAt(0)' remove from first item in the 'row' list, I added just widgets so I just remove them, it someone will add in the future any layout they will be removed from 'row' but still exist in Qt and because it belong to Qt the garbage collector will not remove them
            while row.count():          # return how many 'QLayoutItem' exist in the list of 'QLayoutItem'
                item = row.takeAt(0)    # get the first QLayoutItem of the layout 'row' and remove him from the list of the 'QLayoutItem'
                widget = item.widget()  # get the widget
                if widget:  #
                    widget.deleteLater() # remove widget (when widget remove -> all his layouts and children removed also), safer not remove now so he will be removed safely later by Qt

        # build the rows
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

            # label
            label = QLabel()
            label.setText(f"{phase} <img src='{icon}' width='25' height='25'/> {"⭐" if move.is_main else ""}")
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            # remove button
            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(18, 18)
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.setCursor(Qt.CursorShape.PointingHandCursor)
            btn_remove.clicked.connect(lambda _=False, phase_clean=clean_text(phase): self._remove_move(phase_clean))

            # vertical layout
            item_layout = QVBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            # container for the vertical layout
            container = QFrame()
            container.setLayout(item_layout)

            # add the container to the row
            self.scroll_rows_list[row_idx].addWidget(container)

        # move the rows left
        self.scroll_rows_list[0].addStretch()
        self.scroll_rows_list[1].addStretch()
        self.scroll_rows_list[2].addStretch()

    # =============== inner methods =============== #
    def _remove_move(self, move_name):
        """
        This method removes the move and refresh the scroll bar.

        :param move_name:
        :return:
        """
        if self.data_controller.remove_move(move_name):
            self._show_scroll_bar()
            Log.success("Removed successfully!")   # continue here!!!!!

    def _add_move(self, name):

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
            Log.error("Error with type in \"_add_move\"")
            return False
        if self.data_controller.add_move(move_name, move_type, is_main, "0"):
            self._show_scroll_bar()
            return True
        return False

    def _build_name_layout(self):

        label = QLabel("שם המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        self.main_phase_textbox = QLineEdit()                                # create textbox
        self.main_phase_textbox.setObjectName("text_box")
        self.main_phase_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)    # center the text
        self.main_phase_textbox.setMaximumWidth(350)                         # set max-width
        self.main_phase_textbox.setFixedHeight(32)                           # set fixed height

        self.name_layout.addStretch()
        self.name_layout.addWidget(self.main_phase_textbox)
        self.name_layout.addWidget(label)
        self.name_layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom

    def _init_type_radio(self):
        self.traffic_radio = QRadioButton()  # create radio button
        self.traffic_radio.setIcon(QIcon(GREEN_IMAGE_PATH))  # set icon
        self.traffic_radio.setChecked(True)  # default checked

        self.traffic_flashing_radio = QRadioButton()  # create radio button
        self.traffic_flashing_radio.setIcon(QIcon(GREEN_BLINKER_IMAGE_PATH))  # set icon

        self.pedestrian_radio = QRadioButton()  # create radio button
        self.pedestrian_radio.setIcon(QIcon(PEDESTRIAN_IMAGE_PATH))  # set icon

        self.blinker_radio = QRadioButton()  # create radio button
        self.blinker_radio.setIcon(QIcon(BLINKER_IMAGE_PATH))  # set icon

        self.blinker_conditional_radio = QRadioButton()  # create radio button
        self.blinker_conditional_radio.setIcon(QIcon(BLINKER_CONDITIONAL_IMAGE_PATH))  # set icon

        # create radio button group
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.traffic_radio)
        type_radio_group.addButton(self.traffic_flashing_radio)
        type_radio_group.addButton(self.pedestrian_radio)
        type_radio_group.addButton(self.blinker_radio)
        type_radio_group.addButton(self.blinker_conditional_radio)

    def _build_type_radio_layout(self):
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
        label = QLabel("סוג המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        self.type_layout.addWidget(label)
        self.type_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _init_main_radio(self):
        self.main_radio = QRadioButton("כן")
        self.not_main_radio = QRadioButton("לא")
        self.main_radio.setChecked(True)  # checked is default

        # radio group
        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)

    def _build_main_radio_layout(self):

        # add to main layout
        self.main_layout.addStretch() # move the elements right
        self.main_layout.addWidget(self.main_radio)
        self.main_layout.addSpacing(10)
        self.main_layout.addWidget(self.not_main_radio)
        self.main_layout.addSpacing(30)

        label = QLabel("מופע ראשי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        self.main_layout.addWidget(label)
        self.main_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _build_button_layout(self):

        run_button = QPushButton("הוסף")                # create button
        run_button.setObjectName("add_button")
        run_button.clicked.connect(lambda: self._add_move(self.main_phase_textbox.text()))

        self.btn_layout.addStretch()
        self.btn_layout.addWidget(run_button)
        self.btn_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom




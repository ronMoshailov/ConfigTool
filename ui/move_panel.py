from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QMessageBox, QRadioButton, QButtonGroup
from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon

from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, BLINKER_CONDITIONAL_IMAGE_PATH
from config.special import clean_text, init_scroll, clear_widget_from_layout
from config.style import move_panel_style

from controllers.data_controller import DataController

class MovePanel(QWidget):
    def __init__(self):
        super().__init__()

        # controllers
        self.data_controller = DataController()

        # Name Layout
        self.name_layout = self._build_name_layout()

        # Type Layout
        self._init_type_radio()
        type_layout = self._build_type_radio_layout()

        # Main Layout
        self._init_main_radio()
        main_layout = self._build_main_radio_layout()

        # Button Layout
        btn_layout = self._build_button_layout()

        # Moves Layouts List
        self.moves_layout_list = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()] # list of layouts that each one is a row in the scroll bar
        for row in self.moves_layout_list:
            row.setSpacing(20)

        # Scroll Layout
        scroll_layout = QVBoxLayout()                           # scroll layout
        scroll_layout.addLayout(self.moves_layout_list[0])      # add traffic layout
        scroll_layout.addLayout(self.moves_layout_list[1])      # add pedestrians layout
        scroll_layout.addLayout(self.moves_layout_list[2])      # add blinker layout
        scroll_layout.addStretch()                              # move all up

        # Scroll Layout
        scroll_area = init_scroll(scroll_layout)

        # Root Layout
        root_layout = QVBoxLayout()
        root_layout.addLayout(self.name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(main_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(scroll_area)

        # self
        self.setLayout(root_layout)
        self.setObjectName("movePanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(move_panel_style)
        self.hide()

    # =============== Add methods =============== #
    def _add_move(self, name):
        # check if move name contain numbers and digits
        is_main = True if self.main_radio.isChecked() else False

        # initialize variables
        move_type = ""

        # fix the value to fit the DB
        if self.traffic_radio.isChecked():
            move_type = "Traffic"
        elif self.traffic_flashing_radio.isChecked():
            move_type = "Traffic_Flashing"
        elif self.pedestrian_radio.isChecked():
            move_type = "Pedestrian"
        elif self.blinker_radio.isChecked():
            move_type = "Blinker"
        elif self.blinker_conditional_radio.isChecked():
            move_type = "Blinker_Conditional"

        # add the move to DB
        success, message = self.data_controller.add_move(name, move_type, is_main)
        if success:
            self._show_scroll_bar()
            return
        QMessageBox.critical(self, "שגיאה", message)
        self.main_phase_textbox.setText("")

    # =============== Remove methods =============== #
    def _remove_move(self, move_name):
        success, message = self.data_controller.remove_move(move_name)
        if success:
            self._show_scroll_bar()
            return
        QMessageBox.critical(self, "שגיאה", message)

    # =============== General methods =============== #
    def show_panel(self):
        self._show_scroll_bar()
        self.show()

    # =============== Inner methods =============== #
    def _show_scroll_bar(self):
        # Data
        moves_list = self.data_controller.get_all_moves()

        # Remove All Widgets
        clear_widget_from_layout(self.moves_layout_list)
        self.main_phase_textbox.clear()

        for move in moves_list:
            phase = move.name
            move_type = move.type

            # Determine icon and index
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
                QMessageBox.critical(self, "שגיאה", "לא תקין, התקלה הזאת נובעת מהקוד ולא בגלל המשתמש move_type")
                continue

            # QLabel
            label = QLabel()
            label.setText(f"{phase} <img src='{icon}' width='25' height='25'/> {"⭐" if move.is_main else ""}")
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            # QPushButton
            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(18, 18)
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.setCursor(Qt.CursorShape.PointingHandCursor)
            btn_remove.clicked.connect(lambda _=False, phase_clean=clean_text(phase): self._remove_move(phase_clean))

            # =============== Layout =============== #
            card_layout = QVBoxLayout()
            card_layout.addWidget(label)
            card_layout.addWidget(btn_remove)

            # QFrame
            card_widget = QFrame()
            card_widget.setLayout(card_layout)

            # =============== add to layout =============== #
            self.moves_layout_list[row_idx].addWidget(card_widget)

        # =============== move rows left =============== #
        self.moves_layout_list[0].addStretch()
        self.moves_layout_list[1].addStretch()
        self.moves_layout_list[2].addStretch()

    # =============== build methods =============== #
    def _build_name_layout(self):
        # QLabel
        label = QLabel("שם המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # QLineEdit
        self.main_phase_textbox = QLineEdit()
        self.main_phase_textbox.setObjectName("text_box")
        self.main_phase_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)    # center the text
        self.main_phase_textbox.setMaximumWidth(350)
        self.main_phase_textbox.setFixedHeight(32)

        # Layout
        layout = QHBoxLayout()

        layout.addStretch()
        layout.addWidget(self.main_phase_textbox)
        layout.addWidget(label)
        layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom

        return layout

    def _build_type_radio_layout(self):
        # QLabel
        label = QLabel("סוג המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # Layout
        layout = QHBoxLayout()

        layout.addStretch() # move the elements right
        layout.addWidget(self.traffic_radio)
        layout.addSpacing(20)
        layout.addWidget(self.traffic_flashing_radio)
        layout.addSpacing(20)
        layout.addWidget(self.pedestrian_radio)
        layout.addSpacing(20)
        layout.addWidget(self.blinker_radio)
        layout.addSpacing(20)
        layout.addWidget(self.blinker_conditional_radio)

        layout.addWidget(label)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

        return layout

    def _build_main_radio_layout(self):
        # QLabel
        label = QLabel("מופע ראשי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        # Layout
        layout = QHBoxLayout()

        layout.addStretch() # move the elements right
        layout.addWidget(self.main_radio)
        layout.addSpacing(10)
        layout.addWidget(self.not_main_radio)
        layout.addSpacing(30)
        layout.addWidget(label)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

        return layout

    def _build_button_layout(self):
        # QPushButton
        run_button = QPushButton("הוסף")
        run_button.setCursor(Qt.CursorShape.PointingHandCursor)
        run_button.setObjectName("add_button")
        run_button.clicked.connect(lambda: self._add_move(self.main_phase_textbox.text()))

        # Layout
        layout = QHBoxLayout()

        layout.addStretch()
        layout.addWidget(run_button)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

        return layout

    # =============== Init methods =============== #
    def _init_type_radio(self):
        # QRadioButton
        self.traffic_radio = QRadioButton()                     
        self.traffic_radio.setIcon(QIcon(GREEN_IMAGE_PATH))
        self.traffic_radio.setChecked(True)

        self.traffic_flashing_radio = QRadioButton()  
        self.traffic_flashing_radio.setIcon(QIcon(GREEN_BLINKER_IMAGE_PATH))

        self.pedestrian_radio = QRadioButton()  
        self.pedestrian_radio.setIcon(QIcon(PEDESTRIAN_IMAGE_PATH))

        self.blinker_radio = QRadioButton()  
        self.blinker_radio.setIcon(QIcon(BLINKER_IMAGE_PATH))

        self.blinker_conditional_radio = QRadioButton()  
        self.blinker_conditional_radio.setIcon(QIcon(BLINKER_CONDITIONAL_IMAGE_PATH))

        # QButtonGroup
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.traffic_radio)
        type_radio_group.addButton(self.traffic_flashing_radio)
        type_radio_group.addButton(self.pedestrian_radio)
        type_radio_group.addButton(self.blinker_radio)
        type_radio_group.addButton(self.blinker_conditional_radio)

    def _init_main_radio(self):
        # QRadioButton
        self.main_radio = QRadioButton("כן")
        self.main_radio.setChecked(True)  # checked is default

        self.not_main_radio = QRadioButton("לא")

        # QButtonGroup
        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)


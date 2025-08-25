from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt6.QtWidgets import QRadioButton, QButtonGroup

from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, \
    BLINKER_CONDITIONAL_IMAGE_PATH

from config.special import clean_text
from config.style import blue_button_white_text_style
from controllers.data_controller import DataController
from entities.log import Log


class SetMovePanel(QWidget):

    def __init__(self):
        super().__init__()

        self.setObjectName("movePanel")

        # =============== style =============== #
        root_style = """
        /* ********************************* panel ********************************* */
        #movePanel{
            border-radius: 20px;
            border: 1px solid #1a98ff;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0   #94cfff,
                stop:1   #f0f8ff
            );
        }

        /* ********************************* gray label ********************************* */
            QLabel#gray_label {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
                color: #2c3e50;
                border: 1px solid #d5d8dc;
                border-radius: 8px;
                padding: 8px 12px;
                font-size: 24px;
                font-weight: bold;
                min-width: 170px;
            }

            QLabel#gray_label:hover {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
                border: 1px solid #b2babb;
            }
            
        /* ********************************* text field ********************************* */
                QLineEdit#text_box {
                    font-size: 32px;
                    max-height: 50px;
                    padding: 6px 10px;
                    border: 2px solid #cccccc;
                    border-radius: 6px;
                    background-color: #fdfdfd;
                    selection-background-color: #3399ff;
                }
                QLineEdit#text_box:focus {
                    border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
                    background-color: #ffffff;
                }
                QLineEdit#text_box:disabled {
                    background-color: #eeeeee;
                    color: #888888;
                }

        /* ********************************* Radio Button ********************************* */
                QRadioButton {
                    qproperty-iconSize: 22px 22px;   /* אם יש גם אייקון */
                    spacing: 8px;                    /* רווח בין הבולט/אייקון לטקסט */
                    font-weight: bold;
                    font-size: 16px;
                    color: #2c3e50;
                }
        
                QRadioButton::indicator {
                    width: 18px; height: 18px;
                    border-radius: 9px;
                    border: 2px solid #2471a3;
                    background: #ffffff;
                    margin: 0 6px;
                }
                QRadioButton::indicator:hover {
                    border: 2px solid #1b4f72;
                }
                QRadioButton::indicator:checked {
                    background: #000000;
                    border: 2px solid #4060b9;
                }
                QRadioButton::indicator:disabled {
                    background: #e5e7eb;
                    border: 2px solid #a6acaf;
                }

        /* ********************************* scroll ********************************* */
        QScrollArea {
            background: transparent;
            border: None;
        }

        QScrollBar:vertical {
            width: 10px;
            margin: 4px 4px 4px 2px;           /* top right bottom left */
            background: transparent;
        }
        QScrollBar::handle:vertical {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                        stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-height: 24px;
        }
        QScrollBar::handle:vertical:hover {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                        stop:0 #5499c7, stop:1 #21618c);
        }

        QScrollBar:horizontal {
            height: 10px;
            margin: 2px 4px 4px 4px;
            background: transparent;
        }
        QScrollBar::handle:horizontal {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0,
                                        stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-width: 24px;
        }
        QScrollBar::handle:horizontal:hover {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5499c7, stop:1 #21618c);
        }

        /* הסתרת כפתורי החיצים של הסקרולבר */
        QScrollBar::add-line, QScrollBar::sub-line {
            width: 0px; height: 0px;
            background: none;
            border: none;
        }
        QScrollBar::add-page, QScrollBar::sub-page {
            background: transparent;
        }

        #scrollContent {
            background: transparent;
            /* border-top: 1px solid #005aa3; */
            /* border-radius: 25px; */
            /* border-radius: 10px; */
            /* background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #0087f5, stop:1 #eff6fb); */
        }

        QLabel#scroll_label {
            font-size: 36px;
            font-weight: bold;
            color: black;
            padding: 4px 6px;
            border-radius: 30px;
            background: white;
            border: 1px solid #4559a1;
            min-height: 60px;
        }
        QLabel#scroll_label:hover {
            background: #d6eaf8;
        }
        
        QPushButton#btn_remove_move {
            border: 1px solid black;      /* עובי וצבע גבול */
            background-color: transparent;      /* צבע רקע */
            border: None;
            max-width: 10px;
        }
        QPushButton#btn_remove_move:hover {
            border: 1px solid #3498db;      /* עובי וצבע גבול */
            background-color: #f5f5f5;    /* רקע במעבר עכבר */
        }
        QPushButton#btn_remove_move:pressed {
            background-color: #e0e0e0;    /* רקע בלחיצה */
        }
        """

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== layouts =============== #
        root_layout = QVBoxLayout()     # panel layout
        name_layout = QHBoxLayout()     # name layout
        type_layout = QHBoxLayout()     # type radio layout
        main_layout = QHBoxLayout()     # main radio layout
        btn_layout = QHBoxLayout()      # button 'run' layout

        # =============== build layouts =============== #
        self._build_name_layout(name_layout)

        self._init_type_radio()
        self._build_type_radio_layout(type_layout)

        self._init_main_radio()
        self._build_main_radio_layout(main_layout)

        self._build_button_layout(btn_layout)

        # =============== style =============== #
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(root_style)

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()     # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")

        self.scroll_layout = QVBoxLayout()

        self.scroll_area.setWidget(self.scroll_content)   # set 'scroll_area' as father of 'scroll_content'
        self.scroll_content.setLayout(self.scroll_layout) # set the widget as the father of the layout

        self.phase_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout()] # list of layout that each one is a row in the scroll bar

        self.scroll_layout.addLayout(self.phase_rows[0])    # add row 1 to the layout
        self.scroll_layout.addLayout(self.phase_rows[1])    # add row 2 to the layout
        self.scroll_layout.addLayout(self.phase_rows[2])    # add row 3 to the layout
        self.scroll_layout.addStretch()                     # move all up

        #
        # for row in self.phase_rows: # add space between each element
        #     row.setSpacing(40)      # add space between each element

        # =============== create the layout =============== #
        root_layout.addLayout(name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(main_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(self.scroll_area)

        # root_layout.setContentsMargins(0, 0, 0, 0)  # left, top, right, bottom

        self.setLayout(root_layout)
        self.hide()

        # =============== scroll rows =============== #

    def show_panel(self):
        """
        Show the 'set move' panel.

        :return: None
        """
        self._show_scroll_bar()
        self.show()

    def _show_scroll_bar(self):
        """
        Show the scroll bar of the 'set move' panel.

        :return: None
        """
        moves_list = self.data_controller.get_all_moves() # get all moves

        # remove all widgets
        for row in self.phase_rows:

            # Warning: 'row.takeAt(0)' remove from first item in the 'row' list, I added just widgets so I just remove them, it someone will add in the future any layout they will be removed from 'row' but still exist in Qt and because it belong to Qt the garbage collector will not remove them
            while row.count():          # return how many 'QLayoutItem' exist in the list of 'QLayoutItem'
                item = row.takeAt(0)    # get the first QLayoutItem of the layout 'row' and remove him from the list of the 'QLayoutItem'
                widget = item.widget()  # get the widget
                if widget:  #
                    widget.deleteLater() # remove widget (when widget remove -> all his layouts and children removed also), safer not remove now so he will be removed safely later by Qt

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
            label.setText(f"{phase} <img src='{icon}' width='16' height='20'/> {"⭐" if move.is_main else ""}")
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            btn_remove = QPushButton("❌")
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.clicked.connect(lambda _=False, phase_clean=clean_text(phase): self._remove_move(phase_clean))

            item_layout = QVBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            container = QFrame()
            container.setLayout(item_layout)

            self.phase_rows[row_idx].addWidget(container)
        self.phase_rows[0].addStretch() # move the row left
        self.phase_rows[1].addStretch() # move the row left
        self.phase_rows[2].addStretch() # move the row left



    # =============== inner methods =============== #
    def _remove_move(self, move_name):
        """
        This method removes the move and refresh the scroll bar.

        :param move_name:
        :return:
        """
        if self.data_controller.remove_move(move_name):
            self._show_scroll_bar()
            Log.success("Removed successfully!")

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

    def _build_name_layout(self, layout):

        label = QLabel("שם המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        self.main_phase_textbox = QLineEdit()                                # create textbox
        self.main_phase_textbox.setObjectName("text_box")
        self.main_phase_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)    # center the text
        self.main_phase_textbox.setMaximumWidth(350)                         # set max-width
        self.main_phase_textbox.setFixedHeight(32)                           # set fixed height

        layout.addStretch()
        layout.addWidget(self.main_phase_textbox)
        layout.addWidget(label)
        layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom

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

    def _build_type_radio_layout(self, type_layout):
        # add radio to button
        type_layout.addStretch() # move the elements right
        type_layout.addWidget(self.traffic_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.traffic_flashing_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.pedestrian_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.blinker_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.blinker_conditional_radio)
        label = QLabel("סוג המופע")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        type_layout.addWidget(label)
        type_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _init_main_radio(self):
        self.main_radio = QRadioButton("כן")
        self.not_main_radio = QRadioButton("לא")
        self.main_radio.setChecked(True)  # checked is default

        # radio group
        main_group = QButtonGroup(self)
        main_group.addButton(self.not_main_radio)
        main_group.addButton(self.main_radio)

    def _build_main_radio_layout(self, layout):

        # add to main layout
        layout.addStretch() # move the elements right
        layout.addWidget(self.main_radio)
        layout.addSpacing(10)
        layout.addWidget(self.not_main_radio)
        layout.addSpacing(30)

        label = QLabel("מופע ראשי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        layout.addWidget(label)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _build_button_layout(self, layout):

        run_button = QPushButton("הוסף")                # create button
        # run_button.setStyleSheet(blue_button_white_text_style)
        run_button.clicked.connect(lambda: self._add_move(self.main_phase_textbox.text()))

        layout.addStretch()
        layout.addWidget(run_button)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom




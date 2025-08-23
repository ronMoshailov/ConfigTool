from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt6.QtWidgets import QRadioButton, QButtonGroup

from config.constants import GREEN_IMAGE_PATH, GREEN_BLINKER_IMAGE_PATH, PEDESTRIAN_IMAGE_PATH, BLINKER_IMAGE_PATH, \
    BLINKER_CONDITIONAL_IMAGE_PATH

from config.special import clean_text
from config.style import blue_button_white_text_style, dark_button_style
from controllers.data_controller import DataController
from entities.log import Log


class SetMovePanel(QWidget):

    def __init__(self):
        super().__init__()

        self.setObjectName("movePanel")

        self.data_controller = DataController()

        # =============== layouts =============== #
        root_layout = QVBoxLayout(self)     # root layout
        row1 = QHBoxLayout()                #
        type_radio_layout = QHBoxLayout()   # type radio layout
        main_radio_layout = QHBoxLayout()   # main radio layout
        row4 = QHBoxLayout()                #

        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)






        # =============== style =============== #
        # labels
        gray_label_style = """

        
            QLabel {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
                color: #2c3e50;
                border: 1px solid #d5d8dc;
                border-radius: 8px;
                padding: 8px 12px;
                font-size: 24px;
                font-weight: bold;
                min-width: 170px;
                }

            QLabel:hover {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
                border: 1px solid #b2babb;
                }
        """
        text_field_style = """
                    QLineEdit {
                        font-size: 32px;
                        max-height: 50px;
                        padding: 6px 10px;
                        border: 2px solid #cccccc;
                        border-radius: 6px;
                        background-color: #fdfdfd;
                        selection-background-color: #3399ff;
                    }
                    QLineEdit:focus {
                        border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
                        background-color: #ffffff;
                    }
                    QLineEdit:disabled {
                        background-color: #eeeeee;
                        color: #888888;
                    }
                """
        radio_tile_style = """
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
        """
        scroll_style = """
        QScrollArea {
            border: 1px solid #3498db;
            border-radius: 25px;
            padding: 10px;
            background-color: #4f57ff;
        }


        /* סרגל גלילה אנכי */
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

        /* סרגל גלילה אופקי */
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
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0,
                                        stop:0 #5499c7, stop:1 #21618c);
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

        /* רקע התוכן הפנימי */
        #scrollContent {
            background: #0000ff;
            border: 1px solid #c8d1dc;
            border-radius: 8px;
        }
        """

        # =============== labels =============== #
        #                      index 0             index 1              index 2
        label_list = [  QLabel("שם המופע"), QLabel("סוג המופע"), QLabel("מופע ראשי")]

        # set style
        self.setStyleSheet(gray_label_style + text_field_style + radio_tile_style + scroll_style)

        # center the text
        for lbl in label_list:
            lbl.setAlignment(Qt.AlignmentFlag.AlignCenter)  # both H & V

        # =============== textbox =============== #
        main_phase_textbox = QLineEdit()                                # create textbox
        main_phase_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)    # center the text
        main_phase_textbox.setMaximumWidth(350)                         # set max-width
        main_phase_textbox.setFixedHeight(32)                           # set fixed height

        # =============== button =============== #
        run_button = QPushButton("הוסף")                # create button
        run_button.setStyleSheet(blue_button_white_text_style + dark_button_style)
        run_button.clicked.connect(lambda: self.add_move(main_phase_textbox.text()))

        # =============== type move =============== #

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

        row1.addStretch()
        row1.addWidget(main_phase_textbox)
        row1.addWidget(label_list[0])

        type_radio_layout.addWidget(label_list[1])

        main_radio_layout.addWidget(label_list[2])

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
        # self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)

        for row in self.phase_rows: # add space between each element
            row.setSpacing(40)      # add space between each element

        # =============== layout =============== #

        # =============== create the layout =============== #
        root_layout.addLayout(row1)
        root_layout.addSpacing(30)
        root_layout.addLayout(type_radio_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(main_radio_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(row4)

        root_layout.addSpacing(50)
        root_layout.addWidget(self.scroll_area)

        self.setLayout(root_layout)
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

        scroll_label_style = """
                        QLabel {
                            font-size: 36px;
                            font-weight: bold;
                            color: #2c3e50;
                            padding: 4px 6px;
                            border-radius: 6px;
                            background: #cceeff;
                            border: 1px solid #d0d7de;
                            min-height: 60px;
                        }
                        QLabel:hover {
                            background: #d6eaf8;
                            border: 1px solid #3498db;
                        }
                    """
        btn_remove_style = """
                        QPushButton {
                            border: 1px solid black;      /* עובי וצבע גבול */
                            background-color: white;      /* צבע רקע */
                        }
                        QPushButton:hover {
                            border: 1px solid #3498db;      /* עובי וצבע גבול */
                            background-color: #f5f5f5;    /* רקע במעבר עכבר */
                        }
                        QPushButton:pressed {
                            background-color: #e0e0e0;    /* רקע בלחיצה */
                        }
                    """

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



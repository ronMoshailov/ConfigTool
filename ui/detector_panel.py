from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator, QFont
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QScrollArea
from PyQt6.QtWidgets import QRadioButton, QButtonGroup

from controllers.data_controller import DataController


class DetectorPanel(QWidget):
    """
        A panel widget that allows adding and removing move buttons.
    """

    def __init__(self):
        """
            Initialize the main window, create controllers, panels, and layout.
        """
        super().__init__()

        # =============== style =============== #
        root_style = """
        /* ********************************* panel ********************************* */
        #detectorPanel{
            border-radius: 20px;
            border: 1px solid #ff8c1a;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0   #fbc898, stop:1   #f0f8ff);
        }

        /* ********************************* gray label ********************************* */
        QLabel#gray_label {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
            color: #2c3e50;
            border: 1px solid #d5d8dc;
            border-radius: 8px;
            padding: 8px 12px;
            font-size: 24px;
            font-weight: bold;
            min-width: 170px;
        }

        QLabel#gray_label:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
            border: 1px solid #b2babb;
        }
        
        QLabel#little_title {
            color: #2c3e50;         /* צבע כהה ואלגנטי */
            font-weight: bold;      /* הדגשה */
            letter-spacing: 1px;    /* ריווח קטן בין אותיות */
            border-bottom: 1px solid #1a98ff;  /* קו דק מתחת */
            padding-bottom: 2px;    /* ריווח קטן מהקו */
        }

        /* ********************************* text field ********************************* */
        QLineEdit#text_box {
            font-size: 32px;
            max-height: 50px;
            padding: 6px 10px;
            border: 2px solid #cccccc;
            border-radius: 6px;
            background: #fdfdfd;
            selection-background: #3399ff;
        }
        QLineEdit#text_box:focus {
            border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
            background: #ffffff;
        }
        QLineEdit#text_box:disabled {
            background: #eeeeee;
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
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-height: 24px;
        }
        QScrollBar::handle:vertical:hover {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5499c7, stop:1 #21618c);
        }
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
            height: 0; /* remove the scrollbar buttons */
        } 


        QScrollBar:horizontal {
            height: 10px;
            margin: 2px 4px 4px 4px;
            background: transparent;
        }
        QScrollBar::handle:horizontal {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5dade2, stop:1 #2e86c1);
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
            min-width: 150px;
        }
        QLabel#scroll_label:hover {
            background: #d6eaf8;
        }

        QPushButton#btn_remove_move {
            background: transparent;
            border: None;

        }

        QPushButton#btn_remove_move:hover {
            border: 1px solid #3498db;      /* עובי וצבע גבול */
            background: #f5f5f5;    /* רקע במעבר עכבר */
        }
        QPushButton#btn_remove_move:pressed {
            background: #e0e0e0;    /* רקע בלחיצה */
        }

        /* ********************************* QPushButton ********************************* */
        QPushButton#add_button {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            color: white;
            border: 2px solid #2471a3;
            border-radius: 10px;
            padding: 0px;
            font-weight: bold;
            font-size: 18px;
            min-width: 150px;
            min-height: 36px;
        }

        QPushButton#add_button:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
            border: 2px solid #1b4f72;
        }

        QPushButton#add_button:checked {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #58d68d, stop:1 #28b463);
            border: 2px solid #239b56;
        }

        QPushButton#add_button:disabled {
            background: #d5d8dc;
            border: 2px solid #a6acaf;
            color: #7f8c8d;
        }
        """

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== layouts =============== #
        root_layout = QVBoxLayout()  # panel layout
        name_layout = QHBoxLayout()  # name layout
        type_layout = QHBoxLayout()  # type radio layout
        btn_layout = QHBoxLayout()   # button 'run' layout

        # =============== build layouts =============== #
        self._build_name_layout(name_layout)

        self._init_type_radio()
        self._build_type_radio_layout(type_layout)

        self._build_button_layout(btn_layout)

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()  # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")

        self.scroll_layout = QVBoxLayout()

        self.scroll_area.setWidget(self.scroll_content)  # set 'scroll_area' as father of 'scroll_content'
        self.scroll_content.setLayout(self.scroll_layout)  # set the widget as the father of the layout

        self.detector_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout(), QHBoxLayout(), QHBoxLayout()]  # list of layout that each one is a row in the scroll bar

        self.scroll_layout.addLayout(self.detector_rows[0])  # add row 1 to the layout
        self.scroll_layout.addLayout(self.detector_rows[1])  # add row 2 to the layout
        self.scroll_layout.addLayout(self.detector_rows[2])  # add row 2 to the layout
        self.scroll_layout.addLayout(self.detector_rows[3])  # add row 2 to the layout
        self.scroll_layout.addLayout(self.detector_rows[4])  # add row 2 to the layout
        self.scroll_layout.addStretch()  # move all up

        for row in self.detector_rows:  # add space between each element
            row.setSpacing(10)  # add space between each element

        # =============== create the layout =============== #
        root_layout.addLayout(name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(self.scroll_area)

        # =============== self =============== #
        self.setObjectName("detectorPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)  # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(root_style)
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
        detector_list = self.data_controller.get_all_detectors()  # get all moves

        # remove all widgets
        for row in self.detector_rows:
            # Warning: 'row.takeAt(0)' remove from first item in the 'row' list, I added just widgets so I just remove them, it someone will add in the future any layout they will be removed from 'row' but still exist in Qt and because it belong to Qt the garbage collector will not remove them
            while row.count():  # return how many 'QLayoutItem' exist in the list of 'QLayoutItem'
                item = row.takeAt(0)  # get the first QLayoutItem of the layout 'row' and remove him from the list of the 'QLayoutItem'
                widget = item.widget()  # get the widget
                if widget:  #
                    widget.deleteLater()  # remove widget (when widget remove -> all his layouts and children removed also), safer not remove now so he will be removed safely later by Qt

        # build the rows
        for detector in detector_list:
            name = detector.name
            type = detector.type

            if type == "DDetector":
                row_idx = 0
            elif type == "EDetector":
                row_idx = 1
            elif type == "TPDetector":
                row_idx = 2
            elif type == "DEDetector":
                row_idx = 3
            elif type == "QDetector":
                row_idx = 4
            else:
                self.data_controller.write_log(f"detector '{name}' is not supported", "r")
                continue

            # label
            label = QLabel()
            label.setText(name)
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            # remove button
            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(18, 18)
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.setCursor(Qt.CursorShape.PointingHandCursor)
            btn_remove.clicked.connect(lambda _=False, remove_name=name: self._remove_detector(remove_name))

            # vertical layout
            item_layout = QVBoxLayout()
            item_layout.addWidget(label)
            item_layout.addWidget(btn_remove)

            # container for the vertical layout
            container = QFrame()
            container.setLayout(item_layout)

            # add the container to the row
            self.detector_rows[row_idx].addWidget(container)

        # move the rows left
        self.detector_rows[0].addStretch()
        self.detector_rows[1].addStretch()
        self.detector_rows[2].addStretch()
        self.detector_rows[3].addStretch()
        self.detector_rows[4].addStretch()

    # =============== inner methods =============== #
    def _remove_detector(self, detector_name):
        """
        This method removes the move and refresh the scroll bar.

        :param move_name:
        :return:
        """
        if self.data_controller.remove_detector(detector_name):
            self._show_scroll_bar()
            self.data_controller.write_log("Removed successfully", "r")

    def _add_detector(self, name:str, ext_time:int = None):
        if self.demand_radio.isChecked():
            move_type = "DDetector"
            detector_name = "d_" + name
        elif self.ext_radio.isChecked():
            move_type = "EDetector"
            detector_name = "e_" + name
        elif self.pedestrian_radio.isChecked():
            move_type = "TPDetector"
            detector_name = "tp_" + name
        elif self.demand_ext_radio.isChecked():
            move_type = "DEDetector"
            detector_name = "de_" + name
        elif self.queue_radio.isChecked():
            move_type = "QDetector"
            detector_name = "q_" + name
        else:
            self.data_controller.write_log(f"detector_{name} is not supported", "r")
            return False

        if self.data_controller.add_detector(detector_name, move_type, ext_time):
            self._show_scroll_bar()
            return True
        return False

    def _build_name_layout(self, layout):
        # label
        label = QLabel("גלאי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)

        font = QFont("Segoe UI Semibold")  # גופן אלגנטי יותר
        font.setPointSize(10)  # שומר על אותו גודל (אפשר להתאים למה שיש לך כבר)

        label_name = QLabel("שם")
        label_name.setFont(font)
        label_name.setObjectName("little_title")
        label_name.setAlignment(Qt.AlignmentFlag.AlignCenter)

        label_ext_unit = QLabel("יחידת הארכה")
        label_ext_unit.setFont(font)
        label_ext_unit.setObjectName("little_title")
        label_ext_unit.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # layout
        name_layout = QVBoxLayout()
        ext_unit_layout = QVBoxLayout()

        # create textbox
        self.name_textbox = QLineEdit()
        self.name_textbox.setObjectName("text_box")
        self.name_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)  # center the text
        self.name_textbox.setMaximumWidth(350)  # set max-width
        self.name_textbox.setFixedHeight(32)  # set fixed height

        self.ext_unit_textbox = QLineEdit()
        self.ext_unit_textbox.setObjectName("text_box")
        self.ext_unit_textbox.setAlignment(Qt.AlignmentFlag.AlignRight)  # center the text
        self.ext_unit_textbox.setMaximumWidth(350)  # set max-width
        self.ext_unit_textbox.setFixedHeight(32)  # set fixed height
        self.ext_unit_textbox.setValidator(QIntValidator(0, 99))

        # build layout
        name_layout.addWidget(label_name)
        name_layout.addWidget(self.name_textbox)

        ext_unit_layout.addWidget(label_ext_unit)
        ext_unit_layout.addWidget(self.ext_unit_textbox)

        layout.addStretch()
        layout.addLayout(ext_unit_layout)
        layout.addLayout(name_layout)
        layout.addSpacing(15)
        layout.addWidget(label)
        layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom


    def _init_type_radio(self):
        self.demand_radio = QRadioButton()  # create radio button
        self.demand_radio.setText("דרישה")
        self.demand_radio.setChecked(True)  # default checked
        self.demand_radio.setLayoutDirection(Qt.LayoutDirection.LeftToRight)

        self.ext_radio = QRadioButton()  # create radio button
        self.ext_radio.setText("הארכה")
        self.ext_radio.setLayoutDirection(Qt.LayoutDirection.LeftToRight)

        self.demand_ext_radio = QRadioButton()  # create radio button
        self.demand_ext_radio.setText("הולכי רגל")
        self.demand_ext_radio.setLayoutDirection(Qt.LayoutDirection.LeftToRight)

        self.queue_radio = QRadioButton()  # create radio button
        self.queue_radio.setText("דרישה + הארכה")
        self.queue_radio.setLayoutDirection(Qt.LayoutDirection.LeftToRight)

        self.pedestrian_radio = QRadioButton()  # create radio button
        self.pedestrian_radio.setText("תור")
        self.pedestrian_radio.setLayoutDirection(Qt.LayoutDirection.LeftToRight)

        # create radio button group
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.demand_radio)
        type_radio_group.addButton(self.ext_radio)
        type_radio_group.addButton(self.demand_ext_radio)
        type_radio_group.addButton(self.queue_radio)
        type_radio_group.addButton(self.pedestrian_radio)

    def _build_type_radio_layout(self, type_layout):
        # add radio to button
        type_layout.addStretch()  # move the elements right
        type_layout.addWidget(self.pedestrian_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.queue_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.demand_ext_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.ext_radio)
        type_layout.addSpacing(20)
        type_layout.addWidget(self.demand_radio)
        type_layout.addSpacing(20)

        label = QLabel("סוג הגלאי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        type_layout.addWidget(label)
        type_layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

    def _build_button_layout(self, layout):
        run_button = QPushButton("הוסף")  # create button
        run_button.setObjectName("add_button")
        ext_unit = self.ext_unit_textbox.text()
        run_button.clicked.connect(lambda: self._add_detector(self.name_textbox.text(), None if ext_unit == "" else int(ext_unit)))

        layout.addStretch()
        layout.addWidget(run_button)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom




from PyQt6.QtCore import Qt
from PyQt6.QtGui import QFont, QIntValidator
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QScrollArea, QPushButton, QLabel, QRadioButton, \
    QLineEdit, QButtonGroup, QFrame

from Config.special import clear_widget_from_layout
from Config.style import detector_panel_style


class DetectorView(QWidget):
    def __init__(self):
        super().__init__()

        # =============== Controller Methods =============== #
        self.add_detector_method = None
        self.remove_detector_method = None

        # =============== Name =============== #
        name_layout = self._build_name_layout()

        # =============== Type =============== #
        self._init_type_radio()
        type_layout = self._build_type_radio_layout()

        # =============== Button Layout =============== #
        btn_layout = self._build_button_layout()

        # =============== scroll =============== #
        self.scroll_layout = QVBoxLayout()

        self.scroll_area = self.init_scroll(self.scroll_layout)

        self.detector_rows = [QHBoxLayout(), QHBoxLayout(), QHBoxLayout(), QHBoxLayout(), QHBoxLayout()]  # list of layout that each one is a row in the scroll bar
        for layout in self.detector_rows:
            self.scroll_layout.addLayout(layout)  # add row i to the layout
        self.scroll_layout.addStretch()  # move all up

        for row in self.detector_rows:  # add space between each element
            row.setSpacing(10)  # add space between each element

        # =============== create the layout =============== #
        root_layout = QVBoxLayout()  # panel layout
        root_layout.addLayout(name_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(type_layout)
        root_layout.addSpacing(30)
        root_layout.addLayout(btn_layout)
        root_layout.addSpacing(50)
        root_layout.addWidget(self.scroll_area)

        # =============== Self =============== #
        self.setObjectName("detectorPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)  # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.setStyleSheet(detector_panel_style)
        self.setLayout(root_layout)
        self.hide()

    def init_scroll(self, scroll_layout):
        """
        This method initializes the scroll area.

        :param scroll_layout: Layout of the scroll area.
        :return: QScrollArea
        """
        scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        scroll_area.setWidgetResizable(True)   # make the scroll area resizable
        scroll_area.setObjectName("scroll_area")  #

        scroll_content = QWidget()                     # create the widget that will be in the layout.
        scroll_content.setObjectName("scrollContent")  #

        scroll_area.setWidget(scroll_content)     # set 'scroll_area' as father of 'scroll_content'
        scroll_content.setLayout(scroll_layout)        # set the widget as the father of the layout

        return scroll_area

    def show_view(self, detector_list):
        """
        Show the 'set move' panel.

        :return: None
        """
        self._show_scroll_bar(detector_list)
        self.show()

    def hide_view(self):
        self.hide()

    # =============== Init methods =============== #
    def _show_scroll_bar(self, detector_list):
        """
        Show the scroll bar of the 'set move' panel.

        :return: None
        """
        # remove all widgets
        clear_widget_from_layout(self.detector_rows)

        # build the rows
        for detector in detector_list:
            name = detector.name
            ext_unit = detector.ext_unit

            if detector.type == "DDetector":
                row_idx = 0
            elif detector.type == "EDetector":
                row_idx = 1
            elif detector.type == "TPDetector":
                row_idx = 2
            elif detector.type == "DEDetector":
                row_idx = 3
            elif detector.type == "QDetector":
                row_idx = 4

            # label
            label = QLabel()
            label.setText(name)
            # label.setText(f"{name}({ext_unit})")
            label.setObjectName("scroll_label")
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            label.setFixedHeight(30)

            # remove button
            btn_remove = QPushButton("❌")
            btn_remove.setFixedSize(18, 18)
            btn_remove.setObjectName("btn_remove_move")
            btn_remove.setCursor(Qt.CursorShape.PointingHandCursor)
            btn_remove.clicked.connect(lambda _=False, remove_name=name: self.remove_detector_method(remove_name))

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
        for row in self.detector_rows:
            row.addStretch()

    def _init_type_radio(self):
        self.demand_radio = QRadioButton()
        self.demand_radio.setText("דרישה")
        self.demand_radio.setChecked(True)  # default checked
        self.demand_radio.setLayoutDirection(Qt.LayoutDirection.RightToLeft)

        self.ext_radio = QRadioButton()
        self.ext_radio.setText("הארכה")
        self.ext_radio.setLayoutDirection(Qt.LayoutDirection.RightToLeft)

        self.pedestrian_radio = QRadioButton()
        self.pedestrian_radio.setText("הולכי רגל")
        self.pedestrian_radio.setLayoutDirection(Qt.LayoutDirection.RightToLeft)

        self.demand_ext_radio = QRadioButton()
        self.demand_ext_radio.setText("דרישה + הארכה")
        self.demand_ext_radio.setLayoutDirection(Qt.LayoutDirection.RightToLeft)

        self.queue_radio = QRadioButton()
        self.queue_radio.setText("תור")
        self.queue_radio.setLayoutDirection(Qt.LayoutDirection.RightToLeft)

        # create radio button group
        type_radio_group = QButtonGroup(self)
        type_radio_group.addButton(self.demand_radio)
        type_radio_group.addButton(self.ext_radio)
        type_radio_group.addButton(self.demand_ext_radio)
        type_radio_group.addButton(self.queue_radio)
        type_radio_group.addButton(self.pedestrian_radio)

    def _build_type_radio_layout(self):

        layout = QHBoxLayout()  # type radio layout

        # add radio to button
        layout.addStretch()  # move the elements right
        layout.addWidget(self.queue_radio)
        layout.addSpacing(20)
        layout.addWidget(self.demand_ext_radio)
        layout.addSpacing(20)
        layout.addWidget(self.pedestrian_radio)
        layout.addSpacing(20)
        layout.addWidget(self.ext_radio)
        layout.addSpacing(20)
        layout.addWidget(self.demand_radio)
        layout.addSpacing(20)

        label = QLabel("סוג הגלאי")
        label.setObjectName("gray_label")
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)  # center the text (both H & V)
        layout.addWidget(label)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

        return layout

    def _build_button_layout(self):
        run_button = QPushButton("הוסף")  # create button
        run_button.setObjectName("add_button")
        ext_unit = self.ext_unit_textbox.text()
        run_button.clicked.connect(lambda: self.add_detector_method(self.name_textbox.text(), self.get_selected_detector_type() ,None if ext_unit == "" else int(ext_unit)))

        layout = QHBoxLayout()

        layout.addStretch()
        layout.addWidget(run_button)
        layout.setContentsMargins(0, 0, 40, 0)  # left, top, right, bottom

        return layout

    def _build_name_layout(self):
        # =============== Label =============== #
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

        layout = QHBoxLayout()

        layout.addStretch()
        layout.addLayout(ext_unit_layout)
        layout.addLayout(name_layout)
        layout.addSpacing(15)
        layout.addWidget(label)
        layout.setContentsMargins(0, 40, 40, 0)  # left, top, right, bottom

        return layout

    def get_selected_detector_type(self):
        if self.demand_radio.isChecked():
            return "DDetector"
        elif self.ext_radio.isChecked():
            return "EDetector"
        elif self.pedestrian_radio.isChecked():
            return "TPDetector"
        elif self.demand_ext_radio.isChecked():
            return "DEDetector"
        elif self.queue_radio.isChecked():
            return "QDetector"

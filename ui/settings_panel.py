from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy, \
    QGridLayout

from config.style import min_green_panel_style, settings_panel_style
from controllers.data_controller import DataController


class SettingsPanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== Layout =============== #
        root_layout             = QHBoxLayout()
        left_side_layout        = QVBoxLayout()
        right_side_layout       = QVBoxLayout()
        name_layout             = QHBoxLayout()
        junc_layout             = QHBoxLayout()
        junction_name_layout    = QHBoxLayout()
        version_layout          = QHBoxLayout()
        first_cycle_ext_layout  = QHBoxLayout()
        date_layout             = QHBoxLayout()

        # =============== QPushButton =============== #
        master_btn      = QPushButton("מאסטר")
        master_btn.clicked.connect(lambda: self.change_state(slave_btn))
        master_btn.setProperty("class", "settings_button")

        slave_btn       = QPushButton("סלייב")
        slave_btn.clicked.connect(lambda: self.change_state(master_btn))
        slave_btn.setProperty("class", "settings_button")

        dx_btn          = QPushButton("dx")
        dx_btn.setProperty("class", "settings_button")

        new_node_btn    = QPushButton("צומת חדש")
        new_node_btn.setProperty("class", "settings_button")

        # =============== QLabel =============== #
        name_label             = QLabel("שם")
        name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        junc_label             = QLabel("מספר צומת")
        junc_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        junction_name_label    = QLabel("שם צומת")
        junction_name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        version_label          = QLabel("גרסה")
        version_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        first_cycle_ext_label  = QLabel("הארכה ראשונית")
        first_cycle_ext_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        date_label             = QLabel("תאריך")
        date_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # =============== QLineEdit =============== #
        self.name_textbox             = QLineEdit()
        self.junc_textbox             = QLineEdit()
        self.junction_name_textbox    = QLineEdit()
        self.version_textbox          = QLineEdit()
        self.first_cycle_ext_textbox  = QLineEdit()
        self.date_textbox             = QLineEdit()

        # build right side
        name_layout.addStretch()
        name_layout.addWidget(self.name_textbox)
        name_layout.addWidget(name_label)

        junc_layout.addStretch()
        junc_layout.addWidget(self.junc_textbox)
        junc_layout.addWidget(junc_label)

        junction_name_layout.addStretch()
        junction_name_layout.addWidget(self.junction_name_textbox)
        junction_name_layout.addWidget(junction_name_label)

        version_layout.addStretch()
        version_layout.addWidget(self.version_textbox)
        version_layout.addWidget(version_label)

        first_cycle_ext_layout.addStretch()
        first_cycle_ext_layout.addWidget(self.first_cycle_ext_textbox)
        first_cycle_ext_layout.addWidget(first_cycle_ext_label)

        date_layout.addStretch()
        date_layout.addWidget(self.date_textbox)
        date_layout.addWidget(date_label)

        right_side_layout.addSpacing(10)
        right_side_layout.addLayout(name_layout)
        right_side_layout.addSpacing(30)
        right_side_layout.addLayout(junc_layout)
        right_side_layout.addLayout(junction_name_layout)
        right_side_layout.addLayout(version_layout)
        right_side_layout.addLayout(date_layout)
        right_side_layout.addSpacing(30)
        right_side_layout.addLayout(first_cycle_ext_layout)
        right_side_layout.addStretch()

        # build left side
        left_side_layout.addWidget(master_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(slave_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(dx_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(new_node_btn)
        left_side_layout.addStretch()

        root_layout.addLayout(left_side_layout)
        root_layout.addLayout(right_side_layout)

        self._make_checkable([master_btn, slave_btn, dx_btn, new_node_btn])
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(settings_panel_style)
        self.hide()


    def show_panel(self):

        self.name_textbox.setText(self.data_controller.data_manager.lastVersionAuthor)

        self.junc_textbox.setText(self.data_controller.data_manager.anlagenName)
        self.junction_name_textbox.setText(self.data_controller.data_manager.tk1Name)
        self.version_textbox.setText(self.data_controller.data_manager.version)
        self.date_textbox.setText(self.data_controller.data_manager.lastVersionDate)
        self.first_cycle_ext_textbox.setText(self.data_controller.data_manager.first_time_ext)

        self.show()

    def _make_checkable(self, btn_list):
        """
        This method make buttons checkable.

        :return: None
        """
        for btn in btn_list:
            btn.setCheckable(True)

    def change_state(self, btn):
        selected_btn = self.sender()  # מחזיר את ה־QPushButton שירה את הסיגנל

        if selected_btn.isChecked():
            btn.setDisabled(True)
        else:
            btn.setDisabled(False)

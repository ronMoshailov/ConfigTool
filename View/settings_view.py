from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QPushButton, QLabel, QLineEdit, QVBoxLayout

from Config.style import settings_panel_style


class SettingsView(QWidget):

    def __init__(self):
        super().__init__()

        # =============== Controller Methods =============== #
        self.update_settings_method = None
        self.change_state_method = None
        self.make_checkable_method = None

        # =============== Name =============== #
        name_label = QLabel("שם")
        name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.name_textbox = QLineEdit()

        name_layout = QHBoxLayout()
        name_layout.addStretch()
        name_layout.addWidget(self.name_textbox)
        name_layout.addWidget(name_label)

        # =============== junction Number =============== #
        junc_label = QLabel("מספר צומת")
        junc_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.junc_textbox = QLineEdit()

        junc_layout = QHBoxLayout()
        junc_layout.addStretch()
        junc_layout.addWidget(self.junc_textbox)
        junc_layout.addWidget(junc_label)

        # =============== junction Name =============== #
        junction_name_label = QLabel("שם צומת")
        junction_name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.junction_name_textbox = QLineEdit()

        junction_name_layout = QHBoxLayout()
        junction_name_layout.addStretch()
        junction_name_layout.addWidget(self.junction_name_textbox)
        junction_name_layout.addWidget(junction_name_label)

        # =============== Version =============== #
        version_label = QLabel("גרסה")
        version_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.version_textbox = QLineEdit()

        version_layout = QHBoxLayout()
        version_layout.addStretch()
        version_layout.addWidget(self.version_textbox)
        version_layout.addWidget(version_label)

        # =============== First Cycle Extension =============== #
        first_cycle_ext_label = QLabel("הארכה ראשונית")
        first_cycle_ext_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.first_cycle_ext_textbox = QLineEdit()

        first_cycle_ext_layout = QHBoxLayout()
        first_cycle_ext_layout.addStretch()
        first_cycle_ext_layout.addWidget(self.first_cycle_ext_textbox)
        first_cycle_ext_layout.addWidget(first_cycle_ext_label)

        # =============== Date =============== #
        date_label = QLabel("תאריך")
        date_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.date_textbox = QLineEdit()

        date_layout = QHBoxLayout()
        date_layout.addStretch()
        date_layout.addWidget(self.date_textbox)
        date_layout.addWidget(date_label)

        # =============== Right Layout =============== #
        right_side_layout = QVBoxLayout()
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

        update_btn = QPushButton("שמור")
        update_btn.setProperty("class", "settings_button")
        update_btn.clicked.connect(lambda: self.update_settings_method(self.name_textbox.text(), self.junc_textbox.text(), self.junction_name_textbox.text(), self.version_textbox.text(), self.date_textbox.text(), self.first_cycle_ext_textbox.text()))

        right_side_layout.addWidget(update_btn)

        # =============== Left Layout =============== #
        self.master_btn = QPushButton("מאסטר")
        self.master_btn.clicked.connect(lambda: self.change_state_method(self.slave_btn))
        self.master_btn.setProperty("class", "settings_button")

        self.slave_btn = QPushButton("סלייב")
        self.slave_btn.clicked.connect(lambda: self.change_state_method(self.master_btn))
        self.slave_btn.setProperty("class", "settings_button")

        self.dx_btn = QPushButton("dx")
        self.dx_btn.setProperty("class", "settings_button")

        self.new_node_btn = QPushButton("צומת חדש")
        self.new_node_btn.setProperty("class", "settings_button")

        left_side_layout = QVBoxLayout()
        left_side_layout.addWidget(self.master_btn)
        left_side_layout.addWidget(self.slave_btn)
        left_side_layout.addWidget(self.dx_btn)
        left_side_layout.addWidget(self.new_node_btn)
        left_side_layout.addStretch()

        # =============== Layout =============== #
        root_layout = QHBoxLayout()
        root_layout.addLayout(left_side_layout)
        root_layout.addLayout(right_side_layout)

        # =============== Self =============== #
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(settings_panel_style)
        self.hide()

    def show_view(self, programmer_name, junction_num, junction_name, version, date, first_ext):
        self.name_textbox.setText(programmer_name)
        self.junc_textbox.setText(junction_num)
        self.junction_name_textbox.setText(junction_name)
        self.version_textbox.setText(version)
        self.date_textbox.setText(date)
        self.first_cycle_ext_textbox.setText(first_ext)
        self.make_checkable_method([self.master_btn, self.slave_btn, self.dx_btn, self.new_node_btn])
        self.show()

    def hide_view(self):
        self.hide()


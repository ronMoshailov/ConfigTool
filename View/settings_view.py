from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QPushButton, QLabel, QLineEdit, QVBoxLayout, QListWidget, \
    QListWidgetItem

import Config

class SettingsView(QWidget):

    def __init__(self):
        super().__init__()

        # Controller Methods
        self.update_junction_number_method = None
        self.update_junction_name_method = None
        self.update_version_method = None
        self.update_first_cycle_ext_method = None

        # Root Layouts
        root_layout = QVBoxLayout()
        content_layout = QHBoxLayout()
        left_side_layout = QVBoxLayout()
        right_side_layout = QVBoxLayout()

        content_layout.addLayout(left_side_layout)
        content_layout.addLayout(right_side_layout)

        # =========================== Components ============================ #

        # Title
        title = QLabel("Tel Aviv Version")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)
        title.setObjectName("Title")

        # List
        history_title = QLabel("היסטוריה")
        history_title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        self.list_widget = QListWidget()
        self.list_widget.itemDoubleClicked.connect(self.remove_item_from_list)  # לחיצה כפולה מסירה פריט

        # Textbox
        self.junc_textbox, junc_layout                          = self.create_labeled_line_edit("מספר צומת")       # junction Number
        self.name_textbox, name_layout                          = self.create_labeled_line_edit("שם")              # Name
        self.junction_name_textbox, junction_name_layout        = self.create_labeled_line_edit("שם צומת")         # Junction Number
        self.version_textbox, version_layout                    = self.create_labeled_line_edit("גרסה")            # Version
        self.first_cycle_ext_textbox, first_cycle_ext_layout    = self.create_labeled_line_edit("הארכה ראשונית")   # First Cycle Extension
        self.date_textbox, date_layout                          = self.create_labeled_line_edit("תאריך")           # Date

        self.junc_textbox.editingFinished.connect(lambda: self.update_junction_number_method(self.junc_textbox.text()))
        self.junction_name_textbox.editingFinished.connect(lambda: self.update_junction_name_method(self.junction_name_textbox.text()))
        self.version_textbox.editingFinished.connect(lambda: self.update_version_method(self.version_textbox.text()))
        self.first_cycle_ext_textbox.editingFinished.connect(lambda: self.update_first_cycle_ext_method(self.first_cycle_ext_textbox.text()))

        # Update Button
        update_btn = QPushButton("הוסף")
        update_btn.setProperty("class", "settings_button")
        update_btn.clicked.connect(self.add_item)

        # =========================== Set View ============================ #

        # Left Side Layout
        left_side_layout.addWidget(history_title)
        left_side_layout.addWidget(self.list_widget)

        # Right Side Layout
        right_side_layout.addLayout(junc_layout)
        right_side_layout.addLayout(junction_name_layout)
        right_side_layout.addLayout(version_layout)
        right_side_layout.addLayout(first_cycle_ext_layout)
        right_side_layout.addStretch()
        right_side_layout.addLayout(name_layout)
        right_side_layout.addLayout(date_layout)
        right_side_layout.addWidget(update_btn)



        # =============== Layout =============== #
        root_layout.addWidget(title)
        root_layout.addLayout(content_layout)
        # root_layout.addWidget(update_btn)

        # =============== Self =============== #
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(Config.style.settings_panel_style)
        self.hide()

    def show_view(self, junction_num, junction_name, version, first_ext, history):
        self.junc_textbox.setText(junction_num)
        self.junction_name_textbox.setText(junction_name)
        self.version_textbox.setText(version)
        self.first_cycle_ext_textbox.setText(first_ext)

        for date, author in history:
            text = f"{date} - {author}"
            item = QListWidgetItem(text)
            self.list_widget.addItem(item)

        self.show()

    def hide_view(self):
        self.hide()

    def create_labeled_line_edit(self, label_text):
        # Label
        label = QLabel(label_text)
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # Textbox
        textbox = QLineEdit()

        # Layout
        layout = QHBoxLayout()
        layout.addWidget(textbox)
        layout.addWidget(label)

        return textbox, layout

    def remove_item_from_list(self, item: QListWidgetItem):
        row = self.list_widget.row(item)
        self.list_widget.takeItem(row)

    def add_item(self, _):
        text = f"{self.date_textbox.text()} - {self.name_textbox.text()}"
        item = QListWidgetItem(text)
        self.list_widget.addItem(item)



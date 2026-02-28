from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QPushButton, QLabel, QLineEdit, QVBoxLayout, QListWidget, \
    QListWidgetItem, QMessageBox

import Config

class SettingsView(QWidget):

    def __init__(self):
        super().__init__()

        # Controller Methods
        self.update_junction_number_method  = None
        self.update_junction_name_method    = None
        self.update_version_method          = None
        self.update_first_cycle_ext_method  = None
        self.push_to_history_method         = None
        self.remove_from_history_method     = None

        # Root Layouts
        root_layout         = QVBoxLayout()
        content_layout      = QHBoxLayout()
        history_list_layout = QVBoxLayout()
        settings_layout     = QVBoxLayout()

        # Content Layout
        content_layout.addLayout(history_list_layout)
        content_layout.addLayout(settings_layout)

        # Title
        title = QLabel("Tel Aviv Version")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)
        title.setObjectName("Title")

        # List
        history_title = QLabel("היסטוריה")
        history_title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # QListWidget
        self.list_widget = QListWidget()
        self.list_widget.itemDoubleClicked.connect(self._remove_item_from_list)

        # Textbox
        self.junc_textbox, junc_layout                          = self._create_labeled_line_edit("מספר צומת")       # Junction Number
        self.junction_name_textbox, junction_name_layout        = self._create_labeled_line_edit("שם צומת")         # Junction Name
        self.version_textbox, version_layout                    = self._create_labeled_line_edit("גרסה")            # Version
        self.first_cycle_ext_textbox, first_cycle_ext_layout    = self._create_labeled_line_edit("הארכה ראשונית")   # First Cycle Extension
        self.name_textbox, name_layout                          = self._create_labeled_line_edit("שם")              # Name
        self.date_textbox, date_layout                          = self._create_labeled_line_edit("תאריך")           # Date

        # Connect lambda
        self.junc_textbox.editingFinished.connect(self._on_change_junction_number)
        self.junction_name_textbox.editingFinished.connect(self._on_change_junction_name)
        self.version_textbox.editingFinished.connect(self._on_change_version)
        self.first_cycle_ext_textbox.editingFinished.connect(self._on_change_first_cycle_ext)

        # Update Button
        update_btn = QPushButton("הוסף")
        update_btn.setProperty("class", "settings_button")
        update_btn.clicked.connect(self._add_to_history)

        # History List Layout
        history_list_layout.addWidget(history_title)
        history_list_layout.addWidget(self.list_widget)

        # Settings Layout
        settings_layout.addLayout(junc_layout)
        settings_layout.addLayout(junction_name_layout)
        settings_layout.addLayout(version_layout)
        settings_layout.addLayout(first_cycle_ext_layout)
        settings_layout.addStretch()
        settings_layout.addLayout(name_layout)
        settings_layout.addLayout(date_layout)
        settings_layout.addWidget(update_btn)

        # Root Layout
        root_layout.addWidget(title)
        root_layout.addLayout(content_layout)

        # Self
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(Config.style.settings_panel_style)
        self.hide()

    def show_view(self, junction_num, junction_name, version, first_ext, history):
        # Clear
        self.list_widget.clear()

        # Update data from DB to view
        self.junc_textbox.setText(junction_num)
        self.junction_name_textbox.setText(junction_name)
        self.version_textbox.setText(version)
        self.first_cycle_ext_textbox.setText(first_ext)

        # Insert data to the list
        for date, author in history:
            text = f"{date} - {author}"
            item = QListWidgetItem(text)
            self.list_widget.addItem(item)

        # Show view
        self.show()

    def hide_view(self):
        self.hide()

    # ============================== Layout ============================== #
    def _create_labeled_line_edit(self, label_text):
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

    # ============================== CRUD ============================== #
    def _remove_item_from_list(self, item: QListWidgetItem):
        value = item.text()
        date, author = value.split(" - ", 1)
        self.remove_from_history_method(date, author)

    def _add_to_history(self):
        self.push_to_history_method(self.date_textbox.text(), self.name_textbox.text())
        self.name_textbox.clear()
        self.date_textbox.clear()

    def _on_change_junction_number(self):
        self.update_junction_number_method(self.junc_textbox.text())

    def _on_change_junction_name(self):
        self.update_junction_name_method(self.junction_name_textbox.text())

    def _on_change_version(self):
        self.update_version_method(self.version_textbox.text())

    def _on_change_first_cycle_ext(self):
        self.update_first_cycle_ext_method(self.first_cycle_ext_textbox.text())

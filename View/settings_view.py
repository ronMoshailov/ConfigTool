from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QHBoxLayout, QLabel, QLineEdit, QVBoxLayout, QListWidget, QListWidgetItem
from Config.variables import Var

import Config
from View.base_view import BaseView


class SettingsView(BaseView):
    """
    SettingsView manages the settings of the app.

    Layout:
    The View have 2 sides.
    Left Side - This side manages the information about previous programmers.
    Right Side - This side manages the general settings (Node name, Node Number, etc.).
    """
    def __init__(self):
        super().__init__()

        # ========== Controller Methods ========== #
        self.update_junction_number_method  = None
        self.update_junction_name_method    = None
        self.update_version_method          = None
        self.update_first_cycle_ext_method  = None
        self.push_to_history_method         = None
        self.remove_from_history_method     = None
        self.get_date_method                = None

        # ========== Build Layout ========== #
        history_list_layout     = self._build_history_layout()
        settings_layout         = self._build_settings_layout()
        root_layout             = self._build_root_layout([history_list_layout, settings_layout])

        # ========== Self ========== #
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(Config.style.settings_panel_style)
        self.hide()

    def show_view(self, junction_name_num, junction_name, version, first_ext, history):
        # Clear
        self.history_list.clear()

        # Update data from DB to view
        self.junc_textbox.setText(junction_name_num)
        self.junction_name_textbox.setText(junction_name)
        self.version_textbox.setText(version)
        self.first_cycle_ext_textbox.setText(first_ext)

        # Insert data to the list
        for date, author in history:
            text = f"{date} - {author}"
            item = QListWidgetItem(text)
            self.history_list.addItem(item)

        # Show view
        self.show()

    # ============================== Layout ============================== #
    def _build_history_layout(self):
        """
        This method build the left side of the view.
        The left side manages the history.
        """
        # ========== History Layout ========== #
        history_list_layout = QVBoxLayout()

        # title
        title = self.create_centered_label("היסטוריה")

        # List
        self.history_list = QListWidget()
        self.history_list.itemDoubleClicked.connect(self._remove_item_from_list)

        # Programmer Name
        self.name_textbox = QLineEdit()

        combo = self.create_combo(list(Var.EMPLOYEES.keys()), self._on_change_programmer)

        name_layout = QHBoxLayout()
        name_layout.addWidget(combo)
        name_layout.addWidget(self.name_textbox)
        name_layout.addWidget(self.create_centered_label("שם"))

        # Date
        self.date_textbox = QLineEdit()

        date_btn = self.create_button("רשום תאריך", self._on_write_date, property="settings_button")

        date_layout = QHBoxLayout()
        date_layout.addWidget(date_btn)
        date_layout.addWidget(self.date_textbox)
        date_layout.addWidget(self.create_centered_label("תאריך"))

        # "Add To List" Button
        add_btn = self.create_button("הוסף", self._add_to_history, property="settings_button")

        # Connect History Layout
        history_list_layout.addWidget(title)
        history_list_layout.addWidget(self.history_list)
        history_list_layout.addLayout(name_layout)
        history_list_layout.addLayout(date_layout)
        history_list_layout.addWidget(add_btn)

        return history_list_layout

    def _build_settings_layout(self):
        """
        This method build the right side of the view.
        The right side manages the settings of the app.
        """
        # ========== Settings Layout ========== #
        settings_layout     = QVBoxLayout()

        # Textbox
        self.junc_textbox, junc_layout                          = self._create_labeled_line_edit("מספר צומת")       # Junction Number
        self.junction_name_textbox, junction_name_layout        = self._create_labeled_line_edit("שם צומת")         # Junction Name
        self.version_textbox, version_layout                    = self._create_labeled_line_edit("גרסה")            # Version
        self.first_cycle_ext_textbox, first_cycle_ext_layout    = self._create_labeled_line_edit("הארכה ראשונית")   # First Cycle Extension

        # Connect lambda
        self.junc_textbox.editingFinished.connect(self._on_change_junction_number)
        self.junction_name_textbox.editingFinished.connect(self._on_change_junction_name)
        self.version_textbox.editingFinished.connect(self._on_change_version)
        self.first_cycle_ext_textbox.editingFinished.connect(self._on_change_first_cycle_ext)

        # Settings Layout
        settings_layout.addLayout(junc_layout)
        settings_layout.addLayout(junction_name_layout)
        settings_layout.addLayout(version_layout)
        settings_layout.addLayout(first_cycle_ext_layout)
        settings_layout.addStretch()

        return settings_layout

    def _build_root_layout(self, layout_list):
        # ========== Root Layout ========== #
        root_layout = QVBoxLayout()

        # Title
        title = self.create_centered_label("Tel Aviv Version", "Title")

        # ========== Content Layout ========== #
        content_layout      = QHBoxLayout()
        for layout in layout_list:
            content_layout.addLayout(layout)

        # Root Layout
        root_layout.addWidget(title)
        root_layout.addLayout(content_layout)

        return root_layout

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

    def _on_change_programmer(self, text):
        if text == "-":
            return
        self.name_textbox.setText(Var.EMPLOYEES[text])
        self._on_write_date()

    def _on_write_date(self):
        date = self.get_date_method()
        self.date_textbox.setText(date)

    def _remove_item_from_list(self, item: QListWidgetItem):
        value = item.text()
        date, author = value.split(" - ", 1)
        self.remove_from_history_method(date, author)

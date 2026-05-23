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
        history_list_layout = QVBoxLayout()

        # Title
        title               = self.create_label("היסטוריה", to_center=True)
        history_list_layout.addWidget(title)

        # List
        self.history_list   = self.create_list(callback_double_click=self._remove_item_from_list)
        history_list_layout.addWidget(self.history_list)

        # Programmer
        combo                       = self.create_combo(list(Var.EMPLOYEES.keys()), self._on_change_programmer)
        self.programmer_textbox     = self.create_textbox()
        label                       = self.create_label("שם", to_center=True)

        layout = QHBoxLayout()
        layout.addWidget(combo)
        layout.addWidget(self.programmer_textbox)
        layout.addWidget(label)
        history_list_layout.addLayout(layout)

        # Date
        btn                 = self.create_button("רשום תאריך", self._on_write_date, property_name="settings_button")
        self.date_textbox   = QLineEdit()
        label               = self.create_label("תאריך", to_center=True)

        layout = QHBoxLayout()
        layout.addWidget(btn)
        layout.addWidget(self.date_textbox)
        layout.addWidget(label)
        history_list_layout.addLayout(layout)

        # "Add To List" Button
        add_btn = self.create_button("הוסף", self._add_to_history, property_name="settings_button")
        history_list_layout.addWidget(add_btn)

        return history_list_layout

    def _build_settings_layout(self):
        """
        This method build the right side of the view.
        The right side manages the settings of the app.
        """
        # ========== Settings Layout ========== #
        settings_layout     = QVBoxLayout()

        # Junction Number
        label = self.create_label("מספר צומת", to_center=True)
        self.junc_textbox = self.create_textbox(callback=self._on_change_junction_number)

        layout = QHBoxLayout()
        layout.addWidget(self.junc_textbox)
        layout.addWidget(label)

        settings_layout.addLayout(layout)

        # Junction Name
        label = self.create_label("שם צומת", to_center=True)
        self.junction_name_textbox = self.create_textbox(callback=self._on_change_junction_name)

        layout = QHBoxLayout()
        layout.addWidget(self.junction_name_textbox)
        layout.addWidget(label)

        settings_layout.addLayout(layout)

        # Version
        label = self.create_label("גרסה", to_center=True)
        self.version_textbox = self.create_textbox(callback=self._on_change_version)

        layout = QHBoxLayout()
        layout.addWidget(self.version_textbox)
        layout.addWidget(label)

        settings_layout.addLayout(layout)

        # First Cycle Extension
        label = self.create_label("הארכה ראשונית", to_center=True)
        self.first_cycle_ext_textbox = self.create_textbox(callback=self._on_change_first_cycle_ext)

        layout = QHBoxLayout()
        layout.addWidget(self.first_cycle_ext_textbox)
        layout.addWidget(label)

        settings_layout.addLayout(layout)

        #
        settings_layout.addStretch()
        return settings_layout

    def _build_root_layout(self, layout_list):
        # ========== Root Layout ========== #
        root_layout = QVBoxLayout()

        # Title
        title = self.create_label("Tel Aviv Version", object_name="Title", to_center=True)

        # ========== Content Layout ========== #
        content_layout      = QHBoxLayout()
        for layout in layout_list:
            content_layout.addLayout(layout)

        # Root Layout
        root_layout.addWidget(title)
        root_layout.addLayout(content_layout)

        return root_layout


    # ============================== CRUD ============================== #
    def _add_to_history(self):
        self.push_to_history_method(self.date_textbox.text(), self.programmer_textbox.text())
        self.programmer_textbox.clear()
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
        self.programmer_textbox.setText(Var.EMPLOYEES[text])
        self._on_write_date()

    def _on_write_date(self):
        date = self.get_date_method()
        self.date_textbox.setText(date)

    def _remove_item_from_list(self, item: QListWidgetItem):
        value = item.text()
        date, author = value.split(" - ", 1)
        self.remove_from_history_method(date, author)

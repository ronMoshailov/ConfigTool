from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QLabel, QComboBox, QPushButton, QLineEdit, QCheckBox, QListWidget


class BaseLayoutView(QWidget):
    def __init__(self):
        super().__init__()

    # ========== Create Layout - Combo ========== #
    @staticmethod
    def create_label(name, object_name = None, to_center=None):
        """
        This method creates a QLabel object.

        :param name: The text to display inside the label.
        :param object_name: Optional object name used for styling or identification.
        :param to_center: Determines whether the label text should be center-aligned.

        :return: Configured QLabel instance.
        """
        label = QLabel(name)

        if to_center is not None:
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        if object_name is not None:
            label.setObjectName(object_name)

        return label

    @staticmethod
    def create_combo(data, callback, add_dash = False, set_value = None, disable_wheel_event=False):
        """
        This method creates a QComboBox object.

        :param data: List of items to insert into the combo box.
        :param callback: Function to connect to the combo box change event.
        :param add_dash: Optional - Determines whether to add a default "-" item at the top.
        :param set_value: Optional - value to select as the current item.
        :param disable_wheel_event: Optional - Determines whether mouse wheel scrolling is disabled.

        :return: Configured QComboBox instance.
        """
        combo = QComboBox()

        if add_dash:
            data = [" - "] + data
        combo.addItems(data)

        if set_value is not None:
            combo.setCurrentText(set_value)

        if not disable_wheel_event:
            combo.wheelEvent = lambda event: None # override the wheel mouse event (disable the wheel mouse)

        combo.currentTextChanged.connect(callback)

        return combo

    @staticmethod
    def create_check_box(is_checked = False, object_name = None):
        """
        This method creates a QCheckBox object.

        :param is_checked: Optional - Determines whether the checkBox is initially checked.
        :param object_name: Optional - object name used for styling or identification.

        :return: Configured QCheckBox instance.
        """
        check_box = QCheckBox()

        check_box.setChecked(is_checked)

        if object_name is not None:
            check_box.setObjectName(object_name)

    @staticmethod
    def create_button(text, callback, object_name = None, property_name = None):
        """
        :param text: The text displayed on the button.
        :param callback: Function to connect to the button click event.
        :param object_name: Optional - object name used for styling or identification.
        :param property_name: Optional - custom property name for styling or behavior.

        :return: Configured QPushButton instance.
        """
        btn = QPushButton(text)

        if object_name is not None:
            btn.setObjectName(object_name)

        if property_name is not None:
            btn.setProperty("class", property_name)

        btn.clicked.connect(callback)

        return btn

    @staticmethod
    def create_textbox(text=None, callback=None, placeholder=None):
        line_edit = QLineEdit()

        if text is not None:
            line_edit.setText(text)

        if callback is not None:
            line_edit.editingFinished.connect(callback)

        if placeholder is not None:
            line_edit.setPlaceholderText(placeholder)

        return line_edit

    @staticmethod
    def create_list(callback_double_click=None):
        lst = QListWidget()

        if callback_double_click is not None:
            lst.itemDoubleClicked.connect(callback_double_click)

        return lst



# ================================================== #
# -------------------- QLineEdit ------------------- #
# textChanged -> fire every change.
# textEdited -> fire when the user edit the text (not from the code)
# editingFinished -> fire when the user lose focus or press enter



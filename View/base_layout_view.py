from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QMessageBox, QLabel, QComboBox, QPushButton


class BaseLayoutView(QWidget):
    def __init__(self):
        super().__init__()

    # ========== Create Layout - Combo ========== #
    @staticmethod
    def create_centered_label(name, object_name = None):
        label = QLabel(name)
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        if object_name is not None:
            label.setObjectName(object_name)
        return label

    @staticmethod
    def create_combo(data, callback, add_dash = False, set_value = None):
        combo = QComboBox()

        if add_dash:
            data = [" - "] + data
        combo.addItems(data)

        if set_value is not None:
            combo.setCurrentText(set_value)
        combo.currentTextChanged.connect(callback)

        return combo

    @staticmethod
    def create_button(name, callback, object_name = None, property = None):
        date_btn = QPushButton(name)
        date_btn.clicked.connect(callback)

        if property is not None:
            date_btn.setProperty("class", property)
        if object_name is not None:
            date_btn.setObjectName(object_name)

"""
===== Combo =====
setCurrentText:
* return the new value of the 


"""
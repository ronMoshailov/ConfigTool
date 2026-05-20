from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QMessageBox, QLabel, QComboBox, QPushButton


class BaseView(QWidget):
    def __init__(self):
        super().__init__()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ========== Create Layout ========== #
    @staticmethod
    def get_centered_label(name, object_name = None):
        label = QLabel(name)
        label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        if object_name is not None:
            label.setObjectName(object_name)
        return label

    @staticmethod
    def get_combo(data, callback, add_dash = False):
        combo = QComboBox()
        if add_dash:
            data = [" - "] + data
        combo.addItems(data)
        combo.currentTextChanged.connect(callback)

    @staticmethod
    def create_button(name, callback, property = None):
        date_btn = QPushButton(name)
        if property is not None:
            date_btn.setProperty("class", property)
        date_btn.clicked.connect(callback)


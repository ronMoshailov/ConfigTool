from PyQt6.QtWidgets import QMessageBox

from View.base_layout_view import BaseLayoutView


class BaseView(BaseLayoutView):
    def __init__(self):
        super().__init__()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)


from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QMessageBox


class MainView(QWidget):
    def __init__(self, all_views):
        super().__init__()

        root_layout = QHBoxLayout()
        for view in all_views.values():
            root_layout.addWidget(view)
        root_layout.setAlignment(Qt.AlignmentFlag.AlignRight)
        self.setLayout(root_layout)

    def show_view(self):
        # Data
        self.show()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)


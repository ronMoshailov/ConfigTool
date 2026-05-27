from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QHBoxLayout

from View.base_view import BaseView


class MainView(BaseView):
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


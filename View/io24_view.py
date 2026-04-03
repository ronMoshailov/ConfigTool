from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QTableWidget, QMessageBox, QHBoxLayout


class Io24View(QWidget):

    def __init__(self):
        super().__init__()
        # Controller Methods

        # # Data
        # self.input = QTableWidget(32, 2)
        # self.output = QTableWidget(32, 2)
        #
        # # Cards Layout
        # self.cards_layout = QHBoxLayout()
        # self.cards_layout.addWidget(self.input)
        # self.cards_layout.addWidget(self.output)
        #
        # # Self
        # self.setLayout(self.cards_layout)
        # # self.setStyleSheet(sk_panel_style)
        # # self.setObjectName("RootWidget")
        # self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        # self.hide()

    def show_view(self, sk_list, all_moves):
        """
        This method clears the main layout and build back with data from DB.
        """
        # refresh the table
        # clear_widget_from_layout([self.cards_layout])
        # self.tables_list.clear()
        #
        # # for each SK card initialize a table
        # for i in range(1, len(sk_list) + 1):
        #     card_widget = self._build_table_layout(i, all_moves, sk_list)
        #     self.cards_layout.addWidget(card_widget)
        #
        # # move all tables left
        # self.cards_layout.addStretch(1)

        self.show()

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ============================== Layout ============================== #

from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QGridLayout, QWidget, QPushButton, QScrollArea, QLineEdit, QLabel, QVBoxLayout, QSizePolicy, \
    QFrame, QMessageBox

from Config.special import clear_widget_from_layout
from Config.style import min_green_panel_style


class MinGreenView(QWidget):

    def __init__(self):
        super().__init__()

        self.update_method = None

        # =============== Grid Layout =============== #
        self.grid_layout = QGridLayout()
        self.grid_layout.setHorizontalSpacing(36)
        self.grid_layout.setVerticalSpacing(36)
        self.grid_layout.setContentsMargins(40, 40, 40, 40)

        # ==================== Scroll Area ==================== #
        self.scroll_area = QScrollArea()
        self.scroll_area.setWidgetResizable(True)  # חשוב, אחרת הגובה יהיה 0
        self.scroll_area.setObjectName("scroll_area")

        self.container_widget = QWidget()
        self.container_widget.setObjectName("container_widget")

        self.scroll_area.setWidget(self.container_widget)   # set 'scroll_area' as father of 'scroll_content'
        self.container_widget.setLayout(self.grid_layout) # set the widget as the father of the layout

        # ==================== QPushButton ==================== #
        btn = QPushButton("עדכן")
        btn.setCursor(Qt.CursorShape.PointingHandCursor)
        btn.setObjectName("update_button")
        btn.clicked.connect(lambda: self.update_method(self.min_green_dict))

        # ==================== Widget to hold grid ==================== #
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.scroll_area, 1)
        self.root_layout.addWidget(btn)

        # ==================== Self ==================== #
        self.setLayout(self.root_layout)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        self.setStyleSheet(min_green_panel_style)
        self.hide()


    # =============== methods =============== #
    def hide_view(self):
        self.hide()

    def show_view(self, all_moves):

        self.min_green_dict = {}
        cards_in_row = 7

        # =============== Clear grid =============== #
        clear_widget_from_layout([self.grid_layout])

        # =============== Build grid =============== #
        for i, move in enumerate(all_moves):
            row_num = i // cards_in_row
            col_num = i % cards_in_row

            # =============== QLabel =============== #
            label = QLabel(move.name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)

            # =============== QLineEdit =============== #
            textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
            textbox.setAlignment(Qt.AlignmentFlag.AlignCenter)
            textbox.setValidator(QIntValidator(0, 99))
            if move.name.startswith("B"):
                textbox.setReadOnly(True)

            # =============== Add to dictionary =============== #
            self.min_green_dict[move.name] = textbox

            # =============== Vertical layout =============== #
            vertical_layout = QVBoxLayout()
            vertical_layout.addWidget(label)
            vertical_layout.addWidget(textbox)
            vertical_layout.setContentsMargins(0, 0, 0, 18)  # left, top, right, bottom

            # =============== QFrame =============== #
            card = QFrame()
            card.setObjectName("card")
            card.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)
            card.setMaximumHeight(100)
            card.setLayout(vertical_layout)

            # =============== Add to layout =============== #
            self.grid_layout.addWidget(card, row_num, col_num)

        # =============== Add to layout =============== #
        self.grid_layout.setRowStretch(self.grid_layout.rowCount(), 1)
        # self.grid_layout.addWidget(btn, self.grid_layout.rowCount(), 0, 1, cards_in_row)  # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
        self.show()

    # =============== inner methods =============== #
    # def _update(self):
    #     success, message = self.data_controller.update_min_green(self.min_green_dict)
    #     if success:
    #         QMessageBox.information(self, "מינימום ירוק", "כל הזמני מינימום ירוק עודכנו בהצלחה")
    #         return
    #     QMessageBox.critical(self, "מינימום ירוק", message)


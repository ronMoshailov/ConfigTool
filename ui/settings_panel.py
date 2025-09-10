from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIntValidator
from PyQt6.QtWidgets import QWidget, QLabel, QLineEdit, QPushButton, QHBoxLayout, QVBoxLayout, QFrame, QSizePolicy, \
    QGridLayout

from config.style import min_green_panel_style, settings_panel_style
from controllers.data_controller import DataController


class SettingsPanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== Layout =============== #
        root_layout             = QHBoxLayout()
        left_side_layout        = QVBoxLayout()
        right_side_layout       = QVBoxLayout()
        name_layout             = QHBoxLayout()
        junc_layout             = QHBoxLayout()
        junction_name_layout    = QHBoxLayout()
        version_layout          = QHBoxLayout()
        first_cycle_ext_layout  = QHBoxLayout()
        date_layout             = QHBoxLayout()

        # =============== QPushButton =============== #
        master_btn      = QPushButton("מאסטר")
        master_btn.setProperty("class", "settings_button")

        slave_btn       = QPushButton("סלייב")
        slave_btn.setProperty("class", "settings_button")

        dx_btn          = QPushButton("dx")
        dx_btn.setProperty("class", "settings_button")

        new_node_btn    = QPushButton("צומת חדש")
        new_node_btn.setProperty("class", "settings_button")

        # =============== QLabel =============== #
        name_label             = QLabel("שם")
        name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        junc_label             = QLabel("מספר צומת")
        junc_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        junction_name_label    = QLabel("שם צומת")
        junction_name_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        version_label          = QLabel("גרסה")
        version_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        first_cycle_ext_label  = QLabel("הארכה ראשונית")
        first_cycle_ext_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        date_label             = QLabel("תאריך")
        date_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # =============== QLineEdit =============== #
        self.name_textbox             = QLineEdit()
        self.junc_textbox             = QLineEdit()
        self.junction_name_textbox    = QLineEdit()
        self.version_textbox          = QLineEdit()
        self.first_cycle_ext_textbox  = QLineEdit()
        self.date_textbox             = QLineEdit()

        # build right side
        name_layout.addStretch()
        name_layout.addWidget(self.name_textbox)
        name_layout.addWidget(name_label)

        junc_layout.addStretch()
        junc_layout.addWidget(self.junc_textbox)
        junc_layout.addWidget(junc_label)

        junction_name_layout.addStretch()
        junction_name_layout.addWidget(self.junction_name_textbox)
        junction_name_layout.addWidget(junction_name_label)

        version_layout.addStretch()
        version_layout.addWidget(self.version_textbox)
        version_layout.addWidget(version_label)

        first_cycle_ext_layout.addStretch()
        first_cycle_ext_layout.addWidget(self.first_cycle_ext_textbox)
        first_cycle_ext_layout.addWidget(first_cycle_ext_label)

        date_layout.addStretch()
        date_layout.addWidget(self.date_textbox)
        date_layout.addWidget(date_label)

        right_side_layout.addSpacing(10)
        right_side_layout.addLayout(name_layout)
        right_side_layout.addSpacing(30)
        right_side_layout.addLayout(junc_layout)
        right_side_layout.addLayout(junction_name_layout)
        right_side_layout.addLayout(version_layout)
        right_side_layout.addLayout(date_layout)
        right_side_layout.addSpacing(30)
        right_side_layout.addLayout(first_cycle_ext_layout)
        right_side_layout.addStretch()

        # build left side
        left_side_layout.addWidget(master_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(slave_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(dx_btn)
        # left_side_layout.addStretch()
        left_side_layout.addWidget(new_node_btn)
        left_side_layout.addStretch()

        root_layout.addLayout(left_side_layout)
        root_layout.addLayout(right_side_layout)

        self._make_checkable([master_btn, slave_btn, dx_btn, new_node_btn])
        self.setObjectName("SettingsPanel")
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setLayout(root_layout)
        self.setStyleSheet(settings_panel_style)
        self.hide()


    def show_panel(self):

        self.name_textbox.setText(self.data_controller.data_manager.lastVersionAuthor)

        self.junc_textbox.setText(self.data_controller.data_manager.anlagenName)
        self.junction_name_textbox.setText(self.data_controller.data_manager.tk1Name)
        self.version_textbox.setText(self.data_controller.data_manager.version)
        self.date_textbox.setText(self.data_controller.data_manager.lastVersionDate)
        self.first_cycle_ext_textbox.setText(self.data_controller.data_manager.first_time_ext)

        self.show()

    def _make_checkable(self, btn_list):
        """
        This method make buttons checkable.

        :return: None
        """
        for btn in btn_list:
            btn.setCheckable(True)

    #     # =============== Self =============== #
    #     self.setLayout(self.root_layout)
    #
    #     self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
    #     self.setObjectName("root")
    #     self.setStyleSheet(min_green_panel_style)
    #
    #
    #
    # # =============== methods =============== #
    # def show_panel(self):
    #     # =============== Data =============== #
    #     all_moves = self.data_controller.get_all_moves()
    #     min_green_dict = {}
    #     cards_in_row = 7
    #
    #     # =============== Clear grid =============== #
    #     self._clear_layout()
    #
    #     # =============== Build grid =============== #
    #     for i, move in enumerate(all_moves):
    #         row_num = i // cards_in_row
    #         col_num = i % cards_in_row
    #
    #         # =============== QLabel =============== #
    #         label = QLabel(move.name)
    #         label.setAlignment(Qt.AlignmentFlag.AlignCenter)
    #
    #         # =============== QLineEdit =============== #
    #         textbox = QLineEdit("" if move.min_green is None else str(move.min_green))
    #         textbox.setAlignment(Qt.AlignmentFlag.AlignCenter)
    #         textbox.setValidator(QIntValidator(0, 99))
    #         if move.name.startswith("B"):
    #             textbox.setReadOnly(True)
    #
    #         # =============== Add to dictionary =============== #
    #         min_green_dict[move.name] = textbox
    #
    #         # =============== Vertical layout =============== #
    #         vertical_layout = QVBoxLayout()
    #         vertical_layout.addWidget(label)
    #         vertical_layout.addWidget(textbox)
    #         vertical_layout.setContentsMargins(0, 0, 0, 18)  # left, top, right, bottom
    #
    #         # =============== QFrame =============== #
    #         card = QFrame()
    #         card.setObjectName("card")
    #         card.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Preferred)
    #         card.setMaximumHeight(100)
    #         card.setLayout(vertical_layout)
    #
    #         # =============== Add to layout =============== #
    #         self.root_layout.addWidget(card, row_num, col_num)
    #
    #     btn = QPushButton("עדכן")
    #     btn.setCursor(Qt.CursorShape.PointingHandCursor)
    #     btn.setObjectName("update_button")
    #     btn.clicked.connect(lambda: self.data_controller.update_min_green(min_green_dict))
    #
    #     # =============== Add to layout =============== #
    #     self.root_layout.setRowStretch(self.root_layout.rowCount(), 1)
    #     self.root_layout.addWidget(btn, self.root_layout.rowCount(), 0, 1, cards_in_row)  # add the textBox (component, row_num, col_num, how many rows use, how many columns to use)
    #     self.show()
    #
    # # =============== inner methods =============== #
    # def _clear_layout(self, layout=None):
    #     """
    #     Recursively clear all widgets and layouts inside a given layout.
    #     If no layout is provided, clear the root_layout.
    #     """
    #     if layout is None:
    #         layout = self.root_layout
    #
    #     while layout.count():
    #         item = layout.takeAt(0)
    #
    #         if item.widget():
    #             item.widget().deleteLater()
    #         elif item.layout():
    #             self._clear_layout(item.layout())
    #
    #     if layout is not self.root_layout:
    #         layout.deleteLater()

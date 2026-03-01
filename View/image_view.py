from PyQt6.QtGui import QFont

import Config

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QVBoxLayout, QPushButton, QLineEdit, QLabel, QCheckBox, \
    QAbstractItemView, QTableWidget, QHeaderView, QSizePolicy, QMessageBox


class ImageView(QWidget):
    MOVE_NAME_WIDTH = 70
    CHECKBOX_WIDTH = 70

    def __init__(self):
        super().__init__()

        # Set Controller Methods
        self.add_image_method               = None
        self.remove_image_method            = None
        self.update_stop_point_method       = None
        self.update_skeleton_method         = None
        self.update_image_number_method     = None
        self.update_move_assignment_method  = None

        # Data
        self.table_dict = {}

        # Scroll
        self.scroll_layout = QHBoxLayout()
        self.scroll_layout.setContentsMargins(0, 0, 0, 0)
        self.scroll_layout.setSpacing(16)

        # QScrollArea
        self.scroll_area = Config.special.init_scroll(self.scroll_layout)

        # Add Button
        self.btn_add = QPushButton("הוסף")
        self.btn_add.setObjectName("add_button")
        self.handle_add_image(self.btn_add)

        self.btn_add.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Fixed)
        self.btn_add.setFixedWidth(400)

        # =============== QLineEdit =============== #
        self.edit_add = QLineEdit()
        self.edit_add.setPlaceholderText("שם התמונה")
        self.edit_add.Alignment = Qt.AlignmentFlag.AlignRight
        self.edit_add.setFixedWidth(300)

        # Button Layout
        self.btn_layout = QHBoxLayout()
        self.btn_layout.addStretch()
        self.btn_layout.addWidget(self.edit_add)
        self.btn_layout.addWidget(self.btn_add)

        # =============== Root Layout =============== #
        self.root_layout = QVBoxLayout()
        self.root_layout.addWidget(self.scroll_area)
        self.root_layout.addLayout(self.btn_layout)

        # =============== Self =============== #
        self.setLayout(self.root_layout)
        self.setObjectName("RootWidget")
        self.setStyleSheet(Config.style.image_panel_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)
        self.hide()

    def show_view(self, all_images, all_moves_names):
        Config.special.clear_widget_from_layout([self.scroll_layout])
        self.table_dict.clear()

        # widget that holds title and table
        image_count = len(all_images)

        for i in range(image_count):
            wrap = self._create_wrap(all_images[i], all_moves_names)
            self.scroll_layout.addWidget(wrap)
        self.scroll_layout.addStretch(1)
        self.show()                                                         # show panel

    def hide_view(self):
        self.hide()

    def show_error(self, msg):
        QMessageBox.critical(self, "שגיאה", msg)

    # ============================== Layout ============================== #
    def _create_wrap(self, image, all_moves_names):
        # warp
        wrap = QWidget()
        wrap.setObjectName("column_wrap")
        wrap.setFixedWidth(self.MOVE_NAME_WIDTH + self.CHECKBOX_WIDTH + 30)

        # title
        title = QLabel(f"{image.image_name}({image.image_num})")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        wrap.title = image.image_name

        # table
        table = self._init_table(image.image_name, all_moves_names)

        self._fill_table(table, image.move_names_list)

        # # button
        remove_button = QPushButton("מחק")
        remove_button.setObjectName("remove_button")
        remove_button.clicked.connect(lambda _, w=wrap: self.remove_image_method(w.title))

        # skeleton
        textbox_skeleton = QLineEdit()
        textbox_skeleton.setText(str(image.skeleton))
        textbox_skeleton.editingFinished.connect(lambda textbox=textbox_skeleton, m=image.image_name: self.update_skeleton_method(m, textbox.text()))

        label = QLabel("שלד")

        skeleton_layout = QHBoxLayout()
        skeleton_layout.addWidget(textbox_skeleton)
        skeleton_layout.addWidget(label)

        # image number
        textbox_image_number = QLineEdit()
        textbox_image_number.setText(str(image.image_num))
        textbox_image_number.editingFinished.connect(lambda textbox=textbox_image_number, m=image.image_name: self.update_image_number_method(m, textbox.text()))

        label = QLabel("מספר תמונה")

        skeleton_image_number = QHBoxLayout()
        skeleton_image_number.addWidget(textbox_image_number)
        skeleton_image_number.addWidget(label)

        # sp
        textbox_sp = QLineEdit()
        textbox_sp.textChanged.connect(lambda text, name=image.image_name: self.update_stop_point_method(name, text))
        textbox_sp.setText(str(image.sp))

        label = QLabel("נקודת החלטה")

        sp_layout = QHBoxLayout()
        sp_layout.addWidget(textbox_sp)
        sp_layout.addWidget(label)


        # add to wrap
        wrap_layout = QVBoxLayout()
        wrap_layout.addWidget(title)
        wrap_layout.addWidget(table)

        wrap_layout.addLayout(sp_layout)
        wrap_layout.addLayout(skeleton_layout)
        wrap_layout.addLayout(skeleton_image_number)
        wrap_layout.addWidget(remove_button)

        wrap.setLayout(wrap_layout)
        # attribute
        table.skeleton_textbox = textbox_skeleton
        table.image_number = textbox_image_number

        self.table_dict[image.image_name] = table
        return wrap

    # ============================== Logic ============================== #
    def _fill_table(self, table, all_moves_names):
        """
        This method fill the table with all moves and values.

        :param table: The table to be filled
        :param all_moves: All moves that belong to the image(table)
        :return: None
        """
        row_num = table.rowCount()

        for i in range (row_num):
            table_move_name = table.cellWidget(i, 0).text()
            checkbox = table.cellWidget(i, 1).findChild(QCheckBox)

            for move_name in all_moves_names:
                if move_name == table_move_name:
                    checkbox.blockSignals(True)
                    checkbox.setChecked(True)
                    checkbox.blockSignals(False)

    def _init_table(self, image_name, all_moves_names):
        """
        Create table and config the table.

        :return: QTableWidget
        """
        tbl = QTableWidget(0, 2)
        tbl.setObjectName("tbl")
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.verticalHeader().setVisible(False)
        tbl.setHorizontalHeaderLabels(["מופע", "קיים"])
        tbl.setColumnWidth(0, self.MOVE_NAME_WIDTH)
        tbl.setColumnWidth(1, self.CHECKBOX_WIDTH)
        tbl.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Stretch)    # Stretch the horizontal header
        tbl.verticalHeader().setMinimumSectionSize(40)                               # Set minimum size

        # build move rows in table
        for move_name in all_moves_names:
            row_num = tbl.rowCount()
            tbl.insertRow(row_num)

            label = QLabel(move_name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)

            font = QFont()
            font.setFamily("Arial")
            font.setPointSize(16)
            label.setFont(font)

            tbl.setCellWidget(row_num, 0, label)

            check_box = QCheckBox()
            check_box.setChecked(False)
            check_box.setObjectName("checkbox_comment")
            check_box.stateChanged.connect(lambda _, img=image_name, m=move_name: self.update_move_assignment_method(img, m))

            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addStretch()
            layout.addWidget(check_box)
            layout.addStretch()
            layout.setContentsMargins(0, 0, 0, 0)  # with no unnecessary margins
            tbl.setCellWidget(row_num, 1, container)

        return tbl

    def handle_add_image(self, add_btn):
        def handler():
            name = self.edit_add.text().capitalize()
            name = "EQA" if name.lower == "Eqa" else name

            self.add_image_method(name)
        add_btn.clicked.connect(handler)


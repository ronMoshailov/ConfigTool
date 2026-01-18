import Config

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QScrollArea, QHBoxLayout, QVBoxLayout, QPushButton, QLineEdit, QLabel, QCheckBox, \
    QAbstractItemView, QTableWidget


class ImageView(QWidget):

    MOVE_NAME_WIDTH = 70
    CHECKBOX_WIDTH = 70

    def __init__(self):
        super().__init__()

        # set controller methods
        self.add_image_method = None
        self.remove_image_method = None
        self.on_sp_changed_method = None
        self.update_skeleton_method = None
        self.update_image_number_method = None
        self.on_checkbox_changed = None

        # Scroll
        self.scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()             # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")

        self.scroll_layout = QHBoxLayout()

        self.scroll_area.setWidget(self.scroll_content)
        self.scroll_content.setLayout(self.scroll_layout)
        self.table_dict = {}

        # self
        self.root_layout = QVBoxLayout()
        self.btn_layout = QHBoxLayout()

        # Button
        self.btn_add = QPushButton("הוסף")
        self.btn_add.setFixedWidth(150)
        self.btn_add.setObjectName("add_button")
        self.btn_add.clicked.connect(lambda: self.add_image_method(self.edit_add.text()))

        # =============== QLineEdit =============== #
        self.edit_add = QLineEdit()
        self.edit_add.setFixedWidth(200)
        self.edit_add.setPlaceholderText("שם התמונה")
        self.edit_add.Alignment = Qt.AlignmentFlag.AlignRight

        self.btn_layout.addStretch()
        self.btn_layout.addWidget(self.edit_add)
        self.btn_layout.addWidget(self.btn_add)

        # =============== ****** =============== #
        self.root_layout.addWidget(self.scroll_area)
        self.root_layout.addLayout(self.btn_layout)
        self.setLayout(self.root_layout)

        self.setObjectName("imagePanel")
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
        self.scroll_layout.addStretch()
        self.show()                                                         # show panel

    def hide_view(self):
        self.hide()

    # ============================== CRUD ============================== #

    # ============================== Logic ============================== #
    def _create_wrap(self, image, all_moves_names):
        """
        ניסיתי לאחד 2 מתודות וסתם יצאתי חכמולוג ואין לי כוח להחזיר, מה שעושה הפונקציה זה ליצור widget שכולל את כל העמודה כאשר כל עמודה זה תבלה וכל מה שיש בה ומחוץ לה
        :param image:
        :param first_show:
        :return:
        """
        # warp
        wrap = QWidget()
        wrap_layout = QVBoxLayout()
        wrap.setObjectName("column_wrap")
        wrap.setLayout(wrap_layout)

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
        textbox_sp.textChanged.connect(lambda text, name=image.image_name: self.on_sp_changed_method(name, text))
        textbox_sp.setText(str(image.sp))

        label = QLabel("נקודת החלטה")

        sp_layout = QHBoxLayout()
        sp_layout.addWidget(textbox_sp)
        sp_layout.addWidget(label)


        # add to wrap
        wrap_layout.addWidget(title)
        wrap_layout.addWidget(table)
        wrap.setFixedWidth(self.MOVE_NAME_WIDTH + self.CHECKBOX_WIDTH + 30)

        wrap_layout.addLayout(sp_layout)
        wrap_layout.addLayout(skeleton_layout)
        wrap_layout.addLayout(skeleton_image_number)
        wrap_layout.addWidget(remove_button)

        # attribute
        table.skeleton_textbox = textbox_skeleton
        table.image_number = textbox_image_number

        self.table_dict[image.image_name] = table
        return wrap

    def _fill_table(self, table, all_moves_names):
        """
        This method fill the table with all moves and values.

        :param table: The table to be filled
        :param all_moves: All moves that belong to the image(table)
        :return: None
        """
        row_num = table.rowCount()  # לוקח את מספר השורות הקיים

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

        # build move rows in table
        for move_name in all_moves_names:
            row_num = tbl.rowCount()  # לוקח את מספר השורות הקיים
            tbl.insertRow(row_num)  # מוסיף שורה חדשה בסוף

            label = QLabel(move_name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            tbl.setCellWidget(row_num, 0, label)

            check_box = QCheckBox()
            check_box.setChecked(False)
            check_box.setObjectName("checkbox_comment")
            check_box.stateChanged.connect(lambda _, img=image_name, m=move_name: self.on_checkbox_changed(img, m))

            # wrapper קטן עם layout שמרכז את ה-checkbox
            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addStretch()
            layout.addWidget(check_box)
            layout.addStretch()
            layout.setContentsMargins(0, 0, 0, 0)  # בלי margins מיותרים
            tbl.setCellWidget(row_num, 1, container)

        return tbl

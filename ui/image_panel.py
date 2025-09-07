from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QTableWidget, QLabel, \
    QAbstractItemView, QHeaderView, QPushButton, QScrollArea, QCheckBox, QLineEdit

from config.style import image_panel_style
from controllers.data_controller import DataController

class ImagePanel(QWidget):

    def __init__(self):
        super().__init__()
        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== scroll =============== #
        self.scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()     # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")

        self.scroll_layout = QHBoxLayout()

        self.scroll_area.setWidget(self.scroll_content)
        self.scroll_content.setLayout(self.scroll_layout)
        self.table_dict = {}

        # =============== self =============== #
        self.root_layout = QVBoxLayout()
        self.btn_layout = QHBoxLayout()

        # =============== Button =============== #
        self.btn_update = QPushButton("עדכן")
        self.btn_update.setFixedWidth(150)
        self.btn_update.setObjectName("update_button")
        self.btn_update.clicked.connect(lambda: self.data_controller.update_images(self.table_dict))

        self.btn_add = QPushButton("הוסף")
        self.btn_add.setFixedWidth(150)
        self.btn_add.setObjectName("add_button")
        self.btn_add.clicked.connect(lambda: self._add_wrap())

        # =============== QLineEdit =============== #
        self.edit_add = QLineEdit()
        self.edit_add.setFixedWidth(200)
        self.edit_add.setPlaceholderText("שם מופע")

        self.btn_layout.addStretch()
        self.btn_layout.addWidget(self.edit_add)
        self.btn_layout.addWidget(self.btn_add)
        self.btn_layout.addWidget(self.btn_update)

        # =============== ****** =============== #
        self.root_layout.addWidget(self.scroll_area)
        self.root_layout.addLayout(self.btn_layout)
        self.setLayout(self.root_layout)

        self.setObjectName("imagePanel")
        self.setStyleSheet(image_panel_style)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True) # ask from the engine style of Qt to color the background of the widget (QWidget not always active this)

        self.hide()

    # --------------- add methods --------------- #


    # --------------- update methods --------------- #
    def update_images(self):
        self.data_controller.update_images(self.table_dict)

    def _add_wrap(self):

        # image name
        image_name = self.edit_add.text()
        if not image_name.isalpha() or not image_name.isascii(): # כאן נכנסים אם ריק או אם יש תווים לא אותיות באנגלית
            self.data_controller.write_log("Invalid image name", "r")
            return False
        image_name = image_name.capitalize()

        for img_name in self.table_dict.keys():
            if img_name == image_name:
                self.data_controller.write_log("Image name already exists", "r")
                return False

        # warp
        wrap = self._create_wrap(image_name)

        count = self.scroll_layout.count()
        self.scroll_layout.insertWidget(count - 1, wrap)  # לפני ה-stretch

        # self.scroll_layout.addWidget(wrap)
        return True
        # pass

    # --------------- remove methods --------------- #
    def _remove_wrap(self, wrap):
        self.data_controller.remove_image(wrap.title)
        self.scroll_layout.removeWidget(wrap)

        # מוחק
        wrap.deleteLater()

    # --------------- general methods --------------- #
    def show_panel(self):
        """
        This method clear the root layout from all the tables and recreate it again from DB.

        :return: None
        """
        # clear the table
        self.clear_scroll_layout()

        # widget that holds title and table
        all_images = self.data_controller.get_all_images()
        image_count = len(all_images)

        for i in range(image_count):
            wrap = self._create_wrap(all_images[i], True)
            self.scroll_layout.addWidget(wrap)
        self.scroll_layout.addStretch()
        self.show()                                                         # show panel

    def _create_wrap(self, image, first_show = False):
        # warp
        wrap = QWidget()
        wrap_layout = QVBoxLayout()
        wrap.setObjectName("column_wrap")
        wrap.setLayout(wrap_layout)
        wrap.setFixedWidth(150)

        # title
        if first_show:
            title = QLabel(f"{image.image_name}")
        else:
            title = QLabel(f"{image}")

        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        if first_show:
            wrap.title = image.image_name
        else:
            wrap.title = image

        all_moves = self.data_controller.get_all_moves()

        # table
        table = self._init_table()
        self._build_table(table, all_moves)
        if first_show:
            self._fill_table(table, image.move_list)

        if first_show:
            self.table_dict[image.image_name] = table
        else:
            self.table_dict[image] = table



        # # button
        remove_button = QPushButton("מחק")
        remove_button.setObjectName("remove_button")
        remove_button.clicked.connect(lambda _, w=wrap: self._remove_wrap(w))

        wrap_layout.addWidget(title)
        wrap_layout.addWidget(table)
        wrap_layout.addWidget(remove_button)

        return wrap
        # self.table_dict[image.image_name] = table

    def clear_scroll_layout(self):
        """
        Clear the root layout.

        :return: None
        """
        while self.scroll_layout.count():  # as long a 'QLayoutItem' exist in 'tables_layout'
            it = self.scroll_layout.takeAt(0)  # disconnect the first 'QLayoutItem' (can be just another layout)
            w = it.widget()
            if w:
                w.deleteLater()

    def _init_table(self):
        tbl = QTableWidget(0, 2)
        tbl.setObjectName("tbl")
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.verticalHeader().setVisible(False)
        tbl.setHorizontalHeaderLabels(["מופע", "קיים"])
        tbl.setColumnWidth(0, 40)  # עמודה 0 ברוחב 60px
        tbl.setColumnWidth(1, 20)  # עמודה 1 ברוחב 60px
        tbl.horizontalHeader().setSectionResizeMode(2, QHeaderView.ResizeMode.Stretch)
        return tbl

    def _build_table(self, table, all_moves):
        for move in all_moves:
            row_num = table.rowCount()  # לוקח את מספר השורות הקיים
            table.insertRow(row_num)  # מוסיף שורה חדשה בסוף

            label = QLabel(move.name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            table.setCellWidget(row_num, 0, label)

            check_box = QCheckBox()
            check_box.setChecked(False)
            check_box.setObjectName("checkbox_comment")
            table.setCellWidget(row_num, 1, check_box)

    def _fill_table(self, table, all_moves):
            row_num = table.rowCount()  # לוקח את מספר השורות הקיים

            for i in range (row_num):
                move_name = table.cellWidget(i, 0).text()
                checkbox = table.cellWidget(i, 1)

                for move in all_moves:
                    if move.name == move_name:
                        checkbox.setChecked(True)




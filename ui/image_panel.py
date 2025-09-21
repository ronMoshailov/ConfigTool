from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QTableWidget, QLabel, \
    QAbstractItemView, QHeaderView, QPushButton, QScrollArea, QCheckBox, QLineEdit, QMessageBox, QSizePolicy

from config.special import clear_widget_from_layout
from config.style import image_panel_style
from controllers.data_controller import DataController

class ImagePanel(QWidget):

    MOVE_NAME_WIDTH = 70
    CHECKBOX_WIDTH = 70

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
        self.btn_update.clicked.connect(self.update_images)

        self.btn_add = QPushButton("הוסף")
        self.btn_add.setFixedWidth(150)
        self.btn_add.setObjectName("add_button")
        self.btn_add.clicked.connect(lambda: self._add_wrap())

        # =============== QLineEdit =============== #
        self.edit_add = QLineEdit()
        self.edit_add.setFixedWidth(200)
        self.edit_add.setPlaceholderText("שם התמונה")
        self.edit_add.Alignment = Qt.AlignmentFlag.AlignRight

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
    def _add_wrap(self):
        # image name
        image_name = self.edit_add.text()
        if not image_name.isalpha() or not image_name.isascii(): # כאן נכנסים אם ריק או אם יש תווים לא אותיות באנגלית
            QMessageBox.critical(self, "שגיאה", "שם התמונה שגוי")
            self.edit_add.clear()
            return
        image_name = image_name.capitalize()

        for img_name in self.table_dict.keys():
            if img_name == image_name:
                QMessageBox.critical(self, "שגיאה", "התמונה קיימת במערכת")
                self.edit_add.clear()
                return

        # warp
        wrap = self._create_wrap(image_name)
        count = self.scroll_layout.count()
        self.scroll_layout.insertWidget(count - 1, wrap)  # לפני ה-stretch
        self.edit_add.clear()

    # --------------- update methods --------------- #
    def update_images(self):
        self.data_controller.update_images(self.table_dict)
        QMessageBox.information(self, "הצלחה", "העדכן הצליח")
        self.edit_add.clear()


    # --------------- remove methods --------------- #
    def _remove_wrap(self, wrap):
        self.data_controller.remove_image(wrap.title)
        self.scroll_layout.removeWidget(wrap)

        # מוחק
        wrap.deleteLater()

        self.table_dict.pop(wrap.title)

    # --------------- general methods --------------- #
    def show_panel(self):
        """
        This method clear the root layout from all the tables and recreate it again from DB.

        :return: None
        """
        # clear the table
        clear_widget_from_layout([self.scroll_layout])
        self.table_dict.clear()

        # widget that holds title and table
        all_images = self.data_controller.get_all_images()
        image_count = len(all_images)

        for i in range(image_count):
            wrap = self._create_wrap(all_images[i], True)
            self.scroll_layout.addWidget(wrap)
        self.scroll_layout.addStretch()
        self.show()                                                         # show panel

    def _create_wrap(self, image, first_show = False):
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
        table = self._init_table(all_moves)

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

        # skeleton
        textbox = QLineEdit()
        if first_show:
            textbox.setText(str(image.skeleton))
        else:
            textbox.setText(str(99))

        label = QLabel("שלד")

        skeleton_layout = QVBoxLayout()
        skeleton_layout.addWidget(label)
        skeleton_layout.addWidget(textbox)

        wrap_layout.addWidget(title)
        wrap_layout.addWidget(table)
        wrap.setFixedWidth(self.MOVE_NAME_WIDTH + self.CHECKBOX_WIDTH + 30)

        wrap_layout.addLayout(skeleton_layout)
        wrap_layout.addWidget(remove_button)

        # attribute
        table.skeleton_textbox = textbox

        return wrap
        # self.table_dict[image.image_name] = table

    def _init_table(self, all_moves):
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
        for move in all_moves:
            row_num = tbl.rowCount()  # לוקח את מספר השורות הקיים
            tbl.insertRow(row_num)  # מוסיף שורה חדשה בסוף

            label = QLabel(move.name)
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            tbl.setCellWidget(row_num, 0, label)

            check_box = QCheckBox()
            check_box.setChecked(False)
            check_box.setObjectName("checkbox_comment")

            # wrapper קטן עם layout שמרכז את ה-checkbox
            container = QWidget()
            layout = QHBoxLayout(container)
            layout.addStretch()
            layout.addWidget(check_box)
            layout.addStretch()
            layout.setContentsMargins(0, 0, 0, 0)  # בלי margins מיותרים
            tbl.setCellWidget(row_num, 1, container)


        return tbl

    def _fill_table(self, table, all_moves):
        """
        This method fill the table with all moves and values.

        :param table: The table to be filled
        :param all_moves: All moves that belong to the image(table)
        :return: None
        """
        row_num = table.rowCount()  # לוקח את מספר השורות הקיים

        for i in range (row_num):
            move_name = table.cellWidget(i, 0).text()
            checkbox = table.cellWidget(i, 1).findChild(QCheckBox)

            for move in all_moves:
                if move.name == move_name:
                    checkbox.setChecked(True)




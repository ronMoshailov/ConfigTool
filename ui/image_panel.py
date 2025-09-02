from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QHBoxLayout, QTableWidget, QLabel, \
    QAbstractItemView, QHeaderView, QPushButton, QScrollArea, QCheckBox, QTextEdit, QLineEdit

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
        self.btn_update.clicked.connect(lambda: self.data_controller.update_images(self.table_dict))

        self.edit_add = QLineEdit()
        self.edit_add.setPlaceholderText("שם מופע")
        self.edit_add.setAlignment(Qt.AlignmentFlag.AlignRight)
        self.btn_add = QPushButton("הוסף")
        self.btn_add.clicked.connect(lambda: self._add_wrap())

        self.btn_layout.addWidget(self.edit_add)
        self.btn_layout.addWidget(self.btn_add)
        self.btn_layout.addWidget(self.btn_update)

        # =============== ****** =============== #
        self.root_layout.addWidget(self.scroll_area)
        self.root_layout.addLayout(self.btn_layout)
        self.setLayout(self.root_layout)

        # =============== style =============== #
        root_style = """
        /* ================= Image Panel ================= */
        QWidget#imagePanel {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                        stop:0 #f8fbff, stop:1 #eef3f9);
            border: 1px solid #cfd9e3;
            border-radius: 12px;
            padding: 8px;
        }

        /* ================= Scroll Area ================= */
        QScrollArea {
            border: none;
            background: transparent;
        }
        QWidget#scrollContent {
            background: transparent;
        }

        /* ================= Titles ================= */
        QLabel#title {
            font-size: 22px;
            font-weight: bold;
            color: #2c3e50;
            padding: 6px 10px;
            border-radius: 8px;
            background: #d2e1ff;
            border: 1px solid #b4c8e3;
        }
        QLabel#title:hover {
            background: #e6f0ff;
            border: 1px solid #4a90e2;
        }

        /* ================= Table ================= */
        QTableWidget#tbl {
            background: #ffffff;
            border: 1px solid #d0d7de;
            border-radius: 8px;
            gridline-color: #e0e6ed;
            font-size: 14px;
        }
        QTableWidget#tbl::item {
            padding: 6px;
        }
        QHeaderView::section {
            background: #f0f4f8;
            color: #34495e;
            font-weight: bold;
            border: 1px solid #d0d7de;
            padding: 4px;
        }

        /* ================= CheckBox ================= */
        QCheckBox {
            spacing: 8px;
            font-size: 14px;
            color: #2c3e50;
        }
        QCheckBox::indicator {
            width: 18px;
            height: 18px;
            border: 2px solid #7f8c8d;
            border-radius: 4px;
            background: #ffffff;
        }
        QCheckBox::indicator:checked {
            background-color: #27ae60;
            border: 2px solid #1e8449;
        }

        /* ================= Button ================= */
        QPushButton {
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 8px 18px;
            font-size: 15px;
            font-weight: bold;
        }
        QPushButton:hover {
            background-color: #2d83c4;
        }
        QPushButton:pressed {
            background-color: #236fa6;
        }
        """

        self.setObjectName("imagePanel")
        self.setStyleSheet(root_style)

        self.hide()

    # --------------- add methods --------------- #

    # --------------- update methods --------------- #
    def update_images(self):
        self.data_controller.update_images(self.table_dict)

    # --------------- remove methods --------------- #

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
            # warp
            wrap = QWidget()
            wrap_layout = QVBoxLayout()
            wrap.setObjectName("column_wrap")
            wrap.setLayout(wrap_layout)

            # title
            title = QLabel(f"{all_images[i].image_name}")
            title.setObjectName("title")
            title.setAlignment(Qt.AlignmentFlag.AlignCenter)

            all_moves = self.data_controller.get_all_moves()

            # table
            table = self._init_table()
            self._build_table(table, all_moves)
            self._fill_table(table, all_images[i].move_list)
            self.table_dict[all_images[i].image_name] = table

            # button
            remove_button = QPushButton("מחק")
            remove_button.clicked.connect(lambda _, w=wrap: self._remove_wrap(w))

            add_button = QPushButton("הוסף")
            add_button.clicked.connect(lambda: self._add_wrap())

            wrap_layout.addWidget(title)
            wrap_layout.addWidget(table)
            wrap_layout.addWidget(remove_button)

            self.scroll_layout.addWidget(wrap)
        self.show()                                                         # show panel

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
        tbl.setColumnWidth(0, 20)  # עמודה 0 ברוחב 60px
        tbl.setColumnWidth(1, 20)  # עמודה 1 ברוחב 60px
        tbl.horizontalHeader().setSectionResizeMode(2, QHeaderView.ResizeMode.Stretch)
        return tbl

    def _build_table(self, table, all_moves):
        for move in all_moves:
            row_num = table.rowCount()  # לוקח את מספר השורות הקיים
            table.insertRow(row_num)  # מוסיף שורה חדשה בסוף

            table.setCellWidget(row_num, 0, QLabel(move.name))
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

    def _remove_wrap(self, wrap):
        # מוציא מה־layout
        self.scroll_layout.removeWidget(wrap)
        # מוחק
        wrap.deleteLater()

    def _add_wrap(self):
        # warp
        wrap = QWidget()
        wrap_layout = QVBoxLayout()
        wrap.setObjectName("column_wrap")
        wrap.setLayout(wrap_layout)

        # title
        title = QLabel(self.edit_add.text())
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        all_moves = self.data_controller.get_all_moves()

        # table
        table = self._init_table()
        self._build_table(table, all_moves)

        self.table_dict[self.edit_add.text()] = table

        # button
        remove_button = QPushButton("מחק")
        remove_button.clicked.connect(lambda _, w=wrap: self._remove_wrap(w))

        wrap_layout.addWidget(title)
        wrap_layout.addWidget(table)
        wrap_layout.addWidget(remove_button)

        self.scroll_layout.addWidget(wrap)
        # pass


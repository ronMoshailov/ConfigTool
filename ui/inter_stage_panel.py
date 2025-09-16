from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QComboBox, QLabel, QCheckBox, QHBoxLayout, QVBoxLayout, QPushButton, QFrame, \
    QTableWidget, QAbstractItemView, QScrollArea, QTableWidgetItem

from config.style import inter_stage_panel_style
from controllers.data_controller import DataController


class InterStagePanel(QWidget):
    """
    This class present the panel of the inter stage panel.
    """
    def __init__(self):
        super().__init__()

        # =============== Data =============== #
        self.data_controller = DataController()
        self.table_wrap_list = []

        line = QFrame()
        line.setFrameShape(QFrame.Shape.HLine)  # קו אופקי
        line.setStyleSheet("background-color: black;")
        line.setFixedHeight(1)  # עובי הקו

        # =============== Layout =============== #
        top_layout          = self._create_top_layout()
        combo_layout        = self._create_combo_layout()
        root_layout         = QVBoxLayout()
        self.tables_layout  = QHBoxLayout()

        # =============== Scroll =============== #
        self.scroll_area = QScrollArea()        # create the container of the scroll bar. (get only widget)
        self.scroll_area.setObjectName("scroll_area")   #
        self.scroll_area.setWidgetResizable(True)   # it's needed and I don't know why and I don't even want to know, without this the scroll area size is like 0x0, fk chatGPT just confusing me

        self.scroll_content = QWidget()         # create the widget that will be in the layout.
        self.scroll_content.setObjectName("scrollContent")   #

        self.scroll_area.setWidget(self.scroll_content)   # set 'scroll_area' as father of 'scroll_content'
        self.scroll_content.setLayout(self.tables_layout) # set the widget as the father of the layout

        # =============== QPushButton =============== #
        btn = QPushButton("עדכן")

        root_layout.addLayout(top_layout)
        root_layout.addWidget(line)
        root_layout.addLayout(combo_layout)
        root_layout.addWidget(self.scroll_area)
        root_layout.addWidget(btn)

        # # =============== Self =============== #
        self.setLayout(root_layout)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        self.setStyleSheet(inter_stage_panel_style)
        self.hide()

    def show_panel(self):
        # reset
        self._reset()

        # data
        all_images = self.data_controller.get_all_images()
        all_inter_stages = self.data_controller.get_all_inter_stages()

        # fill the combos with values
        self.move_out_combo_top.addItems(["-"] + [image.image_name for image in all_images])
        self.move_in_combo_top.addItems(["-"] + [image.image_name for image in all_images])

        self.move_out_combo_center.addItems(["-"] + [image.image_name for image in all_images])
        self.move_in_combo_center.addItems(["-"] + [image.image_name for image in all_images])

        # create the tables
        out_in_images = [(inter_stage.image_out, inter_stage.image_in, inter_stage.transitions) for inter_stage in all_inter_stages]

        for img_out, img_in, transition in out_in_images:
            wrap = self._init_table(img_out, img_in, transition)
            self.table_wrap_list.append(wrap)
            self.tables_layout.addWidget(wrap)
        self.show()
    #

    def _create_top_layout(self):

        layout = QHBoxLayout()

        btn = QPushButton("הוסף")

        move_out_layout = QVBoxLayout()
        move_out_label = QLabel("מופע יוצא")
        self.move_out_combo_top = QComboBox()
        move_out_layout.addWidget(move_out_label)
        move_out_layout.addWidget(self.move_out_combo_top)

        move_in_layout = QVBoxLayout()
        move_in_label = QLabel("מופע נכנס")
        self.move_in_combo_top = QComboBox()
        move_in_layout.addWidget(move_in_label)
        move_in_layout.addWidget(self.move_in_combo_top)

        layout.addStretch()
        layout.addWidget(btn)
        layout.addLayout(move_out_layout)
        layout.addLayout(move_in_layout)

        return layout

    def _create_combo_layout(self):
        layout = QHBoxLayout()

        # move in
        move_in_layout = QVBoxLayout()
        move_in_label = QLabel("מופע נכנס")
        self.move_in_combo_center = QComboBox()
        self.move_in_combo_center.currentTextChanged.connect(self._filter)
        move_in_layout.addWidget(move_in_label)
        move_in_layout.addWidget(self.move_in_combo_center)

        # move out
        move_out_layout = QVBoxLayout()
        move_out_label = QLabel("מופע יוצא")
        self.move_out_combo_center = QComboBox()
        self.move_out_combo_center.currentTextChanged.connect(self._filter)

        move_out_layout.addWidget(move_out_label)
        move_out_layout.addWidget(self.move_out_combo_center)

        #
        layout.addStretch()
        layout.addLayout(move_in_layout)
        layout.addLayout(move_out_layout)

        return layout

    def _init_table(self, img_out, img_in, transitions):
        """
        This method initialize the widget of the table (title, table with values and signals)

        :param card_number: number of the SK card
        :return: QTableWidget object that holds everything
        """
        # widget that holds title and table
        wrap = QWidget()

        # set title
        title = QLabel(f"{img_out} → {img_in}")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # set table
        tbl = QTableWidget(len(transitions), 3, wrap)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        tbl.setMinimumWidth(300)
        # הוספת כותרות לעמודות (לא חובה, אבל נוח)
        tbl.setHorizontalHeaderLabels(["Move", "State", "Duration"])
        # tbl.verticalHeader().setVisible(False)

        for row, transition in enumerate(transitions):
            tbl.setItem(row, 0, QTableWidgetItem(str(transition.move)))
            tbl.setItem(row, 1, QTableWidgetItem(str(transition.state)))
            tbl.setItem(row, 2, QTableWidgetItem(str(transition.duration)))

        btn_remove = QPushButton("מחק SK")
        # btn_remove.clicked.connect(lambda _, card_num = card_number: self._remove_sk(card_num))
        btn_remove.setObjectName("remove_button")

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addSpacing(10)
        column_layout.addWidget(btn_remove)

        wrap.setLayout(column_layout)

        wrap.img_out = img_out
        wrap.img_in = img_in

        return wrap

    def _filter(self):
        move_in = self.move_in_combo_center.currentText()
        move_out = self.move_out_combo_center.currentText()

        for wrap in self.table_wrap_list:
            wrap.hide()

            show_wrap = True
            if move_out != "-" and wrap.img_out != move_out:
                show_wrap = False
            if move_in != "-" and wrap.img_in != move_in:
                show_wrap = False

            if show_wrap:
                wrap.show()

    def _reset(self):
        #
        self.move_out_combo_top.blockSignals(True)
        self.move_in_combo_top.blockSignals(True)
        self.move_out_combo_center.blockSignals(True)
        self.move_in_combo_center.blockSignals(True)

        #
        self.move_out_combo_top.clear()
        self.move_in_combo_top.clear()

        self.move_out_combo_center.clear()
        self.move_in_combo_center.clear()

        #
        self.move_out_combo_top.blockSignals(False)
        self.move_in_combo_top.blockSignals(False)
        self.move_out_combo_center.blockSignals(False)
        self.move_in_combo_center.blockSignals(False)

        #
        for table in self.table_wrap_list:
            table.deleteLater()





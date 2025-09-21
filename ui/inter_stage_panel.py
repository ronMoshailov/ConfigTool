from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QComboBox, QLabel, QCheckBox, QHBoxLayout, QVBoxLayout, QPushButton, QFrame, \
    QTableWidget, QAbstractItemView, QScrollArea, QTableWidgetItem, QHeaderView, QMessageBox, QLineEdit

from config.special import init_scroll
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

        # =============== QFrame =============== #
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
        self.scroll_area = init_scroll(self.tables_layout)

        # =============== QPushButton =============== #
        btn = QPushButton("עדכן")
        btn.clicked.connect(lambda _: self._update())
        btn.setObjectName("update_button")

        # =============== Root Layout =============== #
        root_layout.addLayout(top_layout)
        root_layout.addWidget(line)
        root_layout.addLayout(combo_layout)
        root_layout.addWidget(self.scroll_area)
        root_layout.addWidget(btn)

        # =============== Self =============== #
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
        all_images_name_list = [image.image_name for image in all_images]

        self.move_out_combo_top.addItems(["-"] + all_images_name_list)
        self.move_in_combo_top.addItems(["-"] + all_images_name_list)

        self.move_out_combo_center.addItems(["-"] + all_images_name_list)
        self.move_in_combo_center.addItems(["-"] + all_images_name_list)

        # create the tables
        out_in_images = [(inter_stage.image_out, inter_stage.image_in, inter_stage.transitions) for inter_stage in all_inter_stages]

        for img_out, img_in, transition in out_in_images:
            wrap = self._init_table(img_out, img_in, transition)
            self.table_wrap_list.append(wrap)
            self.tables_layout.addWidget(wrap)
        self.show()

    def _create_top_layout(self):

        layout = QHBoxLayout()

        btn = QPushButton("הוסף מעבר")
        btn.clicked.connect(self._add_inter_stage)
        btn.setObjectName("add_inter_stage_button")

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
        layout.addLayout(move_in_layout)
        layout.addLayout(move_out_layout)

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
        tbl = QTableWidget(len(transitions), 4, wrap)
        tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)
        tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)
        wrap.setFixedWidth(326)

        # הוספת כותרות לעמודות (לא חובה, אבל נוח)
        tbl.setHorizontalHeaderLabels(["Move", "State", "Duration", "Remove"])
        # tbl.verticalHeader().setVisible(False)
        tbl.cellClicked.connect(self._toggle_state)
        tbl.cellChanged.connect(self._validate_number_column)

        # set header
        header = tbl.horizontalHeader()
        header.setSectionResizeMode(0, QHeaderView.ResizeMode.ResizeToContents)
        header.setSectionResizeMode(1, QHeaderView.ResizeMode.ResizeToContents)
        header.setSectionResizeMode(2, QHeaderView.ResizeMode.ResizeToContents)

        for row, transition in enumerate(transitions):
            # col 1
            combo_widget = QComboBox()
            combo_widget.addItems([move.name for move in self.data_controller.get_all_moves()])
            combo_widget.setCurrentText(transition.move)  # או combo_widget.setCurrentIndex(1)

            remove_btn = QPushButton("❌")
            remove_btn.clicked.connect(lambda _, t=tbl: self._remove_row(t))

            color_widget = QTableWidgetItem("🔴" if str(transition.state) == "ROT" else "🟢")
            color_widget.setFlags(color_widget.flags() & ~Qt.ItemFlag.ItemIsEditable)  # מסיר את האפשרות לערוך
            color_widget.setTextAlignment(Qt.AlignmentFlag.AlignCenter)

            tbl.setCellWidget(row, 0, combo_widget)
            tbl.setItem(row, 1, color_widget)
            duration_table_widget = QTableWidgetItem(str(transition.duration))
            duration_table_widget.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            tbl.setItem(row, 2, duration_table_widget)
            tbl.setCellWidget(row, 3, remove_btn)

        len_textbox = QLineEdit()
        len_textbox.setPlaceholderText("אורך מעבר")

        add_action_btn = QPushButton("הוסף פעולה")
        add_action_btn.clicked.connect(lambda _, t=tbl: self._add_row(t))
        add_action_btn.setObjectName("add_action_button")

        remove_btn = QPushButton("מחק מעבר")
        remove_btn.clicked.connect(lambda _,: self._remove_inter_stage(wrap))
        remove_btn.setObjectName("remove_button")

        btn_layout = QHBoxLayout()
        btn_layout.addWidget(add_action_btn)
        btn_layout.addWidget(remove_btn)

        # set layout
        column_layout = QVBoxLayout()
        column_layout.addWidget(title)
        column_layout.addWidget(tbl)
        column_layout.addSpacing(10)
        column_layout.addWidget(len_textbox)
        column_layout.addLayout(btn_layout)

        wrap.setLayout(column_layout)

        wrap.table = tbl
        wrap.img_out = img_out
        wrap.img_in = img_in
        wrap.len_textbox = len_textbox

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
            # self.tables_layout.removeWidget(table)  # remove from layout
            # table.setParent(None)  # release the child from the father (helps in the child removed before the father now)
            table.deleteLater()

        self.table_wrap_list.clear()  # לרוקן לגמרי

    def _add_inter_stage(self):
        move_in = self.move_in_combo_top.currentText()
        move_out = self.move_out_combo_top.currentText()

        # check if empty value
        if move_in == "-" or move_out == "-":
            return

        # check if inter stage already exist
        for wrap in self.table_wrap_list:
            if move_out == wrap.img_out and move_in == wrap.img_in:
                QMessageBox.critical(self, "שגיאה", "המעבר כבר קיים במערכת")
                return

        if self.data_controller.add_inter_stage(move_out, move_in):
            wrap = self._init_table(move_out, move_in, [])
            self.table_wrap_list.append(wrap)
            self.tables_layout.addWidget(wrap)
            self.tables_layout.addStretch()
            self.scroll_area.widget().update()  # רענון ה־scroll_area

    def _toggle_state(self, row, column):
        # אם זו העמודה של מצב (במקרה שלך 1)
        if column != 1:
            return

        tbl = self.sender()  # שולף את הטבלה שהוציאה את האירוע
        item = tbl.item(row, column)
        if not item:
            return

        # מחליף בין הצבעים
        current = item.text()
        if current == "🔴":
            item.setText("🟢")
        else:
            item.setText("🔴")

    def _remove_row(self, tbl):
        btn = self.sender()  # הכפתור שנלחץ
        if btn is None:
            return

        # מצא את השורה שהכפתור נמצא בה
        row = -1
        for r in range(tbl.rowCount()):
            if tbl.cellWidget(r, 3) == btn:  # נניח שהכפתור בעמודה 3
                row = r
                break

        if row != -1:
            tbl.removeRow(row)

    def _add_row(self, tbl):
        row = tbl.rowCount()
        tbl.insertRow(row)

        combo_widget = QComboBox()
        combo_widget.addItems([move.name for move in self.data_controller.get_all_moves()])
        combo_widget.setCurrentIndex(0)

        remove_btn = QPushButton("❌")
        remove_btn.clicked.connect(lambda _, t=tbl: self._remove_row(tbl))

        tbl.setCellWidget(row, 0, combo_widget)
        widget_color = QTableWidgetItem("🔴")
        widget_color.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        tbl.setItem(row, 1, widget_color)
        number_color = QTableWidgetItem("0")
        number_color.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        tbl.setItem(row, 2, number_color)
        tbl.setCellWidget(row, 3, remove_btn)

    def _validate_number_column(self, row, column):
        # נבדוק אם זו העמודה 2
        if column != 2:
            return

        tbl = self.sender()  # שולף את הטבלה
        item = tbl.item(row, column)
        text = item.text()

        # אם זה לא מספר, החזר את הערך הקודם
        try:
            int(text)  # אם רוצים מספר שלם השתמשו ב int(text)
        except ValueError:
            QMessageBox.critical(self, "שגיאה", "ערך לא תקין, אנה הכנס מספר")
            item.setText("0")
            return

    def _update(self):
        # check if move exist twice
        for wrap in self.table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            table = wrap.table
            seen = set()  # מאגר של ערכים שכבר הופיעו
            row_count = table.rowCount()
            for row in range(row_count):
                combo = table.cellWidget(row, 0)  # נניח שזה QComboBox
                value = combo.currentText()  # הערך הנבחר
                if value in seen:
                    QMessageBox.critical(self, "שגיאה", f"המופע [{value}] מופיע לפחות פעמיים במעבר [{img_out} → {img_in}]")
                    return
                seen.add(value)


        # update the DB
        success, message = self.data_controller.update_inter_stage(self.table_wrap_list)
        if success:
            QMessageBox.information(self, "הצלחה", message)
            return
        QMessageBox.critical(self, "שגיאה", message)

    def _remove_inter_stage(self, wrap):
        # Confirm deletion
        reply = QMessageBox.question(
            self,
            "אישור מחיקה",
            "אתה בטוח שאתה רוצה למחוק את המעבר?",
            QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No
        )

        if reply == QMessageBox.StandardButton.No:
            return

        # Data
        img_out = wrap.img_out
        img_in = wrap.img_in

        # Remove
        success, message = self.data_controller.remove_inter_stage(img_out, img_in)
        if success:
            QMessageBox.information(self, "הודעה", message)
            self.table_wrap_list.remove(wrap)
            wrap.deleteLater()
            return
        QMessageBox.critical(self, "שגיאה", message)


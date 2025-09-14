from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QComboBox, QTextEdit, QLabel, QCheckBox, QHBoxLayout, QVBoxLayout, QPushButton, \
    QStackedWidget

from data_class.program_infp import ProgramInfo
from entities.program_scene import ProgramScene
from config.style import program_panel_style

class ProgramPanel(QWidget):
    def __init__(self):
        super().__init__()

        # =============== Data =============== #
        self.program_info_list = []
        empty_page = QWidget()

        # =============== QStackedWidget =============== #
        self.scene_stack = QStackedWidget()   # מיכל לכל הסצנות
        self.scene_stack.addWidget(empty_page)

        self.program_info_list.append(self.scene_stack)
        self.program_info_list.append(ProgramInfo(prog_num=0, prog_scene=empty_page, cycle_time=0))

        # =============== QLabel =============== #
        title = QLabel("מעברים")
        title.setObjectName("title")
        title.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # =============== QPushButton =============== #
        btn = QPushButton("עדכן")

        # =============== QComboBox =============== #
        self.combo = QComboBox()
        self.combo.setFixedWidth(50)
        self.combo.setMaximumHeight(40)
        self.combo.addItem("-")
        self.combo.currentTextChanged.connect(self._on_combo_changed)

        # =============== Data =============== #
        self.textbox = QTextEdit()
        self.textbox.setFixedWidth(100)
        self.textbox.setMaximumHeight(40)

        # =============== Layouts =============== #
        settings_layout     = self._create_settings_layout()
        active_prog_layout  = self._create_active_prog_layout()
        root_layout         = QVBoxLayout()

        # =============== Root Layout =============== #
        root_layout.addWidget(title)
        root_layout.addLayout(settings_layout)
        root_layout.addLayout(active_prog_layout)
        root_layout.addWidget(self.scene_stack)   # מחזיק את כל ה־scenes
        root_layout.addWidget(btn)

        # =============== Self =============== #
        self.setLayout(root_layout)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        self.setStyleSheet(program_panel_style)
        self.hide()

    def show_panel(self):
        # self.scene._show_panel()
        self.show()

    def _create_active_prog_layout(self):
        horizontal_layout = QHBoxLayout()
        horizontal_layout.addStretch()

        for i in range(1, 33):
            vertical_layout = QVBoxLayout()
            label = QLabel()
            label.setText(str(i))
            label.setAlignment(Qt.AlignmentFlag.AlignCenter)
            checkbox = QCheckBox()
            checkbox.setChecked(False)
            vertical_layout.addWidget(label)
            vertical_layout.addWidget(checkbox)
            horizontal_layout.addLayout(vertical_layout)

            checkbox.stateChanged.connect(lambda state, num=i: self._update_checkbox(state, num))

        horizontal_layout.addStretch()
        return horizontal_layout

    def _create_settings_layout(self):
        prog_num_label = QLabel("תוכנית")
        cycle_tile_label = QLabel("זמן מחזור")
        update_btn = QPushButton("עדכן זמן מחזור")

        buttons_layout = QHBoxLayout()

        buttons_layout.addStretch()
        buttons_layout.addWidget(update_btn)
        buttons_layout.addSpacing(10)
        buttons_layout.addWidget(self.textbox)
        buttons_layout.addSpacing(10)
        buttons_layout.addWidget(cycle_tile_label)
        buttons_layout.addSpacing(100)
        buttons_layout.addWidget(self.combo)
        buttons_layout.addSpacing(10)
        buttons_layout.addWidget(prog_num_label)
        return buttons_layout

    def _update_checkbox(self, state, num):
        text = str(num)

        # if checked (need to add)
        if state == Qt.CheckState.Checked.value:   # אם סומן
            insert_index = 1    # מתחילים אחרי '-'

            # search the right index in combo to insert
            while insert_index < self.combo.count() and int(self.combo.itemText(insert_index)) < num:
                insert_index += 1
            self.combo.insertItem(insert_index, text)

            # create and add scene
            prog_scene = ProgramScene(self.textbox)
            self.program_info_list.append(ProgramInfo(prog_num=num, prog_scene=prog_scene, cycle_time=0))

            # add scene to stack
            self.scene_stack.addWidget(prog_scene)
        else:
            index = self.combo.findText(text)
            self.combo.removeItem(index)

    def _on_combo_changed(self, text):
        if text == "-":
            self.scene_stack.setCurrentIndex(0)  # מציג רק את הסצנה הזאת
            return
        else:
            for idx, item in enumerate (self.program_info_list):
                if idx == 0:
                    continue
                if item.prog_num == int(text):
                    self.scene_stack.setCurrentWidget(item.prog_scene)  # מציג רק את הסצנה הזאת
                    item.prog_scene._show_panel()
                    break




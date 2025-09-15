from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QComboBox, QTextEdit, QLabel, QCheckBox, QHBoxLayout, QVBoxLayout, QPushButton, \
    QStackedWidget

from data_class.program_info import ProgramInfo
from entities.program_scene import ProgramScene
from config.style import program_panel_style

class ProgramPanel(QWidget):
    """
    This class present the panel of the program panel.
    """
    def __init__(self):
        super().__init__()

        # =============== Data =============== #
        self.program_info_list = []
        empty_page = QWidget()

        # =============== QStackedWidget =============== #
        self.scene_stack = QStackedWidget()     # container that contain all the scenes

        # =============== QPushButton =============== #
        btn = QPushButton("עדכן")

        # =============== QComboBox =============== #
        self.combo = QComboBox()
        self.combo.setFixedWidth(50)
        self.combo.setMaximumHeight(40)
        self.combo.addItem("-")
        self.combo.currentTextChanged.connect(self._on_combo_changed)

        # =============== QTextEdit =============== #
        self.cycle_textbox = QTextEdit()
        self.cycle_textbox.setFixedWidth(100)
        self.cycle_textbox.setMaximumHeight(40)

        # =============== Layouts =============== #
        settings_layout     = self._create_settings_layout()
        active_prog_layout  = self._create_checkbox_prog_layout()
        root_layout         = QVBoxLayout()

        # =============== Root Layout =============== #
        root_layout.addLayout(settings_layout)
        root_layout.addLayout(active_prog_layout)
        root_layout.addWidget(self.scene_stack)
        root_layout.addWidget(btn)

        # =============== Self =============== #
        self.scene_stack.addWidget(empty_page)  # add empty widget for '-' option
        self.program_info_list.append(ProgramInfo(prog_num=0, prog_scene=empty_page, cycle_time=0))

        self.setLayout(root_layout)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        self.setStyleSheet(program_panel_style)
        self.hide()

    def show_panel(self):
        # self.scene._show_panel()
        self.show()

    def _create_checkbox_prog_layout(self):
        """
        This method build horizontal layout of the checkboxes on the top.

        :return: The layout of the checkboxes on the top.
        """
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
        """
        This method build horizontal layout of the settings on the top.

        :return: The layout of the settings.
        """
        # =============== QLabel =============== #
        prog_num_label = QLabel("תוכנית")
        cycle_tile_label = QLabel("זמן מחזור")

        # =============== QPushButton =============== #
        buttons_layout = QHBoxLayout()

        # =============== QPushButton =============== #
        buttons_layout.addStretch()
        buttons_layout.addWidget(self.cycle_textbox)
        buttons_layout.addSpacing(10)
        buttons_layout.addWidget(cycle_tile_label)
        buttons_layout.addSpacing(100)
        buttons_layout.addWidget(self.combo)
        buttons_layout.addSpacing(10)
        buttons_layout.addWidget(prog_num_label)
        return buttons_layout

    def _update_checkbox(self, state, num):
        """
        This method is called when the checkbox is changed and update the combobox.

        :param state: current state of the checkbox.
        :param num: number of the checkbox.
        :return: None
        """
        text = str(num)

        # if checked (need to add)
        if state == Qt.CheckState.Checked.value:   # אם סומן
            insert_index = 1    # מתחילים אחרי '-'

            # search the right index in combo to insert
            while insert_index < self.combo.count() and int(self.combo.itemText(insert_index)) < num:
                insert_index += 1
            self.combo.insertItem(insert_index, text)

            # create and add scene
            is_exist = False

            # check if for the option is there any scene
            for idx, scene in enumerate(self.program_info_list):
                if scene.prog_num == num:
                    is_exist = True

            # if not found create and add the scene
            if not is_exist:
                prog_scene = ProgramScene(self.cycle_textbox)
                self.program_info_list.append(ProgramInfo(prog_num=num, prog_scene=prog_scene, cycle_time=0))

                # add scene to stack
                self.scene_stack.addWidget(prog_scene)
        else:
            # find and remove the option from the combo
            index = self.combo.findText(text)
            self.combo.removeItem(index)

    def _on_combo_changed(self, text):
        """
        This method is called when the combo box is changed.

        :param text: The value of the combo box.
        :return: None
        """
        if text == "-":
            # if the chosen option is the first
            self.scene_stack.setCurrentIndex(0)
            return
        else:
            for idx, item in enumerate (self.program_info_list):
                if idx == 0:
                    # The first element is 'empty_widget' so 'item.prog_scene' crush
                    continue
                if item.prog_num == int(text):
                    self.scene_stack.setCurrentWidget(item.prog_scene)  # show this scene
                    item.prog_scene._show_panel()
                    break




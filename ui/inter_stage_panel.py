from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QComboBox, QTextEdit, QLabel, QCheckBox, QHBoxLayout, QVBoxLayout, QPushButton, \
    QStackedWidget, QLineEdit, QFrame
from click import clear

from controllers.data_controller import DataController
from data_class.program_info import ProgramInfo
from entities.program_scene import ProgramScene
from config.style import program_panel_style


def _create_top_layout():
    layout = QHBoxLayout()

    btn = QPushButton("הוסף")

    move_out_layout = QVBoxLayout()
    move_out_label = QLabel("מופע יוצא")
    move_out_combo = QComboBox()
    move_out_layout.addWidget(move_out_label)
    move_out_layout.addWidget(move_out_combo)

    move_in_layout = QVBoxLayout()
    move_in_label = QLabel("מופע נכנס")
    move_in_combo = QComboBox()
    move_in_layout.addWidget(move_in_label)
    move_in_layout.addWidget(move_in_combo)

    layout.addStretch()
    layout.addWidget(btn)
    layout.addLayout(move_out_layout)
    layout.addLayout(move_in_layout)

    return layout


def _create_combo_layout():
    layout = QHBoxLayout()

    # move in
    move_in_layout = QVBoxLayout()
    move_in_label = QLabel("מופע נכנס")
    move_in_combo = QComboBox()

    move_in_layout.addWidget(move_in_label)
    move_in_layout.addWidget(move_in_combo)


    # move out
    move_out_layout = QVBoxLayout()
    move_out_label = QLabel("מופע יוצא")
    move_out_combo = QComboBox()

    move_out_layout.addWidget(move_out_label)
    move_out_layout.addWidget(move_out_combo)

    # clear
    clear_layout = QVBoxLayout()
    clear_label = QLabel("נקה מופעים")
    clear_checkbox = QCheckBox()

    clear_layout.addWidget(clear_label)
    clear_layout.addWidget(clear_checkbox)

    #
    layout.addStretch()
    layout.addLayout(clear_layout)
    layout.addLayout(move_in_layout)
    layout.addLayout(move_out_layout)

    return layout

class InterStagePanel(QWidget):
    """
    This class present the panel of the inter stage panel.
    """
    def __init__(self):
        super().__init__()

        # =============== Data =============== #
        self.data_controller = DataController()

        line = QFrame()
        line.setFrameShape(QFrame.Shape.HLine)  # קו אופקי
        # line.setFrameShadow(QFrame.Shadow.Sunken)  # מראה "שקוע"
        line.setStyleSheet("background-color: black;")
        line.setFixedHeight(1)  # עובי הקו

        # =============== Layout =============== #
        top_layout = _create_top_layout()
        combo_layout = _create_combo_layout()
        root_layout         = QVBoxLayout()

        # =============== QPushButton =============== #
        btn = QPushButton("עדכן")

        root_layout.addLayout(top_layout)
        root_layout.addWidget(line)
        root_layout.addLayout(combo_layout)
        root_layout.addStretch()
        root_layout.addWidget(btn)

        # # =============== Self =============== #
        self.setLayout(root_layout)
        self.setAttribute(Qt.WidgetAttribute.WA_StyledBackground, True)
        self.setObjectName("root")
        # self.setStyleSheet(program_panel_style)
        self.hide()

    def show_panel(self):
        # self.scene._show_panel()
        self.show()
    #
    # def _create_checkbox_prog_layout(self):
    #     """
    #     This method build horizontal layout of the checkboxes on the top.
    #
    #     :return: The layout of the checkboxes on the top.
    #     """
    #     horizontal_layout = QHBoxLayout()
    #     horizontal_layout.addStretch()
    #
    #     for i in range(1, 33):
    #         vertical_layout = QVBoxLayout()
    #         label = QLabel()
    #         label.setText(str(i))
    #         label.setAlignment(Qt.AlignmentFlag.AlignCenter)
    #         checkbox = QCheckBox()
    #         checkbox.setChecked(False)
    #         vertical_layout.addWidget(label)
    #         vertical_layout.addWidget(checkbox)
    #         horizontal_layout.addLayout(vertical_layout)
    #
    #         checkbox.stateChanged.connect(lambda state, num=i: self._update_checkbox(state, num))
    #
    #     horizontal_layout.addStretch()
    #     return horizontal_layout
    #
    # def _create_settings_layout(self):
    #     """
    #     This method build horizontal layout of the settings on the top.
    #
    #     :return: The layout of the settings.
    #     """
    #     # =============== QLabel =============== #
    #     prog_num_label = QLabel("תוכנית")
    #     cycle_tile_label = QLabel("זמן מחזור")
    #
    #     # =============== QPushButton =============== #
    #     buttons_layout = QHBoxLayout()
    #
    #     # =============== QPushButton =============== #
    #     buttons_layout.addStretch()
    #     buttons_layout.addWidget(self.cycle_textbox)
    #     buttons_layout.addSpacing(10)
    #     buttons_layout.addWidget(cycle_tile_label)
    #     buttons_layout.addSpacing(100)
    #     buttons_layout.addWidget(self.combo)
    #     buttons_layout.addSpacing(10)
    #     buttons_layout.addWidget(prog_num_label)
    #     return buttons_layout
    #
    # def _update_checkbox(self, state, num):
    #     """
    #     This method is called when the checkbox is changed and update the combobox.
    #
    #     :param state: current state of the checkbox.
    #     :param num: number of the checkbox.
    #     :return: None
    #     """
    #     text = str(num)
    #
    #     # if checked (need to add)
    #     if state == Qt.CheckState.Checked.value:   # אם סומן
    #         insert_index = 1    # מתחילים אחרי '-'
    #
    #         # search the right index in combo to insert
    #         while insert_index < self.combo.count() and int(self.combo.itemText(insert_index)) < num:
    #             insert_index += 1
    #         self.combo.insertItem(insert_index, text)
    #
    #         # create and add scene
    #         is_exist = False
    #
    #         # check if for the option is there any scene
    #         for idx, scene in enumerate(self.program_info_list):
    #             if scene.prog_num == num:
    #                 is_exist = True
    #
    #         # if not found create and add the scene
    #         if not is_exist:
    #             prog_scene = ProgramScene(self.cycle_textbox)
    #             self.program_info_list.append(ProgramInfo(prog_num=num, prog_scene=prog_scene, cycle_time=0))
    #
    #             # add scene to stack
    #             self.scene_stack.addWidget(prog_scene)
    #     else:
    #         # find and remove the option from the combo
    #         index = self.combo.findText(text)
    #         self.combo.removeItem(index)
    #
    # def _on_combo_changed(self, text):
    #     """
    #     This method is called when the combo box is changed.
    #
    #     :param text: The value of the combo box.
    #     :return: None
    #     """
    #     if text == "-":
    #         # if the chosen option is the first
    #         self.scene_stack.setCurrentIndex(0)
    #         return
    #     else:
    #         for idx, item in enumerate (self.program_info_list):
    #             if idx == 0:
    #                 # The first element is 'empty_widget' so 'item.prog_scene' crush
    #                 continue
    #             if item.prog_num == int(text):
    #                 self.scene_stack.setCurrentWidget(item.prog_scene)  # show this scene
    #                 item.prog_scene._show_panel()
    #                 break




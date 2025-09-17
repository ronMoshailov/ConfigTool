from PyQt6.QtWidgets import QHBoxLayout, QMainWindow, QWidget, QSizePolicy

from config.style import main_window_style

from controllers.ui_controller import UIController

from ui.navigator_panel import NavigatorPanel


class MainWindow(QMainWindow):
    """
    Main application window for the Config Tool.
    Holds all panels (set move, min green, matrix, SK, navigator etc.) in a single layout.
    """

    def __init__(self):
        super().__init__()

        # =============== controllers =============== #
        self.ui_controller = UIController()

        # =============== widgets =============== #
        root = QWidget()

        # =============== panels =============== #
        set_move_panel = self.ui_controller.get_panel_by_name("set_move")
        set_move_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        min_green_panel = self.ui_controller.get_panel_by_name("min_green")
        min_green_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        matrix_panel = self.ui_controller.get_panel_by_name("matrix")
        matrix_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        sk_panel = self.ui_controller.get_panel_by_name("sk")
        sk_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        detector_panel = self.ui_controller.get_panel_by_name("detector")
        detector_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        navigator_panel = NavigatorPanel()

        schedule_panel = self.ui_controller.get_panel_by_name("schedule")
        schedule_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        image_panel = self.ui_controller.get_panel_by_name("image")
        image_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        program_panel = self.ui_controller.get_panel_by_name("program")
        program_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        settings_panel = self.ui_controller.get_panel_by_name("settings")
        settings_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        inter_stage_panel = self.ui_controller.get_panel_by_name("inter_stage")
        inter_stage_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        # =============== layouts =============== #
        root_layout = QHBoxLayout()

        root_layout.addWidget(set_move_panel, 70)
        root_layout.addWidget(min_green_panel, 70)
        root_layout.addWidget(matrix_panel, 70)
        root_layout.addWidget(sk_panel, 70)
        root_layout.addWidget(detector_panel, 70)
        root_layout.addWidget(schedule_panel, 70)
        root_layout.addWidget(image_panel, 70)
        root_layout.addWidget(program_panel, 70)
        root_layout.addWidget(settings_panel, 70)
        root_layout.addWidget(inter_stage_panel, 70)
        root_layout.addStretch(1)
        root_layout.addWidget(navigator_panel, 10)
        root.setLayout(root_layout)

        # =============== root =============== #
        self.setCentralWidget(root)
        self.setStyleSheet(main_window_style)
        self.setWindowTitle("Config Tool")


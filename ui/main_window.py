from PyQt6.QtWidgets import QHBoxLayout, QMainWindow, QWidget, QSpacerItem, QSizePolicy

from controllers.data_controller import DataController
from controllers.ui_controller import UIController
from ui.navigator_panel import NavigatorPanel


class MainWindow(QMainWindow):
    """
    Main application window for the Config Tool.
    Holds all panels (set move, min green, matrix, SK, navigator) in a single layout.
    """

    def __init__(self):
        """
        Initialize the main window, create controllers, panels, and layout.
        """
        super().__init__()

        # --- controllers --- #
        self.data_controller = DataController()
        self.ui_controller = UIController()

        # --- style --- #
        root_style = """
        QMainWindow {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #cce0ff, stop:1 #e6f2fe);
        }
        """

        # =============== widgets =============== #
        root = QWidget()

        # =============== panels =============== #
        set_move_panel = self.ui_controller.get_set_move_layout()
        set_move_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        min_green_panel = self.ui_controller.get_min_green_layout()
        min_green_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        matrix_panel = self.ui_controller.get_matrix_layout()
        matrix_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        sk_panel = self.ui_controller.get_sk_layout()
        sk_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        detector_panel = self.ui_controller.get_detector_panel()
        detector_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        navigator_panel = NavigatorPanel()
        self.data_controller.set_log_textbox(navigator_panel.log_textbox)   # tell the data controller where to write comments

        schedule_panel = self.ui_controller.get_schedule_panel()
        schedule_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        image_panel = self.ui_controller.get_image_panel()
        image_panel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

        # =============== layouts =============== #
        root_layout = QHBoxLayout()

        root_layout.addWidget(set_move_panel, 70)
        root_layout.addWidget(min_green_panel, 70)
        root_layout.addWidget(matrix_panel, 70)
        root_layout.addWidget(sk_panel, 70)
        root_layout.addWidget(detector_panel, 70)
        root_layout.addWidget(schedule_panel, 70)
        root_layout.addWidget(image_panel, 70)
        root_layout.addStretch(1)
        root_layout.addWidget(navigator_panel, 20)
        root.setLayout(root_layout)

        # =============== root =============== #
        self.setCentralWidget(root)
        self.setStyleSheet(root_style)
        self.setWindowTitle("Config Tool")

        # print("** main window was set successfully")

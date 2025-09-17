from ui.detector_panel import DetectorPanel
from ui.image_panel import ImagePanel
from ui.inter_stage_panel import InterStagePanel
from ui.matrix_panel import MatrixPanel
from ui.min_green_panel import MinGreenLayout
from ui.program_panel import ProgramPanel
from ui.schedule_panel import SchedulePanel
from ui.move_panel import MovePanel
from ui.settings_panel import SettingsPanel
from ui.sk_panel import SkPanel


class UIController:
    _instance = None

    # --------------- Construction --------------- #
    def __new__(cls):
        if cls._instance is None:                                       # checks if there is an instance of the class
            cls._instance = super(UIController, cls).__new__(cls)       # create new instance and store him in _instance before __init__
            cls.set_move_layout = MovePanel()
            cls.min_green_layout = MinGreenLayout()
            cls.matrix_layout = MatrixPanel()
            cls.sk_layout = SkPanel()
            cls.detector_panel = DetectorPanel()
            cls.schedule_panel = SchedulePanel()
            cls.image_panel = ImagePanel()
            cls.program_panel = ProgramPanel()
            cls.settings_panel = SettingsPanel()
            cls.inter_stage_panel = InterStagePanel()
        return cls._instance                                            # return _instance

    # --------------- get methods --------------- #
    def get_panel_by_name(self, panel_name: str):
        panel_mapping = {
            "settings": self.settings_panel,
            "set_move": self.set_move_layout,
            "inter_stage": self.inter_stage_panel,
            "min_green": self.min_green_layout,
            "matrix": self.matrix_layout,
            "sk": self.sk_layout,
            "detector": self.detector_panel,
            "schedule": self.schedule_panel,
            "image": self.image_panel,
            "program": self.program_panel,
        }
        return panel_mapping.get(panel_name)

    # --------------- general methods --------------- #
    def show_panel_by_name(self, panel_name):
        panel_mapping = {
            "settings": self.settings_panel,
            "set_move": self.set_move_layout,
            "inter_stage": self.inter_stage_panel,
            "min_green": self.min_green_layout,
            "matrix": self.matrix_layout,
            "sk": self.sk_layout,
            "detector": self.detector_panel,
            "schedule": self.schedule_panel,
            "image": self.image_panel,
            "program": self.program_panel,
        }

        for name, panel in panel_mapping.items():
            if name == panel_name:
                panel.show_panel()
            else:
                panel.hide()


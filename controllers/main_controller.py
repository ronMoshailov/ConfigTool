from managers.cards_manager import CardsManager
from managers.config_manager import ConfigManager
from managers.paths_manager import PathsManager
from ui.phase_panel_window import PhasePanelWindow


class MainController:
    def __init__(self):
        # =============== Managers =============== #
        self.config_manager = ConfigManager()
        self.path_manager = PathsManager()
        self.card_manager = CardsManager()

        # =============== Layouts =============== #
        self.phase_layout = PhasePanelWindow()

    # =============== initialize =============== #
    def initialize(self, btn_list):
        """
        This method initialize the paths, moves, ...

        :return: None
        """
        self.reset()
        self.path_manager.scan_set_paths()
        is_found = self.config_manager.scan_set_moves(self.path_manager.get_path_init_tk1())
        if is_found is False:
            print(f"Moves not found")
            return None
        for btn in btn_list:
            btn.setDisabled(False)

    # =============== getter =============== #
    def get_phase_layout(self):
        return self.phase_layout

    # =============== setter =============== #

    # =============== methods =============== #
    def show_phase_panel(self):
        self.phase_layout.show_panel(self.config_manager.get_all_phases())

    def reset(self):
        self.config_manager.reset()
        self.path_manager.reset()
        self.card_manager.reset()

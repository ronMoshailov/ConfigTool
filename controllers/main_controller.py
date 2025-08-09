from managers.cards_manager import CardsManager
from managers.config_manager import ConfigManager
from managers.paths_manager import PathsManager
from ui.phase_panel_window import PhasePanelWindow


class MainController:
    def __init__(self):
        """
        This method runs when the object initialized.
        """
        # =============== Managers =============== #
        self.config_manager = ConfigManager()
        self.path_manager = PathsManager()
        self.card_manager = CardsManager()

        # =============== Layouts =============== #
        self.phase_layout = PhasePanelWindow(self)

    # =============== initialize =============== #
    def initialize(self, btn_list):
        """
        This method initialize the paths, moves, ...

        :return: None
        """
        self.reset()
        # TO DO:
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
        self.phase_layout.show_panel(self.config_manager.get_all_moves())

    def reset(self):
        self.config_manager.reset()
        self.path_manager.reset()
        self.card_manager.reset()

    def remove_move(self, move_name):
        self.config_manager.remove_move(move_name)

    def add_move(self, value, is_main):
        self.config_manager.add_move(value, is_main)


from managers.cards_manager import CardsManager
from managers.config_manager import ConfigManager
from managers.paths_manager import PathsManager


class MainController:
    def __init__(self):
        # Managers
        self.config_manager = ConfigManager()
        self.path_manager = PathsManager()
        self.card_manager = CardsManager()

    # Set phases
    def initialize(self):
        """
        This method initialize the paths, moves, ...

        :return: None
        """
        self.path_manager.set_paths()
        self.config_manager


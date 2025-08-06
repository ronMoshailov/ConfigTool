from classes.managers.CardsManager import CardsManager
from classes.managers.ConfigManager import ConfigManager
from classes.managers.PathsManager import PathsManager


class MainController:
    def __init__(self):
        self.config_manager = ConfigManager()
        self.path_manager = PathsManager()
        self.card_manager = CardsManager()




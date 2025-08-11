from managers.config_manager import ConfigManager
from managers.paths_manager import PathsManager


class DataController:
    _instance = None

    # =============== class methods =============== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:                                       # checks if there is an instance of the class
            cls._instance = super(DataController, cls).__new__(cls)     # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                    # run _init
        return cls._instance                                            # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        # =============== Managers =============== #
        self.config_manager = ConfigManager()
        self.path_manager = PathsManager()
        # self.card_manager = CardsManager()



    # =============== initialize =============== #
    def initialize(self, btn_list):
        """
        This method initialize the paths, moves, ...

        :return: False is failed, True is success.
        """
        # set new paths
        is_failed = self.path_manager.scan_set_paths()
        if is_failed is False:
            return False

        # clean all data
        self.config_manager.reset()
        # self.card_manager.reset()

        # scan for moves
        is_found = self.config_manager.scan_set_moves(self.path_manager.get_path_init_tk1())
        if is_found is False:
            print(f"Moves not found")
            return False

        # disable buttons
        for btn in btn_list:
            btn.setDisabled(False)
        return True
    # =============== getter =============== #



    # =============== methods =============== #

    def reset(self):
        """
        This method resets all the data.
        """
        self.path_manager.reset()
        self.config_manager.reset()
        # self.card_manager.reset()

    def get_all_moves(self):
        """
        This method returns all the moves.

        :return: list of all moves.
        """
        print("get_all_moves")
        return self.config_manager.get_all_moves()

    def get_add_move_ref(self):
        return self.config_manager.add_move












    #
    #
    #
    #
    #
    #
    #
    # # =============== I don't know why these are here =============== #
    #
    #
    # def remove_move(self, move_name):
    #     self.config_manager.remove_move(move_name)
    #
    # def add_move(self, value, is_main):
    #     self.config_manager.add_move(value, is_main)
    #

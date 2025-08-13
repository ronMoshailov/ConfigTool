from managers.config_manager import DataManager
from managers.paths_manager import PathsManager


class DataController:
    _instance = None

    # =============== class methods =============== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:  # checks if there is an instance of the class
            cls._instance = super(DataController, cls).__new__(
                cls)  # create new instance and store him in _instance before __init__
            cls._instance.__init__()  # run _init
            print("** data controller was set successfully")
        return cls._instance  # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        # =============== Managers =============== #
        self.data_manager = DataManager()
        self.path_manager = PathsManager()

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
        self.data_manager.reset()
        # self.card_manager.reset()

        # scan for moves
        is_found = self.data_manager.init_moves_from_file(self.path_manager.get_path_init_tk1())
        if is_found is False:
            print(f"Moves not found")
            return False

        # disable buttons
        for btn in btn_list:
            btn.setDisabled(False)
        return True

    # =============== add =============== #
    def add_move(self, move_name, move_type, is_main, min_green):
        self.data_manager.add_move(move_name, move_type, is_main, min_green)

    # =============== remove =============== #
    def remove_move(self, move_name):
        self.data_manager.remove_move(move_name)

    # =============== get =============== #
    def get_all_moves(self):
        """
        This method returns all the moves.

        :return: list of all moves.
        """
        print("get_all_moves")
        return self.data_manager.get_all_moves()

    # =============== update =============== #
    def update_min_green(self, name, value):
        self.data_manager.update_min_green(name, value)

    # =============== methods =============== #

    def reset(self):
        """
        This method resets all the data.
        """
        self.path_manager.reset()
        self.data_manager.reset()
        # self.card_manager.reset()

    # def get_add_move_ref(self):
    #     """
    #     This method returns a reference to the 'add move' method.
    #
    #     :return: reference to the 'add move' method.
    #     """
    #     return self.data_manager.add_move
    #
    # def get_remove_move_ref(self):
    #     """
    #     This method returns a reference to the 'remove move' method.
    #
    #     :return: reference to the 'remove move' method.
    #     """
    #     return self.data_manager.remove_move

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
    #     self.data_manager.remove_move(move_name)
    #
    # def add_move(self, value, is_main):
    #     self.data_manager.add_move(value, is_main)
    #

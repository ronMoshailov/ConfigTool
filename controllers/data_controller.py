from managers.data_manager import DataManager
from managers.paths_manager import PathsManager
from managers.sk_manager import SkManager


class DataController:
    _instance = None

    # =========================================== #
    #                class methods                #
    # =========================================== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:  # checks if there is an instance of the class
            cls._instance = super().__new__(cls)  # create new instance and store him in _instance before __init__
            cls.data_manager = DataManager()
            cls.path_manager = PathsManager()
            cls.sk_manager = []
            print("** data controller was set successfully")
        return cls._instance  # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        pass


    # =========================================== #
    #                 add methods                 #
    # =========================================== #
    def add_move(self, move_name, move_type, is_main, min_green):
        self.data_manager.add_move(move_name, move_type, is_main, min_green)

    # =========================================== #
    #                 get methods                 #
    # =========================================== #
    def get_all_moves(self):
        """
        This method returns all the moves.

        :return: list of all moves.
        """
        return self.data_manager.get_all_moves()

    def get_all_matrix_cells(self):
        return self.data_manager.get_all_matrix_cells()
    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_min_green(self, name, value):
        """
        This method updates the minimum green time of a move.

        :param name: name of the move.
        :param value: minimum green time of the move.
        :return: None
        """
        self.data_manager.update_min_green(name, value)

    # =========================================== #
    #               remove methods                #
    # =========================================== #
    def remove_move(self, move_name):
        """
        This method removes a move.

        :param move_name: name of the move.
        :return: None
        """
        self.data_manager.remove_move(move_name)

    # =========================================== #
    #               general methods               #
    # =========================================== #
    def initialize(self, btn_list):
        init_moves = self.initialize_moves(btn_list)
        DataController.set_sk_list(self.path_manager.get_path_init())
        for sk in DataController.data_manager:
            sk.initialize_sk(self.path_manager.get_path_init())
        if init_moves:
            return True
        return False

    def initialize_moves(self, btn_list):
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

        # scan for moves
        is_found = self.data_manager.init_moves_from_file(self.path_manager.get_path_init_tk1())
        if is_found is False:
            print(f"Moves not found")
            return False

        self.data_manager.init_matrix(self.path_manager.get_path_init_tk1())

        # disable buttons
        for btn in btn_list:
            btn.setDisabled(False)
        return True

    @classmethod
    def set_sk_list(cls, path):
        print(f"DataController:\tset_sk_list\t[start] ")
        card_num = 1
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line:
                    print(f"Sk number: {card_num}")
                    cls.sk_manager.append(SkManager(card_num))
                    card_num = card_num + 1

        print(f"DataController:\tset_sk_list\t[end] ")


    def reset(self):
        """
        This method resets all the data.
        """
        self.path_manager.reset()
        self.data_manager.reset()


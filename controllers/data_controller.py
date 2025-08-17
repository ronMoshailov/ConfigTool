import logging

from managers.paths_manager import PathsManager
from managers.data_manager import DataManager
from managers.sk_manager import SkManager
from entities.log import Log

class DataController:
    sk_manager = None
    _instance = None

    # =========================================== #
    #                  Construction               #
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
    def add_move(self, move_name: str, move_type: str, is_main: bool, min_green: str = "0"):
        """
        This method add new move.

        :param move_name: name of the move.
        :param move_type: type of move.
        :param is_main: value to indicate if the move is main or not.
        :param min_green:
        :return: True is success, otherwise False.
        """
        print(f"**** [class] DataController:\t [method] add_move\t[start] ")
        if not self.is_move_valid(move_name, move_type):
            return False
        if not self.data_manager.add_move(move_name, move_type, is_main, min_green):
            return False
        print(f"**** [class] DataController:\t [method] add_move\t[end] ")
        return True

    # def add_sk(self):
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
        """
        This method returns the matrix cells.

        :return: list of the matrix cells.
        """
        return self.data_manager.get_all_matrix_cells()

    def get_sk_count(self):
        """
        This method returns the sk count.

        :return: sk count.
        """
        return len(self.sk_manager)

    def get_all_sk_channels(self, number_card: int):
        """
        This method returns all the sk channels in the number card.

        :param number_card: number of the card.
        :return: if found channel list, otherwise None.
        """
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == number_card:
                return sk_manager.get_sk_channel_list()
        return None

    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_min_green(self, name: str, value: int):
        """
        This method updates the minimum green time of a move.

        :param name: name of the move.
        :param value: minimum green time of the move.
        :return: True is success, otherwise False.
        """
        print(f"**** [class] DataController:\t [method] update_min_green\t[start] ")
        if not value.isdigit():
            Log.error(f"Error: The value is not a number")
            return False
        if isinstance(value, str):
            value = int(value)
            Log.warning(f"Warning: The value was string but converted to int")
        if value < 0:
            Log.error(f"Error: The value is negative")
            return False
        self.data_manager.update_min_green(name, value)
        print(f"**** [class] DataController:\t [method] update_min_green\t[end] ")
        return True

    def update_sk_comment(self, card_number: int, channel: int):
        """
        This method control the comment statement in sk channel.

        :param card_number: number of the sk card
        :param channel: channel of in the sk card
        :return: None
        """
        print(f"**** [class] DataController:\t [method] update_sk_comment\t[start] ")
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                if sk_manager.update_sk_comment(channel):
                    print(f"**** [class] DataController:\t [method] update_sk_comment\t[end] ")
                    return True
        print(f"**** [class] DataController:\t [method] update_sk_comment\t[end] ")
        return False

    def update_sk_color(self, card_number: int, row: int):
        """
        This method control the color statement in sk channel.

        :param card_number: number of the sk card
        :param row: channel of in the sk card
        :return: None
        """
        print(f"**** [class] DataController:\t [method] update_sk_color\t[start] ")
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                return sk_manager.update_sk_color(row)
        return False
        print(f"**** [class] DataController:\t [method] update_sk_color\t[end] ")

    def update_sk_name(self, card_number: int, row: int, new_name: str):
        """
        This method control changing names in sk channel.

        :param card_number: number of the sk card
        :param row: channel of in the sk card
        :param new_name: new name to set
        :return: None
        """
        print(f"**** [class] DataController:\t [method] update_sk_name\t[start] ")
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                sk_manager.update_sk_name(row, new_name)
        print(f"**** [class] DataController:\t [method] update_sk_name\t[end] ")

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
    def initialize_app(self, btn_list):
        """
        This method initialize the app.

        :param btn_list: list of button to enable after the initialization.
        :return: None
        """
        print(f"**** [class] DataController:\t [method] initialize_app\t[start] ")
        # set new paths
        if self.path_manager.scan_set_paths() is False:
            Log.error(f"Error: The path initialization failed")
            return None

        # clean all data
        self.reset()

        # scan for moves
        self.data_manager.init_moves(self.path_manager.get_path_init_tk1())
        self.data_manager.init_matrix(self.path_manager.get_path_init_tk1())
        print(f"self.sk_manager: {self.sk_manager}")
        self.set_sk_list(self.path_manager.get_path_init())
        for sk in self.sk_manager:
            sk.initialize_sk(self.path_manager.get_path_init())

        # enable buttons
        for btn in btn_list:
            btn.setDisabled(False)
        print(f"**** [class] DataController:\t [method] initialize_app\t[end] ")

    def reset(self):
        """
        This method resets all the data.
        """
        print(f"**** [class] DataController:\t [method] reset\t[start] ")
        # self.path_manager.reset() # For now it's not needed because his fields overwritten if all files exist like they should be.
        self.data_manager.reset()
        self.sk_manager = []
        print(f"**** [class] DataController:\t [method] reset\t[end] ")

    def is_move_valid(self, move_name, move_type):
        """
        This method checks if the move is valid.

        :param move_name: name of the move.
        :param move_type: type of the move.
        :return: Ture if the move is valid else False.
        """
        if move_type == "Traffic" or move_type == "Traffic_Flashing":
            if not move_name.startswith("k") or not move_name[1:].isdigit():
                Log.error(f"Error: Invalid name. name: {move_name}, type: {move_type}")
                return False
        elif move_type == "Pedestrian":
            if not move_name.startswith("p") or not move_name[1:].isalpha():
                Log.error(f"Error: Invalid name. name: {move_name}, type: {move_type}")
                return False
        elif move_type == "Blinker" or move_type == "Blinker_Conditional":
            if not move_name.startswith("B") or not move_name[1:].isalpha():
                Log.error(f"Error: Invalid name. name: {move_name}, type: {move_type}")
                return False
        return True

    def set_sk_list(self, path):
        """
        This method count how many sk cards exist and create the same instances of 'SkManager' as a list.

        :param path: path to 'init.java'.
        :return: None
        """
        print(f"**** [class] DataController:\t [method] set_sk_list\t[start] ")
        card_num = 1
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line:

                    self.sk_manager.append(SkManager(card_num))
                    card_num = card_num + 1
        print(f"Found {card_num - 1} sk cards")
        print(f"**** [class] DataController:\t [method] set_sk_list\t[end] ")

    # =========================================== #
    #                class methods                #
    # =========================================== #



    # =========================================== #
    #                static methods               #
    # =========================================== #


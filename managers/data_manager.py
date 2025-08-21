import re

from config.patterns import move_pattern, matrix_pattern
from entities.log import Log
from entities.matrix import MatrixCell
from entities.signal_group import SignalGroup

class DataManager:
    _instance = None

    # =========================================== #
    #                Construction                 #
    # =========================================== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:
            cls._instance = super(DataManager, cls).__new__(cls)    # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                # run _init
        return cls._instance                                        # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        self.moves = []
        # self.d_detectors = []
        # self.e_detectors = []
        # self.inter_stages = []
        self.MatrixCells = []
        print("** data manager was set successfully")

    # =========================================== #
    #                 add methods                 #
    # =========================================== #
    def add_move(self, move_name, move_type, is_main, min_green = "0"):
        """
        This method add new move.

        :param move_name: name of move
        :param move_type: type of move
        :param is_main: True if the move is main, False otherwise
        :param min_green: minimum green time
        :return: True if success, False otherwise
        """
        print(f"** [class] DataManager:\t [method] add_move\t[start] ")
        if self.is_move_exist(move_name):
            Log.error("Error: The move already exist")
            print(f"** [class] DataManager:\t [method] add_move\t[end] ")
            return False
        new_move = SignalGroup(move_name, move_type, is_main, min_green)
        self.moves.append(new_move)
        print(f"** [class] DataManager:\t [method] add_move\t[end] ")
        return True

    def init_moves(self, path):
        """
        This method set from path the moves in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        print(f"** [class] DataManager:\t [method] init_moves\t[start] ")
        pattern = move_pattern

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if line.startswith("//") or not line.startswith("tk."):
                    continue
                match = pattern.match(line)
                if match:
                    phase, move_type, min_green, is_main = match.groups()
                    new_move = SignalGroup(phase, move_type, True if is_main == "true" else False, min_green)
                    self.moves.append(new_move)
        if len(self.moves) == 0:
            Log.warning(f"Warning: Moves not found")
        print(f"** [class] DataManager:\t [method] init_moves\t[end] ")

    def init_matrix(self, path):
        """
        This method set from path the matrix cells in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        print(f"** [class] DataManager:\t [method] init_matrix\t[start] ")
        pattern = matrix_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                line = line.split("//", 1)[0].strip() # ignore what after //, split maximum 1 time
                if not line:
                    continue

                match = pattern.search(line)
                if match:
                    out = match.group("out")
                    inn = match.group("inn")
                    t1 = int(match.group("t1"))
                    t2 = int(match.group("t2"))

                    self.MatrixCells.append(MatrixCell(out, inn, t1))
                    self.MatrixCells.append(MatrixCell(inn, out, t2))

        if len(self.MatrixCells) == 0:
            Log.warning(f"Warning: Matrix cells not found")

        print(f"** [class] DataManager:\t [method] init_matrix\t[end] ")

    # =========================================== #
    #                 get methods                 #
    # =========================================== #
    def get_all_moves(self):
        """
        This method return all the moves sorted combined.

        :return: list of all moves
        """
        print(f"** [class] DataManager:\t [method] get_all_moves\t[start] ")
        self.moves = sorted(self.moves, key=lambda m: m.name)
        sorted_moves = []

        traffic_moves = []
        pedestrian_moves = []
        blinker_moves = []

        for move in self.moves:
            if move.name.startswith("k"):       # insert to list all moves that start with 'k'
                traffic_moves.append(move)
            elif move.name.startswith("p"):     # insert to list all moves that start with 'p'
                pedestrian_moves.append(move)
            elif move.name.startswith("B"):     # insert to list all moves that start with 'B'
                blinker_moves.append(move)
            else:
                Log.error("Error: There is a move with name that not start with 'k', 'p', 'B'")

        print(f"** [class] DataManager:\t [method] get_all_moves\t[end] ")
        return traffic_moves + pedestrian_moves + blinker_moves

    def get_all_matrix_cells(self):
        """
        This method return list of the matrix cells.

        :return: list of all matrix cells.
        """
        return self.MatrixCells

    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_min_green(self, name: str, value: int):
        """
        This method update the minimum green.

        :param name: name of the move.
        :param value: value of the move.
        :return: True if success, False otherwise
        """
        for move in self.moves:
            if move.name == name:
                prev = move.min_green
                move.min_green = value
                Log.success(f"The minimum green has changed from {prev} to {value} successfully")
                return True
        Log.error("Error: The move is not exist")
        return False

    # =========================================== #
    #               remove methods                #
    # =========================================== #
    def remove_move(self, move_name):
        """
        This method remove the move.

        :param move_name: name of the move
        :return: True if success, False otherwise
        """
        target = next((m for m in self.moves if m.name == move_name), None)
        if target:
            perv = target.name
            self.moves.remove(target)
            Log.success(f"{perv} has been removed successfully")
            return True
        return False

    # =========================================== #
    #               general methods               #
    # =========================================== #
    def reset(self):
        """
        This method reset all fields

        :return: None
        """
        self.moves = []
        # self.d_detectors = []
        # self.e_detectors = []
        # self.inter_stages = []
        self.MatrixCells = []

    def is_move_exist(self, move_name: str):
        """
        This method check if the move exists.

        :param move_name: name of the move
        :return: True if exists, False otherwise
        """
        for move in self.moves:
            if move.name == move_name:
                return True
        return False
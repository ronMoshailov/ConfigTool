import re

from config.patterns import move_pattern, matrix_pattern, detectors_pattern, image_pattern
from entities.detector import Detector
from entities.image import Image
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
        self.detectors = []
        # self.inter_stages = []
        self.MatrixCells = []
        self.images = []
        print("** data manager was set successfully")

    # --------------- add methods --------------- #
    def add_move(self, move_name, move_type, is_main, min_green = "0"):
        """
        This method add new move.

        :param move_name: name of move
        :param move_type: type of move
        :param is_main: True if the move is main, False otherwise
        :param min_green: minimum green time
        :return: True if success, False otherwise
        """
        if self.is_move_exist(move_name):
            return False
        new_move = SignalGroup(move_name, move_type, is_main, min_green)
        self.moves.append(new_move)
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

    def init_detectors(self, path):
        """
        This method extracts detector declarations from InitTk1.java.

        :param path: path to "InitTk1.java"
        :return: None
        """
        pattern = detectors_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                matches = pattern.findall(line)
                for detector_type, instances in matches:
                    variables = [v.strip() for v in instances.split(",")]
                    for name in variables:
                        self.detectors.append(Detector(name, detector_type))

    def add_detector(self, detector_name, move_type, ext_time):
        self.detectors.append(Detector(detector_name, move_type, ext_time))
        return True


    # --------------- get methods --------------- #
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

    def get_all_detectors(self):
        return self.detectors

    def get_all_images(self):
        return self.images

    def get_image_count(self):
        return len(self.images)


    # --------------- update methods --------------- #
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

    def update_matrix(self, changes_list):
        is_found = False

        for row_name, col_name, value in changes_list:
            for cell in self.MatrixCells:
                if row_name == cell.move_out and col_name == cell.move_in:
                    cell.wait_time = value
                    is_found = True
                    break
            if not is_found:
                self.MatrixCells.append(MatrixCell(row_name, col_name, value))
            is_found = False

        return True
    # --------------- remove methods --------------- #
    def remove_move(self, move_name):
        """
        This method remove the move from the DB.

        :param move_name: name of the move
        :return: True if success, False otherwise
        """
        target = next((m for m in self.moves if m.name == move_name), None)
        if target:
            perv = target.name
            self.moves.remove(target)
            return True
        return False

    def remove_detector(self, detector_name):
        target = next((d for d in self.detectors if d.name == detector_name), None)
        if target:
            self.detectors.remove(target)
            return True
        return False

    # --------------- general methods --------------- #
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

    def init_images(self, path):
        pattern = image_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                m = pattern.search(line)
                if m:
                    name = m.group(1)
                    moves = [re.sub(r'^tk\.', '', item.strip()) for item in m.group(2).split(",")]
                    all_moves = self.get_all_moves()
                    collection = []

                    for move in all_moves:
                        if move.name in moves:
                            collection.append(move)
                    self.images.append(Image(name, collection))

    def update_images(self, table_dict):
        for image_name, table in table_dict.items():
            checked_moves = []
            for row_num in range(table.rowCount()):
                move_name = table.cellWidget(row_num, 0).text()
                checkbox = table.cellWidget(row_num, 1)
                if checkbox.isChecked():
                    move = self._get_move_by_name(move_name)
                    if move:
                        checked_moves.append(move)

            is_found = False

            for image in self.images:
                if image.image_name == image_name:
                    image.move_list = checked_moves
                    is_found = True
            if not is_found:
                self.images.append(Image(image_name, checked_moves))

    def _get_move_by_name(self, name):
        for move in self.moves:
            if move.name == name:
                return move
        return None

    def remove_images(self, image_name):
        for image in self.images:
            if image.image_name == image_name:
                self.images.remove(image)
                return True
        return False



import re

from entities.signal_group import SignalGroup


# from managers.sk24_manager import Sk24


class DataManager:
    _instance = None

    # =========================================== #
    #                class methods                #
    # =========================================== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:  # checks if there is an instance of the class
            cls._instance = super(DataManager, cls).__new__(
                cls)  # create new instance and store him in _instance before __init__
            cls._instance.__init__()  # run _init
        return cls._instance  # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        self.moves = []
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []

    # =========================================== #
    #                 add methods                 #
    # =========================================== #
    def add_move(self, move_name, move_type, is_main, min_green):
        new_move = SignalGroup(move_name, move_type, is_main, min_green)
        print(f"appending \"add move\" ref with: move_name: {new_move.name}, move_type: {new_move.type}, is_main: {new_move.is_main}, min_green: {new_move.min_green}")

        self.moves.append(new_move)
        return True

    def init_moves_from_file(self, path):
        """
        This method set from path the moves in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        is_found = False
        pattern = re.compile(
            r"""^                          # התחלה
            \s*tk\.(k\d+|p[a-zA-Z]|B[a-zA-Z])     # שם המונע אחרי tk.
            \s*=\s*new\s+Move\(                   # התחלה של new Move
            \s*tk\s*,\s*                          # הטק tk,
            "[^"]+"\s*,\s*                      # השם בתוך גרשיים כפולים
            MoveType\.([A-Za-z_]+)\s*,\s*        # MoveType
            (\d+)\s*,\s*                          # מספר ראשון (min_red)
            \d+\s*,\s*                            # המספר הבא (לא רלוונטי כרגע)
            (true|false)                          # true/false
            """, re.VERBOSE
        )

        print(f"data_manager:\tinit_moves_from_file\t[start] ")
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()

                if line.startswith("//") or not line.startswith("tk."):
                    continue

                match = pattern.match(line)
                if match:
                    is_found = True
                    phase, move_type, min_red, is_main = match.groups()
                    new_move = SignalGroup(phase, move_type, True if is_main == "true" else False, min_red)
                    self.moves = self.moves + [new_move]
        print(f"data_manager:\tinit_moves_from_file\t[end]\n")

        if is_found is False:
            return None

    # =========================================== #
    #                 get methods                 #
    # =========================================== #
    def get_all_moves(self):
        """
        This method return all the moves combined.

        :return: list of all moves
        """
        print(f"[class] data_manager:\t [method] get_all_moves\t[start] ")
        self.moves = sorted(self.moves, key=lambda m: m.name)
        sorted_moves = []

        # insert to list all moves that start with 'k'
        for move in self.moves:
            if move.name.startswith("k"):
                sorted_moves.append(move)

        # insert to list all moves that start with 'p'
        for move in self.moves:
            if move.name.startswith("p"):
                sorted_moves.append(move)

        # insert to list all moves that start with 'B'
        for move in self.moves:
            if move.name.startswith("B"):
                sorted_moves.append(move)

        print(f"[class] data_manager:\t [method] get_all_moves\t[end]\n")
        return sorted_moves

    # =========================================== #
    #               remove methods                #
    # =========================================== #
    def remove_move(self, move_name):
        print(f"--\nremove_move: Start. {move_name}", flush=True)

        target = next((m for m in self.moves if m.name == move_name), None)
        if target:
            self.moves.remove(target)
            print(f"{move_name} removed (main)", flush=True)
            print("remove_move: End\n--", flush=True)
            return True

        print("Move wasn't found", flush=True)
        return False

    # =============== scan methods =============== #

    # =============== general methods =============== #
    def reset(self):
        """
        This method reset all fields

        :return: None
        """
        self.moves = []
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []

    # # =============== Add =============== #

    #
    # def add_not_main_phase(self, new_phase):
    #     self.not_main_phases.append(new_phase)
    #
    # def add_d_detector(self, new_detector):
    #     self.d_detectors.append(new_detector)
    #
    # def add_e_detector(self, new_detector):
    #     self.e_detectors.append(new_detector)
    #
    # def add_inter_stages(self, new_inter_stages):
    #     self.inter_stages.append(new_inter_stages)
    #
    # def add_sk24(self, path):
    #     new_sk24 = Sk24(path, self.sk24_idx)
    #     self.sk24_idx += 1
    #     self.sk24.append(new_sk24)
    #
    # def print_main_phases(self):
    #     print(f"main phases: {self.main_phases}")
    #
    # print(f"phase: {phase:<5}, type: {move_type:<25}, min_red: {min_red:<5}, in_main: {is_main:<5}")

    # def get_phases_from_tk1(self, path):
    #     return self.main_phases[self.sk24_idx]
    # def find_not_main_phase(self):

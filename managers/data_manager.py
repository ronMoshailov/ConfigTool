import re

from entities.matrix import MatrixCell
from entities.signal_group import SignalGroup

class DataManager:
    _instance = None

    # =========================================== #
    #                class methods                #
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
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []
        self.MatrixCells = []

    # =========================================== #
    #                 add methods                 #
    # =========================================== #
    def add_move(self, move_name, move_type, is_main, min_green):
        """

        :param move_name: name of move
        :param move_type: type of move
        :param is_main: True if the move is main, False otherwise
        :param min_green: minimum green time
        :return:
        """
        new_move = SignalGroup(move_name, move_type, is_main, min_green)

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

    def init_matrix(self, path):
        pattern = re.compile(
            r"""
            tk\.zwz\.setzeZwz                      # קריאת הפונקציה
            \(\s*                                   # (
            tk\.(?P<out>[A-Za-z_]\w*)\s*,          # out
            \s*tk\.(?P<inn>[A-Za-z_]\w*)\s*,       # in
            \s*(?P<t1>-?\d+)\s*,                   # זמן 1
            \s*(?P<t2>-?\d+)\s*                    # זמן 2
            \)\s*;                                  # );
            """,
            re.VERBOSE
        )

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                # מסירים הערות ושורות ריקות
                line = line.split("//", 1)[0].strip()
                if not line:
                    continue

                match = pattern.search(line)
                if match:
                    out = match.group("out")
                    inn = match.group("inn")
                    t1 = int(match.group("t1"))
                    t2 = int(match.group("t2"))

                    # יוצרים את שני הכיוונים
                    self.MatrixCells.append(MatrixCell(out, inn, t1))
                    self.MatrixCells.append(MatrixCell(inn, out, t2))

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

    def get_all_matrix_cells(self):
        return self.MatrixCells

    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_min_green(self, name, value):

        for move in self.moves:
            if move.name == name:
                print(f"The name is {name}, the value is {value}")
                print("found")
                move.min_green = value
                return True
        return None

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
            self.moves.remove(target)
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
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []


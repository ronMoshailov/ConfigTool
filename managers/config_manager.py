import re

from entities.move import Move
from managers.sk24_manager import Sk24


class ConfigManager:
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(ConfigManager, cls).__new__(cls)
            cls._instance._init()
        return cls._instance

    def _init(self):
        self.main_moves = []
        self.not_main_phases = []
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []

    # =============== getter =============== #
    def get_all_phases(self):
        return self.main_moves + self.not_main_phases

    # =============== Scan =============== #
    def scan_set_moves(self, path):
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

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()

                if line.startswith("//") or not line.startswith("tk."):
                    continue

                match = pattern.match(line)
                if match:
                    is_found = True
                    phase, move_type, min_red, is_main = match.groups()
                    is_main = is_main.capitalize()

                    new_move = Move(phase, move_type, min_red, is_main)
                    if is_main == "True":
                        self.main_moves.append(new_move)
                        continue
                    self.not_main_phases.append(new_move)

        if is_found is False:
            return None

    # =============== methods =============== #
    def reset(self):
        """
        This method reset all fields

        :return: None
        """
        self.main_moves = []
        self.not_main_phases = []
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []



















    # =============== Add =============== #
    def add_main_phase(self, new_phase):
        self.main_phases.append(new_phase)
        self.print_main_phases()

    def add_not_main_phase(self, new_phase):
        self.not_main_phases.append(new_phase)

    def add_d_detector(self, new_detector):
        self.d_detectors.append(new_detector)

    def add_e_detector(self, new_detector):
        self.e_detectors.append(new_detector)

    def add_inter_stages(self, new_inter_stages):
        self.inter_stages.append(new_inter_stages)

    def add_sk24(self, path):
        new_sk24 = Sk24(path, self.sk24_idx)
        self.sk24_idx += 1
        self.sk24.append(new_sk24)

    def print_main_phases(self):
        print(f"main phases: {self.main_phases}")

                    # print(f"phase: {phase:<5}, type: {move_type:<25}, min_red: {min_red:<5}, in_main: {is_main:<5}")

    # def get_phases_from_tk1(self, path):
    #     return self.main_phases[self.sk24_idx]
    # def find_not_main_phase(self):

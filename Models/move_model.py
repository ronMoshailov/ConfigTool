class _Move:
    """
    Represents a traffic signal move.
    """
    def __init__(self, name: str, move_type: str, is_main: bool, min_green: int = 0):
        """
        :param name: name   of the move                                     e.g., 'k1', 'k2', 'pa', 'Bd'.
        :param move_type:   The category/type of move                       e.g., 'Traffic', 'Pedestrian', 'Blinker_Conditional'.
        :param is_main:     Indicates whether this is a main move           e.g., (True/False)
        :param min_green:   Minimum green time for this move (in seconds)   e.g., default is 0.
        """
        self.name = name
        self.type = move_type
        self.is_main = is_main
        self.min_green = min_green

class MoveModel:
    """
    Represents a model of all the moves.
    """
    def __init__(self):
        """
        Constructor.
        """
        self.all_moves = []

    # ============================== CRUD ============================== #
    def add_move(self, name: str, move_type: str, is_main: bool, min_green: int = 0):
        """
        This method adds a new move.

        :param name: Name of the move.
        :param move_type: The type of move.
        :param is_main: Indicates whether this is a main move.
        :param min_green: Minimum green time for this move (in seconds)   e.g., default is 0.
        :return: True if successful, False otherwise
        """
        # check if the move already exist
        if self.is_move_exist(name):
            return False

        # create move
        new_move  = _Move(name, move_type, is_main, min_green)
        self.all_moves.append(new_move)
        self._sort_moves()

        return True

    def get_move_type(self, move_name):
        for move in self.all_moves:
            if move.name == move_name:
                return move.type
        return None

    def remove_move(self, move_name):
        """
        This method removes a move if exists.

        :param move_name: The name of the move.
        :return: True if move removed, False otherwise.
        """
        for move in self.all_moves:
            if move.name == move_name:
                self.all_moves.remove(move)
                return True
        return False

    def update_min_green(self, name, min_green):
        """
        This method updates the minimum green time for this move.

        :param name: Name of the move.
        :param min_green: Minimum green time.
        :return: True if successful, False otherwise
        """
        for move in self.all_moves:
            if move.name == name:
                move.min_green = min_green
                return True
        return False

    def get_all_moves_names(self):
        return [m.name for m in self.all_moves]

    def get_all_types(self):
        return ["Traffic", "Traffic_Flashing", "Pedestrian", "Blinker_Conditional", "Blinker"]

    def update_name(self, old_name, new_name):
        move_to_change = None

        for move in self.all_moves:
            if move.name == old_name:
                if not move_to_change:
                    move_to_change = move
            if move.name == new_name:
                raise Exception("המופע כבר קיים")
        move_to_change.name = new_name
        if move_to_change.name.startswith("k") and (move_to_change.type != "Traffic" and move_to_change.type != "Traffic_Flashing"):
            self.update_type(move_to_change.name, "Traffic")
        if move_to_change.name.startswith("p") and move_to_change.type != "Pedestrian":
            self.update_type(move_to_change.name, "Pedestrian")
        if move_to_change.name.startswith("B") and (move_to_change.type != "Blinker_Conditional" and move_to_change.type != "Blinker"):
            self.update_type(move_to_change.name, "Blinker")

        self._sort_moves()
        return

    def update_type(self, move_name,  new_type):
        for move in self.all_moves:
            if move.name == move_name:
                move.type = new_type
                return

    # ============================== Logic ============================== #
    def is_move_exist(self, move_name):
        """
        This method checks if a move exists.

        :param move_name: The name of the move
        :return: True if move exists, False otherwise
        """
        for move in self.all_moves:
            if move.name == move_name:
                return True
        return False

    def reset(self):
        self.all_moves.clear()

    def _sort_moves(self):
        traffic_list = [m for m in self.all_moves if m.name.startswith("k")]
        pedestrian_list = [m for m in self.all_moves if m.name.startswith("p")]
        blinker_list = [m for m in self.all_moves if m.name.startswith("B")]

        traffic_list = sorted(traffic_list, key=lambda m: m.name)
        pedestrian_list = sorted(pedestrian_list, key=lambda m: m.name)
        blinker_list = sorted(blinker_list, key=lambda m: m.name)

        self.all_moves = traffic_list + pedestrian_list + blinker_list









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

        # data
        priority_map = {"k": 0, "p": 1, "B": 2} # priority map
        new_priority = priority_map[new_move.name[0]] # priority of the move
        insert_index = len(self.all_moves)  # set default index in the end

        # check which index need to be placed
        for i, move in enumerate(self.all_moves):
            existing_priority = priority_map[move.name[0]]
            if existing_priority > new_priority:
                insert_index = i
                break

        # add the move to the model
        self.all_moves.insert(insert_index, new_move)

        # return
        return True

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


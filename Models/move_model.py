class _Move:
    def __init__(self, name: str, move_type: str, is_main: bool, min_green: int):
        self.name       = name
        self.type       = move_type
        self.is_main    = is_main
        self.min_green  = min_green


class MoveModel:
    def __init__(self):
        self.all_moves = []

    # ============================== CRUD ============================== #
    def add_move(self, name: str, move_type: str, is_main: bool, min_green: int = 0):
        """
        This method add new move to the model
        """
        # Check if the move already exist
        if self._is_move_exist(name):
            return False

        # Create move
        new_move  = _Move(name, move_type, is_main, min_green)
        self.all_moves.append(new_move)
        self._sort_moves()

        # Return
        return True

    def get_move_type(self, move_name):
        """
        This method return the type of the move
        """
        for move in self.all_moves:
            if move.name == move_name:
                return move.type
        return None

    def remove_move(self, move_name):
        """
        This method removes the move from the model
        """
        for move in self.all_moves:
            if move.name == move_name:
                self.all_moves.remove(move)
                return True
        return False

    def set_min_green(self, move_name, min_green):
        """
        This method set a new value of the minimum green of the move
        """
        for move in self.all_moves:
            if move.name == move_name:
                move.min_green = min_green
                return

    def set_main(self, move_name, new_state):
        """
        This method set new state for the move
        """
        for move in self.all_moves:
            if move.name == move_name:
                move.is_main = True if new_state == 2 else False

    def get_all_moves_names(self):
        """
        This method return all the names of the moves in the model
        """
        return [m.name for m in self.all_moves]

    def get_all_types(self):
        """
        This method return all the possible types of the moves
        """
        return ["Traffic", "Traffic_Flashing", "Pedestrian", "Blinker_Conditional", "Blinker"]

    def update_name(self, old_name, new_name):
        """
        This method change the name of the move
        """
        # Data
        move_to_change = None

        # Check if move name already exist
        for move in self.all_moves:
            if move.name == old_name:
                if not move_to_change:
                    move_to_change = move
            if move.name == new_name:
                raise Exception("המופע כבר קיים")

        # Rename the move
        move_to_change.name = new_name

        # Update type depend on the name
        if move_to_change.name.startswith("k") and (move_to_change.type != "Traffic" and move_to_change.type != "Traffic_Flashing"):
            self.set_type(move_to_change.name, "Traffic")
        if move_to_change.name.startswith("p") and move_to_change.type != "Pedestrian":
            self.set_type(move_to_change.name, "Pedestrian")
        if move_to_change.name.startswith("B") and (move_to_change.type != "Blinker_Conditional" and move_to_change.type != "Blinker"):
            self.set_type(move_to_change.name, "Blinker")

        # Sort the moves
        self._sort_moves()

    def set_type(self, move_name,  new_type):
        """
        This method change set new type for the move
        """
        for move in self.all_moves:
            if move.name == move_name:
                move.type = new_type
                return

    # ============================== Logic ============================== #
    def reset_move_model(self):
        """
        This method reset the data of the model
        """
        self.all_moves.clear()

    def _sort_moves(self):
        """
        This method sort the moves in the model
        """
        traffic_list    = [m for m in self.all_moves if m.name.startswith("k")]
        pedestrian_list = [m for m in self.all_moves if m.name.startswith("p")]
        blinker_list    = [m for m in self.all_moves if m.name.startswith("B")]

        traffic_list    = sorted(traffic_list,    key=lambda m: m.name)
        pedestrian_list = sorted(pedestrian_list, key=lambda m: m.name)
        blinker_list    = sorted(blinker_list,    key=lambda m: m.name)

        self.all_moves = traffic_list + pedestrian_list + blinker_list

    def _is_move_exist(self, move_name):
        """
        This method check if the move already exist in the model
        """
        for move in self.all_moves:
            if move.name == move_name:
                return True
        return False





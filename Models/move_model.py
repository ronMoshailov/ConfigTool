class Move:
    def __init__(self, name: str, move_type: str, is_main: bool, min_green: int = 0):
        self.name = name
        self.type = move_type
        self.min_green = min_green
        self.is_main = is_main

class MoveModel:
    def __init__(self):
        self.all_moves = []

    def add_move(self, name: str, move_type: str, is_main: bool, min_green: int = 0):
        if self.is_move_exist(name):
            return False
        new_move  = Move(name, move_type, is_main, min_green)

        priority_map = {"k": 0, "p": 1, "B": 2}
        new_priority = priority_map[new_move.name[0]]
        insert_index = len(self.all_moves)  # כברירת מחדל – בסוף
        for index, existing_move in enumerate(self.all_moves):
            existing_priority = priority_map[existing_move.name[0]]
            if existing_priority > new_priority:
                insert_index = index
                break

        self.all_moves.insert(insert_index, new_move)
        return True

    def is_move_exist(self, move_name):
        for move in self.all_moves:
            if move.name == move_name:
                return True
        return False

    def remove_move(self, move_name):
        for move in self.all_moves:
            if move.name == move_name:
                self.all_moves.remove(move)
                return True
        return False

    def update_min_green(self, name, min_green):
        for move in self.all_moves:
            if move.name == name:
                move.min_green = min_green
                return True
        return False
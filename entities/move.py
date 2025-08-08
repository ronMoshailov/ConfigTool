class Move:

    def __init__(self, name, move_type, min_red, is_main):
        self.name = name
        self.type = move_type
        self.min_green = 1
        self.min_red = min_red
        self.is_main = is_main

        #
        self.min_green = None

class Move:

    def __init__(self, name, move_type, is_main, min_red = None):
        """
        This method runs when the object initialized.
        """
        self.name = name
        self.type = move_type
        self.min_red = min_red
        self.is_main = is_main


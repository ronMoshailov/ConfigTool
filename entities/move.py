class Move:

    def __init__(self, name, move_type, min_green, is_main):
        """
        This method runs when the object initialized.
        """
        self.name = name
        self.type = move_type
        self.min_green = min_green
        self.is_main = is_main

        # print(f"self.name: {self.name:<5}, move_type: {move_type:<25}, is_main: {is_main:<8}, min_green: {min_green:<3}")
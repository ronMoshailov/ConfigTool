class SignalGroup:
    """
    This class represents a traffic signal group.
    """
    def __init__(self, name, move_type, is_main, min_green = None):
        self.name = name
        self.type = move_type
        self.min_green = min_green
        self.is_main = is_main


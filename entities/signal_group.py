class SignalGroup:
    """
    This class represents a traffic signal group.
    """
    def __init__(self, name: str, move_type: str, is_main: bool, min_green:int  = None):
        self.name = name
        self.type = move_type
        self.min_green = min_green
        self.is_main = is_main


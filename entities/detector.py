class Detector:
    """
    This class represents a matrix group.
    """
    def __init__(self, name: str, type: str, ext_unit:int = None):
        self.name = name
        self.type = type
        self.ext_unit = ext_unit

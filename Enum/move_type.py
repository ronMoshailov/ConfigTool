from enum import Enum

class MoveType(Enum):
    T  = "Traffic"
    TF = "Traffic_Flashing"
    P  = "Pedestrian"
    B  = "Blinker"
    BC = "Blinker_Conditional"

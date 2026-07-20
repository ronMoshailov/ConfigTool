from enum import Enum

class Authority(Enum):
    TEL_AVIV = "תל אביב"
    JERUSALEM = "ירושלים"
    # HAIFA = "חיפה"
    # NETIVEI_ISRAEL = "נתיבי ישראל"
    NETIVEI_ISRAEL_FALCON = "נתיבי ישראל - falcon"

class DetectorType(Enum):
    D = "DDetector"
    E = "EDetector"
    DE = "DEDetector"
    TP = "TPDetector"
    Q = "QDetector"

class MoveType(Enum):
    T  = "Traffic"
    TF = "Traffic_Flashing"
    P  = "Pedestrian"
    B  = "Blinker"
    BC = "Blinker_Conditional"

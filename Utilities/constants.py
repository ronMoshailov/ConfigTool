import os

from PyQt6.QtGui import QColor

class Constants:
    """
    This class contains all the constants information in the application.
    """
    # =============== Employees =============== #
    EMPLOYEES = {
        "-" : "-",
        "איליה": "Ilia B.",
        "רון": "Ron M.",
        "לירוי": "Liroy M.",
        "קטיה": "Katya L.",
        "סרגי": "Sergei V.",
        "שחר": "Shachar G.",
        "לנה": "Lena B",
        "דוד": "David R.",
    }

    # =============== IP =============== #
    IP_ADDRESS = {
        "TEL_AVIV" : "172.18.0.2",
    "MAATZ_FALCON" : "192.168.16.99"

    }

    # =============== QColor =============== #
    gray_color          = QColor(230, 230, 230)
    light_green_color   = QColor(180, 255, 180)
    white_color         = QColor(255, 255, 255)
    light_red_color     = QColor(255, 236, 235)
    black_color         = QColor(  0,   0,   0)
    light_blue_color    = QColor(153, 207, 249)


# This file holds the data that don't change.


# =============== data =============== #

# =============== paths =============== #


# QColor - just the color itself
# QBrush - color + how to fill (full, Semi-transparent, transparent, lines, picture etc')

# =================================================================================================================== #


# GREEN_IMAGE_PATH                = os.path.join(PROJECT_DIR, "Pictures", "Icons", "green.png")
# GREEN_BLINKER_IMAGE_PATH        = os.path.join(PROJECT_DIR, "Pictures", "Icons", "green_blink.png")
# PEDESTRIAN_IMAGE_PATH           = os.path.join(PROJECT_DIR, "Pictures", "Icons", "pedestrian.png")
# BLINKER_IMAGE_PATH              = os.path.join(PROJECT_DIR, "Pictures", "Icons", "blinker.png")
# BLINKER_CONDITIONAL_IMAGE_PATH  = os.path.join(PROJECT_DIR, "Pictures", "Icons", "blinker conditional.png")


# =============== log colors =============== #
# RED = "\033[91m"
# YELLOW = "\033[93m"
# RESET = "\033[0m"
















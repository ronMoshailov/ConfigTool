import os

from PyQt6.QtGui import QColor

# =============== data =============== #
PROJECT_NUMBER      = None

# =============== paths =============== #
BASE_DIR            = os.path.dirname(__file__)     # path of the file
PROJECT_DIR         = os.path.dirname(BASE_DIR)     # path of the project file

GREEN_IMAGE_PATH                = os.path.join(PROJECT_DIR, "Pictures", "Icons", "green.png")
GREEN_BLINKER_IMAGE_PATH        = os.path.join(PROJECT_DIR, "Pictures", "Icons", "green_blink.png")
PEDESTRIAN_IMAGE_PATH           = os.path.join(PROJECT_DIR, "Pictures", "Icons", "pedestrian.png")
BLINKER_IMAGE_PATH              = os.path.join(PROJECT_DIR, "Pictures", "Icons", "blinker.png")
BLINKER_CONDITIONAL_IMAGE_PATH  = os.path.join(PROJECT_DIR, "Pictures", "Icons", "blinker conditional.png")

# =============== QColor =============== #
# QColor - just the color itself
# QBrush - color + how to fill (full, Semi-transparent, transparent, lines, picture etc')

gray_color          = QColor(230, 230, 230)
light_green_color   = QColor(180, 255, 180)
white_color         = QColor(255, 255, 255)
light_red_color     = QColor(255, 236, 235)
black_color         = QColor(  0,   0,   0)
light_blue_color    = QColor(153, 207, 249)

# =============== log colors =============== #
RED = "\033[91m"
YELLOW = "\033[93m"
RESET = "\033[0m"
















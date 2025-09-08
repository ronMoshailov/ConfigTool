# config/constants.py
import os

BASE_DIR = os.path.dirname(__file__)        # path of the file
PROJECT_DIR = os.path.dirname(BASE_DIR)     # path of the project file

# =============== paths =============== #
GREEN_IMAGE_PATH                = os.path.join(PROJECT_DIR, "data", "icons", "green.png")
GREEN_BLINKER_IMAGE_PATH        = os.path.join(PROJECT_DIR, "data", "icons", "green_blink.png")
PEDESTRIAN_IMAGE_PATH           = os.path.join(PROJECT_DIR, "data", "icons", "pedestrian.png")
BLINKER_IMAGE_PATH              = os.path.join(PROJECT_DIR, "data", "icons", "blinker.png")
BLINKER_CONDITIONAL_IMAGE_PATH  = os.path.join(PROJECT_DIR, "data", "icons", "blinker conditional.png")
















# =============== not used =============== #
BUTTON_WIDTH = 120
BUTTON_HEIGHT = 40
COLUMN_SPACING = 20

MIN_GREEN_IMAGE_PATH            = os.path.join(PROJECT_DIR, "data", "icons", "min green.png")

# =============== log colors =============== #
RED = "\033[91m"
YELLOW = "\033[93m"
RESET = "\033[0m"



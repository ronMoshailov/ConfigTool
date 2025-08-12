# config/constants.py
import os

BASE_DIR = os.path.dirname(__file__)         # תיקיית הקובץ
PROJECT_DIR = os.path.dirname(BASE_DIR)

BUTTON_WIDTH = 120
BUTTON_HEIGHT = 40
ROW_SPACING = 20
COLUMN_SPACING = 20
# WINDOW_WIDTH = 600
# WINDOW_HEIGHT = 400
# FONT_FAMILY = "Arial"

# =============== paths =============== #
# INIT_PATH = r"C:\Users\ron.MENORAH-RND\PycharmProjects\ConfigTool\data\Init.java"

GREEN_IMAGE_PATH                = os.path.join(PROJECT_DIR, "data\icons", "green.png")
GREEN_BLINKER_IMAGE_PATH        = os.path.join(PROJECT_DIR, "data\icons", "green_blink.png")
PEDESTRIAN_IMAGE_PATH           = os.path.join(PROJECT_DIR, "data\icons", "pedestrian.png")
BLINKER_IMAGE_PATH              = os.path.join(PROJECT_DIR, "data\icons", "blinker.png")
BLINKER_CONDITIONAL_IMAGE_PATH  = os.path.join(PROJECT_DIR, "data\icons", "blinker conditional.png")

# =============== log colors =============== #
RED = "\033[91m"
YELLOW = "\033[93m"
RESET = "\033[0m"

# =============== icons =============== #


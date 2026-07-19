import os


class Var:
    """
    This class contain all the global variables.
    """
    AUTHORITY       = None      # Enum: Authority.TEL_AVIV
    PROJECT_NAME    = None      # ta172 - HaShalom HaTayasim
    PROJECT_NUMBER  = None      # ta172
    IS_NEW_PROJECT  = None      #

    BASE_DIR = os.path.dirname(__file__)  # path of the file
    PROJECT_DIR = os.path.dirname(BASE_DIR)  # path of the project file

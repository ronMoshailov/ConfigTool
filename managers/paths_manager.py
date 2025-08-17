import os

from PyQt5.QtWidgets import QFileDialog

from entities.log import Log


class PathsManager:
    _instance = None

    # =========================================== #
    #                Construction                 #
    # =========================================== #
    def __new__(cls):
        if cls._instance is None:                                       # checks if there is an instance of the class
            cls._instance = super(PathsManager, cls).__new__(cls)       # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                    # run _init
        return cls._instance                                            # return _instance

    def __init__(self):
        self.path_project = None
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None

    # =========================================== #
    #                 add methods                 #
    # =========================================== #

    # =========================================== #
    #                  get methods                #
    # =========================================== #
    def get_path_init_tk1(self):
        """
        This method returns the path of 'initTK1.java'.

        :return: None
        """
        return self.path_init_tk1

    def get_path_init(self):
        """
        This method returns the path of 'init.java'.

        :return: None
        """
        return self.path_init

    # =========================================== #
    #               update methods                #
    # =========================================== #

    # =========================================== #
    #               remove methods                #
    # =========================================== #

    # =========================================== #
    #                general methods              #
    # =========================================== #
    def scan_set_paths(self):
        """
        This method set the paths of for all the files that needed.

        :return: False if failed, otherwise True.
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if not folder_path:
            return False

        self.path_project = folder_path

        init_found = False
        init_tk1_found = False
        tk1_found = False

        for root, dirs, files in os.walk(self.path_project):
            for file in files:
                if file.lower() == "init.java" and init_found is False:  # בודק בלי תלות ברישיות
                    self.path_init = os.path.join(root, file)
                    init_found = True
                elif file.lower() == "tk1.java" and tk1_found is False:
                    self.path_tk1 = os.path.join(root, file)
                    tk1_found = True
                elif file.lower() == "inittk1.java" and init_tk1_found is False:
                    self.path_init_tk1 = os.path.join(root, file)
                    init_tk1_found = True

        if not init_found:
            Log.warning(f"Warning: init.java wasn't found")
        if not tk1_found:
            Log.warning(f"Warning: Tk1.java wasn't found")
        if not init_tk1_found:
            Log.warning(f"Warning: initTk1.java wasn't found")
        return True

    # ============================================================
    # os.walk returns:
    #   root - current path in the scan
    #   dirs - list of all the folders in root
    #   files - list of all the files in root
    # ============================================================

    # ======================================================= #
    #    not needed for now but maybe in future I will use    #
    # ======================================================= #
    # def reset(self):
    #     """
    #     This method reset all the paths.
    #
    #     :return: None
    #     """
    #     self.path_project = None
    #     self.path_init = None
    #     self.path_tk1 = None
    #     self.path_init_tk1 = None
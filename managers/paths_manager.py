import os

from PyQt5.QtWidgets import QFileDialog


class PathsManager:
    _instance = None

    # =========================================== #
    #                class methods                #
    # =========================================== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:                                       # checks if there is an instance of the class
            cls._instance = super(PathsManager, cls).__new__(cls)     # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                    # run _init
        return cls._instance                                            # return _instance

    def __init__(self):
        self.path_project = None
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None

    # =========================================== #
    #                  get methods                #
    # =========================================== #
    def get_path_init_tk1(self):
        return self.path_init_tk1

    # =========================================== #
    #                general methods              #
    # =========================================== #
    def reset(self):
        self.path_project = None
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None

    def scan_set_paths(self):
        """
        This method set the paths of 'init.java' and 'Tk1.java'

        :return: None
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if not folder_path:
            print("Error in the root path")
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
                    print("init.java was found in:", self.path_init)
                elif file.lower() == "tk1.java" and tk1_found is False:
                    self.path_tk1 = os.path.join(root, file)
                    tk1_found = True
                    print("Tk1.java was found in:", self.path_tk1)
                elif file.lower() == "inittk1.java" and init_tk1_found is False:
                    self.path_init_tk1 = os.path.join(root, file)
                    init_tk1_found = True
                    print("Tk1.java was found in:", self.path_init_tk1)
                # ============================================================
                # os.walk returns:
                #   root - current path in the scan
                #   dirs - list of all the folders in root
                #   files - list of all the files in root
                # ============================================================

import os

from PyQt5.QtWidgets import QFileDialog


class PathsManager:
    def __init__(self):
        self.path_project = None
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None

    # =================== scan paths ===================
    def scan_set_paths(self):
        """
        This method set the paths of 'init.java' and 'Tk1.java'

        :return: None
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if not folder_path:
            print("Error in the root path")
            return None

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


    # =================== getter ===================
    def get_path_init_tk1(self):
        return self.path_init_tk1

    # =================== methods ===================
    def reset(self):
        self.path_project = None
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None

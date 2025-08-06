import os

from PyQt5.QtWidgets import QFileDialog


class PathsManager:
    def __init__(self):
        super().__init__()
        self.path_project = None
        self.path_init = None

    # =================== set paths ===================
    def set_path_project(self, path_project):
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if folder_path:
            self.path_project = folder_path
            self.set_init_path()

    # =================== scan paths ===================
    def set_init_path(self):
        if not self.path_project:
            print("path_project לא מוגדר.")
            return

        for root, dirs, files in os.walk(self.path_project):
            for file in files:
                if file.lower() == "init.java":  # בודק בלי תלות ברישיות
                    self.path_init = os.path.join(root, file)
                    print("init.java was found in:", self.path_init)
                    return
        print("init.java לא נמצא.")

    # =================== scan data ===================

# ============================================================
# os.walk returns:
#   root - current path in the scan
#   dirs - list of all the folders in root
#   files - list of all the files in root
# ============================================================
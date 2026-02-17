from pathlib import Path
import os
import shutil

from PyQt6.QtWidgets import QFileDialog, QMessageBox

import Config

class PathManager:
    def __init__(self):
        # Data
        self.path_project       = None

        # Source
        self.path_init          = None
        self.path_tk1           = None
        self.path_init_tk1      = None
        self.path_parameters_ta = None

        # Destination
        self.path_init_dst          = None
        self.path_tk1_dst           = None
        self.path_init_tk1_dst      = None
        self.path_parameters_ta_dst = None
        self.path_phue_folder_dst   = None
        self.path_phase_folder_dst  = None

    def create_project(self, view):
        """
        This method create new project and set the destinations paths
        """
        # set source project path
        source_folder = Path(os.path.join(Config.constants.PROJECT_DIR, "Template"))

        # user choose folder
        target_dir = QFileDialog.getExistingDirectory(view, "בחר תיקייה לשמירת הפרויקט")
        if not target_dir:
            return False

        # set target project path
        target_dir = Path(target_dir)

        # set destinations paths
        dst                         = target_dir / f"{Config.constants.PROJECT_NUMBER}"

        self.path_init_dst          = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "init.java"
        self.path_tk1_dst           = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "Tk1.java"
        self.path_init_tk1_dst      = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "initTk1.java"
        self.path_parameters_ta_dst = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "ParametersTelAviv.java"
        self.path_phue_folder_dst   = dst / "src" / "phue"
        self.path_phase_folder_dst  = dst / "src" / "phase"

        # If the folder exist remove it
        if dst.exists():
            shutil.rmtree(dst)

        # Copy the template project
        shutil.copytree(source_folder, dst)

        #
        self.path_phue_folder_dst.mkdir(parents=True, exist_ok=True)
        self.path_phase_folder_dst.mkdir(parents=True, exist_ok=True)

        # rename
        old_path = dst / "src" / "ta00"
        new_path = dst / "src" / f"{Config.constants.PROJECT_NUMBER}"

        old_path.rename(new_path)
        print(f"Folder renamed to {new_path}")

        QMessageBox.information(view, "הצלחה", "הפרויקט נשמר בהצלחה")
        return True

    def set_files_path(self, phue_paths):
        """
        This method set all the paths of the files that hold the data of the project
        """
        for root, dirs, files in os.walk(self.path_project):
            # ============================================================
            # os.walk returns:
            #   root - current path in the scan
            #   dirs - list of all the folders in root
            #   files - list of all the files in root
            # ============================================================
            for file in files:
                if file.lower() == "init.java":  # בודק בלי תלות ברישיות
                    self.path_init = os.path.join(root, file)
                elif file.lower() == "tk1.java":
                    self.path_tk1 = os.path.join(root, file)
                elif file.lower() == "inittk1.java":
                    self.path_init_tk1 = os.path.join(root, file)
                elif file.lower() == "parameterstelaviv.java":
                    self.path_parameters_ta = os.path.join(root, file)
                elif file.lower().startswith("phue") and file.lower().endswith(".java"):
                    path_to_add = os.path.join(root, file)
                    phue_paths.append(path_to_add)

    def set_folder_path(self):
        """
        This method set the folder of the project
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")  # choose folder window
        if not folder_path:
            return False
        self.path_project = folder_path
        return True


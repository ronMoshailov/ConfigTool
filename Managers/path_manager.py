from pathlib import Path
import os
import shutil

from PyQt6.QtWidgets import QFileDialog, QMessageBox

import Config

class PathManager:
    def __init__(self):
        # Data
        self.path_project = None

        # Source
        self.path_init = None
        self.path_tk1 = None
        self.path_init_tk1 = None
        self.path_parameters_ta = None

        # Destination
        self.path_init_dst = None
        self.path_tk1_dst = None
        self.path_init_tk1_dst = None
        self.path_parameters_ta_dst = None
        self.path_phue_folder_dst = None
        self.path_phase_folder_dst = None

    def create_copy(self, view):
        # create path
        source_folder = Path(os.path.join(Config.constants.PROJECT_DIR, "Original"))

        # user choose folder
        target_dir = QFileDialog.getExistingDirectory(view, "בחר תיקייה לשמירת הפרויקט")
        if not target_dir:
            return

        target_dir = Path(target_dir)

        dst = target_dir / f"{Config.constants.PROJECT_NUMBER}"

        self.path_init_dst = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "init.java"
        self.path_tk1_dst = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "Tk1.java"
        self.path_init_tk1_dst = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "initTk1.java"
        self.path_parameters_ta_dst = dst / "src" / f"{Config.constants.PROJECT_NUMBER}" / "ParametersTelAviv.java"
        self.path_phue_folder_dst = dst /  "src" /"phue"
        self.path_phase_folder_dst = dst /  "src" /"phase"

        try:
            # remove the old folder if exist
            if dst.exists():
                reply = QMessageBox.question(view, "אישור מחיקה", f"התיקייה כבר קיימת:\n{dst}\n\nלמחוק ולהמשיך?", QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No)

                if reply != QMessageBox.StandardButton.Yes:
                    return False

                # remove folder
                shutil.rmtree(dst)

            # copy
            shutil.copytree(source_folder, dst)

            # rename
            old_folder = dst / "src" / "ta00"
            new_folder = dst / "src" / f"{Config.constants.PROJECT_NUMBER}"

            old_folder.rename(new_folder)
            print(f"Folder renamed to {new_folder}")

            QMessageBox.information(view, "הצלחה", "הפרויקט נשמר בהצלחה")
            return True

        except Exception as e:
            QMessageBox.critical(view, "שגיאה", str(e))
            return False


    def set_files_path(self, phue_paths):
        """
        Set all the path of the files that hold the data.
        :return:
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
        Set folder path.
        :return:
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")  # choose folder window
        if not folder_path:
            print(f"folder_path is false")
            return
        self.path_project = folder_path

    # def _update_package(self, path):
    #     with open(path, 'r', encoding='utf-8') as f:
    #         lines = f.readlines()
    #
    #     for i, line in enumerate(lines):
    #         if line.strip().startswith("package"):
    #             lines[i] = f"package {Config.constants.PROJECT_NUMBER};"
    #             break
    #
    #     with open(path, 'w', encoding='utf-8') as f:
    #         f.writelines(lines)

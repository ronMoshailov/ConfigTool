from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QFileDialog, QMessageBox, QMainWindow, QTextEdit, QPushButton, \
    QVBoxLayout, QDialog
import os
import re
from pathlib import Path
import shutil
import Config
from Config.patterns import  sk_pattern
from Config.style import main_window_style
from Controllers.parameters_controller import ParametersTaController
from Models.detector_model import DetectorModel
from Models.image_model import ImageModel
from Models.io_model import IoModel
from Models.matrix_model import MatrixModel
from Models.move_model import MoveModel
from Models.parameters_ta_model import ParametersTaModel
from Models.phue_model import PhueModel
from Models.schedule_model import ScheduleModel
from Models.settings_model import SettingsModel
from Models.sk_model import SkModel
from View.image_view import ImageView

from View.matrix_view import MatrixView
from View.navigator_view import NavigatorView
from View.detector_view import DetectorView
from View.move_view import MoveView
# from View.min_green_view import MinGreenView

from Controllers.detector_controller import DetectorController
from Controllers.image_controller import ImageController
from Controllers.io_controller import IoController
from Controllers.matrix_controller import MatrixController
# from Controllers.min_green_controller import MinGreenController
from Controllers.move_controller import MoveController
from Controllers.phue_controller import PhueController
from Controllers.schedule_controller import ScheduleController
from Controllers.settings_controller import SettingsController
from Controllers.sk_controller import SkController
from View.parameters_ta_view import ParametersTaView
from View.phue_view import PhueView
from View.schedule_view import ScheduleView
from View.settings_view import SettingsView
from View.sk_view import SkView


class MainController:
    """
    Main application window for the Config Tool.
    Holds all panels in a single layout.
    """
    def __init__(self):
        self.root = QMainWindow()

        self.main_root = QWidget()
        self.main_root.setWindowTitle("Tel Aviv Version")
        self.path_project = None

        # =============== models =============== #
        self.move_model = MoveModel()
        self.detector_model = DetectorModel()
        self.image_model = ImageModel()
        self.phue_model = PhueModel()
        self.matrix_model = MatrixModel()
        self.schedule_model = ScheduleModel()
        self.settings_model = SettingsModel()
        self.sk_model = SkModel()
        self.parameters_ta_model = ParametersTaModel()

        # =============== views =============== #
        self.navigator_view = NavigatorView(self.show_view, self.print_all)
        self.settings_view = SettingsView()
        self.detector_view = DetectorView()
        # self.min_green_view = MinGreenView()
        self.matrix_view = MatrixView()
        self.move_view = MoveView()
        self.sk_view = SkView()
        self.schedule_view = ScheduleView()
        self.image_view = ImageView()
        self.phue_view = PhueView()
        self.parameters_ta_view = ParametersTaView()

        # =============== controllers =============== #
        self.image_controller = ImageController(self.image_view, self.image_model)
        # self.io_controller = IoController(root)
        self.matrix_controller = MatrixController(self.matrix_view, self.matrix_model)
        self.move_controller = MoveController(self.move_view, self.move_model)
        # self.min_green_controller = MinGreenController(self.min_green_view, self.move_model)
        self.phue_controller = PhueController(self.phue_view, self.phue_model)
        self.schedule_controller = ScheduleController(self.schedule_view, self.schedule_model)
        self.settings_controller = SettingsController(self.settings_view, self.settings_model)
        self.sk_controller = SkController(self.sk_view, self.sk_model)
        self.detector_controller = DetectorController(self.detector_view, self.detector_model)
        self.parameters_ta_controller = ParametersTaController(self.parameters_ta_view, self.parameters_ta_model)

        # =============== layout =============== #
        root_layout = QHBoxLayout()
        root_layout.setAlignment(Qt.AlignmentFlag.AlignRight)
        root_layout.addWidget(self.move_view)
        # root_layout.addWidget(self.min_green_view)
        root_layout.addWidget(self.detector_view)
        root_layout.addWidget(self.settings_view)
        root_layout.addWidget(self.matrix_view)
        root_layout.addWidget(self.sk_view)
        root_layout.addWidget(self.schedule_view)
        root_layout.addWidget(self.image_view)
        root_layout.addWidget(self.phue_view)
        root_layout.addWidget(self.parameters_ta_view)
        root_layout.addWidget(self.navigator_view)

        # =============== self =============== #
        self.main_root.setLayout(root_layout)
        self.move_controller.view.update_names_method = self.update_names

        self.root.setCentralWidget(self.main_root)
        self.root.setStyleSheet(main_window_style)
        self.root.show()
        self.root.showMaximized()          # show in full-screen

        self.navigator_view.write_to_code_method = self.write_to_code
        self.move_controller.remove_from_matrix_method = self.matrix_controller.remove_from_matrix


    def show_view(self, act):
        """
        Determines which view should be displayed based on the given action.

        :param act: An action identifier that specifies which operation/view to perform.
        :return: None
        """
        # hide all views
        self.settings_view.hide_view()
        self.move_view.hide_view()
        # self.min_green_view.hide_view()
        self.matrix_view.hide_view()
        self.detector_view.hide_view()
        self.schedule_view.hide_view()
        self.sk_view.hide_view()
        self.image_view.hide_view()
        self.phue_view.hide_view()
        self.parameters_ta_view.hide_view()

        if act == "init":
            self._initialize_app()
            return

        if self.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "פרויקט לא מאותחל")
            return

        # if act == "settings":
        # self.settings_controller.show_view()
        if act == "move":
            self.move_controller.show_view()
        # elif act == "min_green":
        #     self.min_green_controller.show_view()
        elif act == "matrix":
            self.matrix_controller.show_view(self.move_model.all_moves)
        elif act == "detector":
            self.detector_controller.show_view(self.move_model.get_all_moves_names())
        elif act == "sk":
            self.sk_controller.show_view(self.move_model.all_moves)
        elif act == "schedule":
            self.schedule_controller.show_view()
            self.schedule_view.show_view()
        elif act == "image":
            self.image_controller.show_view(self.move_model.all_moves)
        elif act == "phue":
            self.phue_controller.show_view(self.image_model.all_images, self.move_model.all_moves)
        elif act == "parameters_ta":
            if not self.image_model.is_sp_valid():
                QMessageBox.critical(self.main_root, "שגיאה", "רצף נקודות ההחלטה לא תקינות")
                self.image_controller.show_view(self.move_model.all_moves)
                return
            self.parameters_ta_controller.show_view(self.image_model.all_images)
        else:
            self.settings_controller.show_view()

    def print_all(self):
        """
        This method prints all the data for the window.
        :return: None
        """
        if self.path_project is None:
            return

        out = []

        # out.append("\n============================== Moves ==============================")
        # for move in self.move_model.all_moves:
        #     out.append(
        #         f"name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")

        out.append("\n============================== Settings ==============================")
        out.append(
            f"junction_num: {self.settings_model.junction_num:<5}, junction_name: {self.settings_model.junction_name:<25}, version: {self.settings_model.version:<8}, first_ext: {self.settings_model.first_ext:<3}")
        for h in self.settings_model.history:
            date, author = h
            out.append(f"date: {date:<5}, author: {author:<25}")


        # out.append("\n============================== Matrix ==============================")
        # for cell in self.matrix_model.all_cells:
        #     out.append(f"out: {cell.move_out:<5}, in: {cell.move_in:<5}, wait: {cell.wait_time:<5}")

        # out.append("\n============================== SK ==============================")
        # for sk_card in self.sk_model.sk_list:
        #     out.append(f"----------------------------- sk:{sk_card.card_number} -----------------------------")
        #     for channel in sk_card.all_channels:
        #         out.append(
        #             f"name: {channel.name:<5}, color: {channel.color:<10}, channel: {channel.channel:<5}, is_comment: {channel.is_comment}")
        #     out.append("")

        # out.append("\n============================== Detector ==============================")
        # for detector in self.detector_model.all_detectors:
        #     out.append(f"var_name: {detector.var_name:<5}, class_name: {detector.class_name:<10}, datector_name: {detector.datector_name:<5}, move_name: {detector.move_name:<10}, ext_unit: {detector.ext_unit:<10}")

        # out.append("\n============================== Schedule ==============================")
        # for schedule_table in self.schedule_model.all_schedule_tables:
        #     out.append(f"----- table: {schedule_table.table_num} -----")
        #     for cell in schedule_table.cell_list:
        #         out.append(f"hour: {cell.hour:<5}, minute: {cell.minute:<5}, program_num: {cell.prog_num:<5}")
        #
        # out.append("\n============================== Image ==============================")
        # for image in self.image_model.all_images:
        #     if image.image_name == 'A':
        #         out.append(
        #             f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, is_police: {image.is_police:<5}")
        #     else:
        #         out.append(
        #             f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, sp: {image.sp:<5}, is_police: {image.is_police:<5}")
        #     move_str = ", ".join([m.name for m in image.move_list])
        #     out.append(f"moves: {move_str}\n")
        #
        # out.append("\n============================== Phue ==============================")
        # for phue in self.phue_model.all_phue:
        #     out.append(f"{phue.image_out:<4} → {phue.image_in:<4}, length: {phue.length:<3}")
        #     for tran in phue.transitions:
        #         out.append(f"move: {tran.move:<4}, state: {tran.state:<5}, duration: {tran.duration:<5}")
        #     out.append("")
        #
        # out.append("\n============================== Parameters ==============================")
        # for parameter in self.parameters_ta_model.parameters:
        #     out.append(f"Program number: {parameter.program_number:<4}")
        #     min_str = ""
        #     for min_param in parameter.min_list:
        #         min_str += f"{min_param:<4}"
        #     out.append(f"Min: {min_str}")
        #     max_str = ""
        #     for max_param in parameter.max_list:
        #         max_str += f"{max_param:<4}"
        #     out.append(f"Max: {max_str}")
        #     type_str = ""
        #     for type_param in parameter.type_list:
        #         type_str += f"{type_param:<4}"
        #     out.append(f"Type: {type_str}")
        #
        #     out.append(f"str: {parameter.str:<4}")
        #     out.append(f"cycle: {parameter.cycle:<4}")
        #     out.append("")

        text_to_show = "\n".join(out)

        # create window for the data
        dialog = QDialog(self.root)
        dialog.setWindowTitle("Print All Output")
        dialog.resize(800, 600)

        text_edit = QTextEdit()
        text_edit.setReadOnly(True)
        text_edit.setText(text_to_show)

        layout = QVBoxLayout()
        layout.addWidget(text_edit)

        dialog.setLayout(layout)
        dialog.exec()

    def write_to_code(self):
        if not self.matrix_controller.is_matrix_valid():
            return

        if self.image_model.is_sp_valid():
            print("sp valid")
        if not self._create_copy():
            return

        self.write_imports()

        self.move_controller.write_to_file(self.path_tk1_dst, self.path_init_tk1_dst)
        self.matrix_controller.write_to_file(self.path_init_tk1_dst)
        self.schedule_controller.write_to_file(self.path_init_tk1_dst)
        self.sk_controller.write_to_file(self.path_init_dst)
        self.image_controller.write_to_file(self.path_tk1_dst, self.path_init_tk1_dst, self.path_phase_folder_dst)
        self.phue_controller.write_to_file(self.path_init_tk1_dst, self.path_phue_folder_dst)
        self.parameters_ta_controller.write_to_file(self.path_parameters_ta_dst, self.image_model.get_images_by_sp())
        self.detector_controller.write_to_file(self.path_init_tk1_dst, self.path_tk1_dst)

    def write_imports(self):

        # data
        code = []

        # update tk1.java file
        with open(self.path_init_tk1_dst, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write imports here" in line:
                for image in self.image_model.all_images:
                    if image.image_name == "A":
                        continue
                    line = f"import phase.Phase{image.image_name};\n"
                    code.append(line)
                continue
            code.append(line)

        with open(self.path_init_tk1_dst, 'w', encoding='utf-8') as f:
            f.writelines(code)



    # =============== inner methods =============== #
    def _update_package(self, path):
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for i, line in enumerate(lines):
            if line.strip().startswith("package"):
                lines[i] = f"package {Config.constants.PROJECT_NUMBER};"
                break

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(lines)

    def _initialize_app(self):
        """
        Initialize all the data for the models.
        :return: None
        """
        # set project path
        self._set_folder_path()
        if self.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "לא נבחרה תיקייה")
            return

        # set files of use
        self._set_files_path()

        # init settings moves
        self.settings_controller.init_model(self.path_init)

        # init sk moves
        self.sk_controller.init_model(self.path_init)

        # init project moves
        self.move_controller.init_model(self.path_init_tk1)

        # init detectors moves
        self.detector_controller.init_model(self.path_init_tk1)

        # init images moves
        self.image_controller.init_model(self.path_init_tk1, self.move_model.all_moves)

        # init phue moves
        self.phue_controller.init_model(self.phue_model.phue_paths, self.path_init_tk1)

        # init matrix
        self.matrix_controller.init_model(self.path_init_tk1)

        # init schedule
        self.schedule_controller.init_model(self.path_init_tk1)

        #
        self.parameters_ta_controller.init_model(self.path_parameters_ta, len(self.image_model.all_images))

        self.settings_controller.show_view()

    def _set_folder_path(self):
        """
        Set folder path.
        :return:
        """
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")  # choose folder window
        if not folder_path:
            print(f"folder_path is false")
            return
        self.path_project = folder_path

    def _set_files_path(self):
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
                elif file.lower().startswith("phue"):
                    self.phue_model.phue_paths.append(os.path.join(root, file))

    def _create_copy(self):
        # create path
        source_folder = Path(os.path.join(Config.constants.PROJECT_DIR, "Original"))

        # user choose folder
        target_dir = QFileDialog.getExistingDirectory(self.root, "בחר תיקייה לשמירת הפרויקט")
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
                reply = QMessageBox.question(self.root, "אישור מחיקה", f"התיקייה כבר קיימת:\n{dst}\n\nלמחוק ולהמשיך?", QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No)

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

            QMessageBox.information(self.root, "הצלחה", "הפרויקט נשמר בהצלחה")
            return True

        except Exception as e:
            QMessageBox.critical(self.root, "שגיאה", str(e))
            return False

    def update_names(self, old_name, new_name):
        self.matrix_controller.update_names(old_name, new_name)
        self.move_controller.update_names(old_name, new_name)



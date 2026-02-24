from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QMessageBox, QMainWindow, QTextEdit, QVBoxLayout, QDialog

import Config

from Controllers.parameters_controller import ParametersTaController
from Controllers.detector_controller import DetectorController
from Controllers.image_controller import ImageController
from Controllers.matrix_controller import MatrixController
from Controllers.move_controller import MoveController
from Controllers.phue_controller import PhueController
from Controllers.schedule_controller import ScheduleController
from Controllers.settings_controller import SettingsController
from Controllers.sk_controller import SkController

from View.image_view import ImageView
from View.matrix_view import MatrixView
from View.navigator_view import NavigatorView
from View.detector_view import DetectorView
from View.move_view import MoveView
from View.parameters_ta_view import ParametersTaView
from View.phue_view import PhueView
from View.schedule_view import ScheduleView
from View.settings_view import SettingsView
from View.sk_view import SkView

from Models.detector_model import DetectorModel
from Models.image_model import ImageModel
from Models.matrix_model import MatrixModel
from Models.move_model import MoveModel
from Models.parameters_ta_model import ParametersTaModel
from Models.phue_model import PhueModel
from Models.schedule_model import ScheduleModel
from Models.settings_model import SettingsModel
from Models.sk_model import SkModel

from Managers.path_manager import PathManager
from Managers.load_data_manager import LoadDataManager

class MainController:
    def __init__(self):
        # =============== Managers =============== #
        self.path_manager       = PathManager()

        # =============== Models =============== #
        self.settings_model         = SettingsModel()
        self.move_model             = MoveModel()
        self.matrix_model           = MatrixModel()
        self.sk_model               = SkModel()
        self.detector_model         = DetectorModel()
        self.schedule_model         = ScheduleModel()
        self.image_model            = ImageModel()
        self.phue_model             = PhueModel()
        self.parameters_ta_model    = ParametersTaModel()




        # =============== Views =============== #
        self.settings_view      = SettingsView()
        self.move_view          = MoveView()
        self.matrix_view        = MatrixView()
        self.sk_view            = SkView()
        self.detector_view      = DetectorView()
        self.schedule_view      = ScheduleView()
        self.image_view         = ImageView()
        self.phue_view          = PhueView()
        self.parameters_ta_view = ParametersTaView()



        self.navigator_view     = NavigatorView(self.show_view, self.print_all)

        # =============== Controllers =============== #
        self.settings_controller        = SettingsController(self.settings_view, self.settings_model)
        self.move_controller            = MoveController(self.move_view, self.move_model)
        self.matrix_controller          = MatrixController(self.matrix_view, self.matrix_model)
        self.sk_controller              = SkController(self.sk_view, self.sk_model)
        self.detector_controller        = DetectorController(self.detector_view, self.detector_model)
        self.schedule_controller        = ScheduleController(self.schedule_view, self.schedule_model)
        self.image_controller           = ImageController(self.image_view, self.image_model)
        self.phue_controller            = PhueController(self.phue_view, self.phue_model)
        self.parameters_ta_controller   = ParametersTaController(self.parameters_ta_view, self.parameters_ta_model)



        # self.io_controller            = IoController(root)

        # =============== Set Controllers Methods =============== #
        self.move_controller.view.rename_move_method           = self.rename_move
        self.move_controller.global_remove_move                 = self.global_remove_move
        self.move_controller.remove_move_from_matrix_method     = self.matrix_controller.remove_move
        self.matrix_controller.get_move_type                    = self.move_controller.get_move_type
        self.navigator_view.write_to_code_method                = self.write_to_code
        self.parameters_ta_controller.get_sp_by_image_method    = self.image_controller.get_sp_by_image
        self.detector_controller.get_all_moves_names            = self.move_controller.get_all_moves_names

        # =============== Root Layout =============== #
        root_layout = QHBoxLayout()
        root_layout.addWidget(self.move_view)
        root_layout.addWidget(self.detector_view)
        root_layout.addWidget(self.settings_view)
        root_layout.addWidget(self.matrix_view)
        root_layout.addWidget(self.sk_view)
        root_layout.addWidget(self.schedule_view)
        root_layout.addWidget(self.image_view)
        root_layout.addWidget(self.phue_view)
        root_layout.addWidget(self.parameters_ta_view)
        root_layout.addWidget(self.navigator_view)
        root_layout.setAlignment(Qt.AlignmentFlag.AlignRight)

        # =============== Main Root =============== #
        self.main_root = QWidget()
        self.main_root.setLayout(root_layout)

        # =============== Root Widget =============== #
        self.root = QMainWindow()
        self.root.setWindowTitle("Tel Aviv Version")
        self.root.setCentralWidget(self.main_root)
        self.root.setStyleSheet(Config.style.main_window_style)
        self.root.show()
        self.root.showMaximized()          # show in full-screen

    def show_view(self, act):
        # Hide All Views
        self.settings_controller.hide_view()
        self.move_controller.hide_view()
        self.matrix_controller.hide_view()

        self.matrix_view.hide_view()
        self.detector_view.hide_view()
        self.schedule_view.hide_view()
        self.sk_view.hide_view()
        self.image_view.hide_view()
        self.phue_view.hide_view()
        self.parameters_ta_view.hide_view()

        # Initialize app
        if act == "init":
            self._initialize_app()

        # Check if the project initialized
        if self.path_manager.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "פרויקט לא מאותחל")
            return

        # Choose which view to show
        if act == "move":
            self.move_controller.show_view()
        elif act == "matrix":
            self.matrix_controller.show_view(self.move_model.all_moves)
        elif act == "detector":
            self.detector_controller.show_view()
        elif act == "sk":
            self.sk_controller.show_view(self.move_model.all_moves)
        elif act == "schedule":
            self.schedule_controller.show_view()
        elif act == "image":
            self.image_controller.show_view(self.move_controller.get_all_moves_names())
        elif act == "phue":
            self.phue_controller.show_view(self.image_model.all_images, self.move_controller.get_all_moves_names())
        elif act == "parameters_ta":
            if not self.image_model.is_sp_valid():
                QMessageBox.critical(self.main_root, "שגיאה", "רצף נקודות ההחלטה לא תקינות")
                return
            self.parameters_ta_controller.show_view(self.image_model.all_images)
        else:
            self.settings_controller.show_view()

    # ============================== CRUD ============================== #
    def rename_move(self, old_name, new_name):
        try:
            self.move_controller.rename_move(old_name, new_name)        # must be first
            self.matrix_controller.rename_move(old_name, new_name)
            self.sk_controller.rename_move(old_name, new_name)
            self.detector_controller.rename_move(old_name, new_name)
            self.image_controller.rename_move(old_name, new_name)
            self.phue_controller.rename_move(old_name, new_name)
        except Exception as e:
            print(str(e))

    def global_remove_move(self, move_name):
        # self.detector_model.remove_move(move_name)
        self.image_controller.remove_move(move_name)
        self.phue_model.remove_move(move_name)
        self.matrix_controller.remove_move(move_name)
        self.sk_controller.remove_move(move_name)

    # ============================== Logic ============================== #
    def reset_all(self):
        self.sk_controller.reset()
        self.phue_controller.reset()
        self.move_controller.reset()
        self.matrix_controller.reset()
        self.image_controller.reset()
        self.detector_controller.reset()
        self.schedule_controller.reset()
        self.settings_controller.reset()
        self.parameters_ta_controller.reset()

    def _initialize_app(self):
        # Set Project Path
        if not self.path_manager.set_folder_path():
            QMessageBox.critical(self.main_root, "שגיאה", "לא נבחרה תיקייה")
            return

        # Reset
        self.reset_all()

        # Initialize Controllers
        self.path_manager.set_files_path(self.phue_model.phue_paths)
        self.settings_controller.init_model(self.path_manager.path_init)
        self.move_controller.init_model(self.path_manager.path_init_tk1)
        self.matrix_controller.init_model(self.path_manager.path_init_tk1)
        self.sk_controller.init_model(self.path_manager.path_init)

        self.detector_controller.init_model(self.path_manager.path_init_tk1)
        self.image_controller.init_model(self.path_manager.path_init_tk1, self.move_controller.get_all_moves_names())
        self.phue_controller.init_model(self.phue_model.phue_paths, self.path_manager.path_init_tk1)
        self.schedule_controller.init_model(self.path_manager.path_init_tk1)
        self.parameters_ta_controller.init_model(self.path_manager.path_parameters_ta, len(self.image_model.all_images))

    # ============================== Write to file ============================== #
    def write_to_code(self):
        if not self.matrix_controller.is_matrix_valid():
            return

        if not self.phue_controller.is_names_valid():
            self.show_view("phue")
            return

        if self.image_model.is_sp_valid():
            print("sp valid")
        if not self.path_manager.create_project(self.root):
            return

        self.write_phase_imports()

        self.settings_controller.write_settings_to_project(self.path_manager.path_init_dst)
        self.move_controller.write_moves_to_project(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst)
        self.matrix_controller.write_matrix_to_file(self.path_manager.path_init_tk1_dst)
        self.sk_controller.write_to_file(self.path_manager.path_init_dst)

        self.schedule_controller.write_to_file(self.path_manager.path_init_tk1_dst)
        self.image_controller.write_to_file(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst, self.path_manager.path_phase_folder_dst)
        self.phue_controller.write_to_file(self.path_manager.path_init_tk1_dst, self.path_manager.path_phue_folder_dst)
        self.parameters_ta_controller.write_to_file(self.path_manager.path_parameters_ta_dst, self.path_manager.path_init_tk1_dst, self.image_controller.fetch_images_by_sp())
        self.detector_controller.write_to_file(self.path_manager.path_init_tk1_dst, self.path_manager.path_tk1_dst)

    def write_phase_imports(self):
        with open(self.path_manager.path_init_tk1_dst, 'r', encoding='utf-8') as file:
            code = []
            for line in file:
                if "write imports here" in line:
                    for image in self.image_model.all_images:
                        if image.image_name == "A":
                            continue
                        line = f"import phase.Phase{image.image_name};\n"
                        code.append(line)
                    continue
                code.append(line)

        with open(self.path_manager.path_init_tk1_dst, 'w', encoding='utf-8') as f:
            f.writelines(code)

    # ============================== Debug ============================== #
    def print_all(self):
        """
        This method prints all the data for the window.
        :return: None
        """
        if self.path_manager.path_project is None:
            return

        out = ["\n============================== Moves =============================="]

        for move in self.move_model.all_moves:
            out.append(f"name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")

        out.append("\n============================== Settings ==============================")
        out.append(f"junction_num: {self.settings_model.junction_num:<5}, junction_name: {self.settings_model.junction_name:<25}, version: {self.settings_model.version:<8}, first_ext: {self.settings_model.first_ext:<3}")
        for h in self.settings_model.history:
            date, author = h
            out.append(f"date: {date:<5}, author: {author:<25}")


        out.append("\n============================== Matrix ==============================")
        for cell in self.matrix_model.all_cells:
            out.append(f"out: {cell.move_out:<5}, in: {cell.move_in:<5}, wait: {cell.wait_time:<5}")

        out.append("\n============================== SK ==============================")
        for sk_card in self.sk_model.sk_list:
            out.append(f"----------------------------- sk:{sk_card.card_number} -----------------------------")
            for channel in sk_card.all_channels:
                out.append(f"name: {channel.name:<5}, color: {channel.color:<10}, channel: {channel.channel:<5}, is_comment: {channel.is_comment}")
            out.append("")

        out.append("\n============================== Detector ==============================")
        for detector in self.detector_model.all_detectors:
            out.append(f"var_name: {detector.var_name:<5}, class_name: {detector.class_name:<10}, datector_name: {detector.datector_name:<5}, move_name: {detector.move_name:<10}, ext_unit: {detector.ext_unit:<10}")

        out.append("\n============================== Schedule ==============================")
        for schedule_table in self.schedule_model.all_schedule_tables:
            out.append(f"----- table: {schedule_table.table_num} -----")
            for cell in schedule_table.cell_list:
                out.append(f"hour: {cell.hour:<5}, minute: {cell.minute:<5}, program_num: {cell.prog_num:<5}")

        out.append("\n============================== Image ==============================")
        for image in self.image_model.all_images:
            if image.image_name == 'A':
                out.append(
                    f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, is_police: {image.is_police:<5}")
            else:
                out.append(
                    f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, sp: {image.sp:<5}, is_police: {image.is_police:<5}")
            move_str = ", ".join(image.move_names_list)
            out.append(f"moves: {move_str}\n")

        out.append("\n============================== Phue ==============================")
        for phue in self.phue_model.all_phue:
            out.append(f"{phue.image_out:<4} → {phue.image_in:<4}, length: {phue.length:<3}")
            for tran in phue.transitions:
                out.append(f"move: {tran.move_name:<4}, state: {tran.state:<5}, duration: {tran.duration:<5}")
            out.append("")

        out.append("\n============================== Parameters ==============================")
        for parameter in self.parameters_ta_model.parameters:
            out.append(f"Program number: {parameter.program_number:<4}")
            min_str = ""
            for min_param in parameter.min_list:
                min_str += f"{min_param:<4}"
            out.append(f"Min: {min_str}")
            max_str = ""
            for max_param in parameter.max_list:
                max_str += f"{max_param:<4}"
            out.append(f"Max: {max_str}")
            type_str = ""
            for type_param in parameter.type_list:
                type_str += f"{type_param:<4}"
            out.append(f"Type: {type_str}")

            out.append(f"str: {parameter.str:<4}")
            out.append(f"cycle: {parameter.cycle:<4}")
            out.append("")

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

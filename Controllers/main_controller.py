from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QMessageBox, QMainWindow

from Config.exceptions import InvalidMoveName, DuplicateMoveError
from Config.style import main_window_style
from Config.variables import Var
from Controllers.io64_controller import Io64Controller
from Controllers.io24_controller import Io24Controller

from Controllers.parameters_controller import ParametersTaController
from Controllers.detector_controller import DetectorController
from Controllers.image_controller import ImageController
from Controllers.matrix_controller import MatrixController
from Controllers.move_controller import MoveController
from Controllers.phue_controller import PhueController
from Controllers.schedule_controller import ScheduleController
from Controllers.settings_controller import SettingsController
from Controllers.sk_controller import SkController
from Managers.display_manager import DisplayManager
from Managers.write_data_manager import WriteDataManager
from Models.io64_model import Io64Model
from Models.io24_model import Io24Model

from View.image_view import ImageView
from View.io24_view import Io24View
from View.io64_view import Io64View
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


class MainController:
    def __init__(self):
        # =============== Managers =============== #
        self.path_manager       = PathManager()
        self.display_manager    = DisplayManager()

        # =============== Models =============== #
        self.settings_model         = SettingsModel()
        self.move_model             = MoveModel()
        self.matrix_model           = MatrixModel()
        self.sk_model               = SkModel()
        self.io24_model             = Io24Model()
        self.io64_model             = Io64Model()
        self.detector_model         = DetectorModel()
        self.schedule_model         = ScheduleModel()
        self.image_model            = ImageModel()
        self.phue_model             = PhueModel()
        self.parameters_ta_model    = ParametersTaModel()

        # =============== Views =============== #
        self.settings_view          = SettingsView()
        self.move_view              = MoveView()
        self.matrix_view            = MatrixView()
        self.sk_view                = SkView()
        self.io24_view              = Io24View()
        self.io64_view              = Io64View()
        self.detector_view          = DetectorView()
        self.schedule_view          = ScheduleView()
        self.image_view             = ImageView()
        self.phue_view              = PhueView()
        self.parameters_ta_view     = ParametersTaView()
        self.navigator_view         = NavigatorView()

        # =============== Controllers =============== #
        self.settings_controller        = SettingsController(self.settings_view, self.settings_model)
        self.move_controller            = MoveController(self.move_view, self.move_model)
        self.matrix_controller          = MatrixController(self.matrix_view, self.matrix_model)
        self.sk_controller              = SkController(self.sk_view, self.sk_model)
        self.io24_controller            = Io24Controller(self.io24_view, self.io24_model)
        self.io64_controller            = Io64Controller(self.io64_view, self.io64_model)
        self.detector_controller        = DetectorController(self.detector_view, self.detector_model)
        self.schedule_controller        = ScheduleController(self.schedule_view, self.schedule_model)
        self.image_controller           = ImageController(self.image_view, self.image_model)
        self.phue_controller            = PhueController(self.phue_view, self.phue_model)
        self.parameters_ta_controller   = ParametersTaController(self.parameters_ta_view, self.parameters_ta_model)

        # =============== Set Controllers Methods =============== #
        self.move_controller.view.rename_move_method            = self.rename_move
        self.move_controller.global_remove_move                 = self.global_remove_move
        self.move_controller.remove_move_from_matrix_method     = self.matrix_controller.remove_move
        self.matrix_controller.get_move_type                    = self.move_controller.get_move_type
        self.navigator_view.write_to_code_method                = self.write_to_code
        self.parameters_ta_controller.get_sp_by_image_method    = self.image_controller.get_sp_by_image
        self.detector_controller.get_all_moves_names            = self.move_controller.get_all_moves_names
        self.navigator_view.show_view_method                    = self.show_view

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
        root_layout.addWidget(self.io64_view)
        root_layout.addWidget(self.io24_view)
        root_layout.addWidget(self.navigator_view)
        root_layout.setAlignment(Qt.AlignmentFlag.AlignRight)

        # =============== Main Root =============== #
        self.main_root = QWidget()
        self.main_root.setLayout(root_layout)

        # =============== Logic =============== #
        self.set_display_logic()

        # =============== Root Widget =============== #
        self.root = QMainWindow()
        self.root.setWindowTitle("Config Tool")
        self.root.setCentralWidget(self.main_root)
        self.root.setStyleSheet(main_window_style)
        self.root.show()
        self.root.showMaximized()  # show in full-screen

    def show_view(self, act):
        # Initialize app
        if act == "init":
            if self._initialize_app():
                Var.authority = self.navigator_view.authority_combo.currentText()
                self.navigator_view.authority_combo.setDisabled(True)
                self.display_manager.show("settings")
            return

        # Check if the project initialized
        if self.path_manager.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "פרויקט לא מאותחל")
            return

        # Choose which view to show
        if act == "matrix":
            self.display_manager.show("matrix", self.move_model.all_moves)
        elif act == "image":
            self.display_manager.show("image", self.move_controller.get_all_moves_names())
        elif act == "phue":
            self.display_manager.show("phue", self.image_model.all_images, self.move_controller.get_all_moves_names())
        elif act == "parameters_ta":
            self.display_manager.show("parameters_ta", self.image_model.all_images)
        elif act == "sk":
            self.display_manager.show("sk", self.move_model.all_moves)
        else:
            self.display_manager.show(act)

    # ============================== CRUD ============================== #
    def rename_move(self, old_name, new_name):
        try:
            self.move_controller.rename_move(old_name, new_name)  # must be first
            self.matrix_controller.rename_move(old_name, new_name)
            self.sk_controller.rename_move(old_name, new_name)
            self.detector_controller.rename_move(old_name, new_name)
            self.image_controller.rename_move(old_name, new_name)
            self.phue_controller.rename_move(old_name, new_name)
        except (InvalidMoveName, DuplicateMoveError):
            return

    def global_remove_move(self, move_name):
        # self.detector_model.remove_move(move_name)
        self.image_controller.remove_move(move_name)
        self.phue_model.remove_move(move_name)
        self.matrix_controller.remove_move(move_name)
        self.sk_controller.remove_move(move_name)
        self.detector_controller.remove_move(move_name)

    # ============================== Logic ============================== #
    def reset_models(self):
        self.sk_controller.reset()
        self.phue_controller.reset()
        self.move_controller.reset()
        self.matrix_controller.reset()
        # self.io_controller.reset()
        # self.io64_controller.reset()
        self.image_controller.reset()
        self.detector_controller.reset()
        self.schedule_controller.reset()
        self.settings_controller.reset()
        self.parameters_ta_controller.reset()

    def _initialize_app(self):
        # Set Project Path
        if not self.path_manager.set_folder_path():
            QMessageBox.critical(self.main_root, "שגיאה", "לא נבחרה תיקייה")
            return False

        # Reset
        self.reset_models()

        # Initialize Controllers
        self.path_manager.set_files_path(self.phue_model.phue_paths)
        self.settings_controller.init_model(self.path_manager.path_init)
        self.move_controller.init_model(self.path_manager.path_init_tk1)
        self.matrix_controller.init_model(self.path_manager.path_init_tk1)
        self.sk_controller.init_model(self.path_manager.path_init)
        self.io24_controller.init_model(self.path_manager.path_init)
        self.io64_controller.init_model(self.path_manager.path_init)

        self.detector_controller.init_model(self.path_manager.path_init_tk1)
        self.image_controller.init_model(self.path_manager.path_init_tk1, self.move_controller.get_all_moves_names())
        self.phue_controller.init_model(self.phue_model.phue_paths, self.path_manager.path_init_tk1)
        self.schedule_controller.init_model(self.path_manager.path_init_tk1)
        self.parameters_ta_controller.init_model(self.path_manager.path_parameters_ta, len(self.image_model.all_images))

        # self.path_manager.load_project_number_and_name()
        return True

    def set_display_logic(self):
        self.display_manager.register("move"            , self.move_controller)
        self.display_manager.register("matrix"          , self.matrix_controller)
        self.display_manager.register("detector"        , self.detector_controller)
        self.display_manager.register("sk"              , self.sk_controller)
        self.display_manager.register("io24"            , self.io24_controller)
        self.display_manager.register("io64"            , self.io64_controller)
        self.display_manager.register("schedule"        , self.schedule_controller)
        self.display_manager.register("image"           , self.image_controller)
        self.display_manager.register("phue"            , self.phue_controller)
        self.display_manager.register("parameters_ta"   , self.parameters_ta_controller)
        self.display_manager.register("settings"        , self.settings_controller)


    # ============================== Write to file ============================== #
    def write_to_code(self):
        if not self.is_data_valid():
            return

        if not self.path_manager.create_project(self.root):
            return

        # self.write_phase_imports()
        WriteDataManager.write_phase_imports(self.path_manager.path_init_tk1_dst, self.image_controller.get_all_images())
        self.settings_controller.write_settings_to_project(self.path_manager.path_init_dst)
        self.move_controller.write_moves_to_project(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst)
        self.matrix_controller.write_matrix_to_file(self.path_manager.path_init_tk1_dst)
        self.sk_controller.write_to_file(self.path_manager.path_init_dst)
        # self.io_controller.write_io24_to_project(self.path_manager.path_init_dst)
        self.schedule_controller.write_to_file(self.path_manager.path_init_tk1_dst)
        self.image_controller.write_to_file(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst, self.path_manager.path_phase_folder_dst)
        self.phue_controller.write_to_file(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst, self.path_manager.path_phue_folder_dst)
        self.parameters_ta_controller.write_to_file(self.path_manager.path_parameters_ta_dst, self.path_manager.path_init_tk1_dst, self.image_controller.fetch_images_by_sp())
        self.detector_controller.write_to_file(self.path_manager.path_init_tk1_dst, self.path_manager.path_tk1_dst)

    def is_data_valid(self):
        if not self.matrix_controller.is_matrix_valid():
            self.show_view("matrix")
            return False

        if not self.phue_controller.is_names_valid():
            self.show_view("phue")
            return False

        if not self.image_controller.is_sp_valid():
            self.show_view("image")
            return False

        if not self.detector_controller.is_data_valid():
            self.show_view("detector")
            return False
        return True


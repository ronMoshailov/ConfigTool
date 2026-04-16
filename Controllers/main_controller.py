from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QMessageBox, QMainWindow

from Config.exceptions import InvalidMoveName, DuplicateMoveError
from Config.style import main_window_style
from Config.variables import Var

from Managers.builder import SetupBuilder
from Managers.display_manager import DisplayManager
from Managers.write_data_manager import WriteDataManager
from Managers.path_manager import PathManager


class MainController:
    def __init__(self):
        # =============== Build =============== #
        self.path_manager       = PathManager()
        self.display_manager    = DisplayManager()
        self.models             = SetupBuilder.build_models()
        self.views              = SetupBuilder.build_views()
        self.controllers        = SetupBuilder.build_controllers(self.models, self.views)

        # =============== Set Controllers Methods =============== #
        SetupBuilder.connect_controllers(self.controllers)
        self.controllers["move"].view.rename_move_method        = self.rename_move
        self.controllers["move"].global_remove_move             = self.global_remove_move
        self.views["navigator"].write_to_code_method            = self.write_to_code
        self.views["navigator"].show_view_method                = self.show_view
        self.views["navigator"].start_new_project               = self.start_new_project

        # =============== Root Layout =============== #
        root_layout = QHBoxLayout()
        for view in self.views.values():
            root_layout.addWidget(view)
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
                Var.authority = self.views["navigator"].authority_combo.currentText()
                self.views["navigator"].authority_combo.setDisabled(True)
                self.display_manager.show("settings")
            return

        # Check if the project initialized
        if self.path_manager.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "פרויקט לא מאותחל")
            return

        # Choose which view to show
        if act == "matrix":
            self.display_manager.show("matrix", self.models["move"].all_moves)
        elif act == "image":
            self.display_manager.show("image", self.controllers["move"].get_all_moves_names())
        elif act == "phue":
            self.display_manager.show("phue", self.models["image"].all_images, self.controllers["move"].get_all_moves_names())
        elif act == "parameters_ta":
            self.display_manager.show("parameters_ta", self.models["image"].all_images)
        elif act == "sk":
            self.display_manager.show("sk", self.models["move"].all_moves)
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
        for controller in self.controllers.values():
            controller.reset()

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
        self.display_manager.register("move"            , self.controllers["move"])
        self.display_manager.register("matrix"          , self.controllers["matrix"])
        self.display_manager.register("detector"        , self.controllers["detector"])
        self.display_manager.register("sk"              , self.controllers["sk"])
        self.display_manager.register("io24"            , self.controllers["io24"])
        self.display_manager.register("io64"            , self.controllers["io64"])
        self.display_manager.register("schedule"        , self.controllers["schedule"])
        self.display_manager.register("image"           , self.controllers["image"])
        self.display_manager.register("phue"            , self.controllers["phue"])
        self.display_manager.register("parameters_ta"   , self.controllers["parameters_ta"])
        self.display_manager.register("settings"        , self.controllers["settings"])

    def start_new_project(self):
        reply = QMessageBox.question(
            self.main_root,
            "אישור",
            "האם אתה בטוח?",
            QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No,
            QMessageBox.StandardButton.No           # default
        )

        if reply == QMessageBox.StandardButton.No:
            return

        self.path_manager.path_project          = True
        self.schedule_view.is_copy_sunday       = True
        self.navigator_view.authority_combo.setDisabled(True)
        self.reset_models()
        self.show_view("settings")
        QMessageBox.information(self.main_root,"פרויקט חדש","הופעל פרויקט חדש")
        pass

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


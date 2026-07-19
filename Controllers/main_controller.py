from PyQt6.QtWidgets import QMessageBox, QMainWindow

from Utilities.exceptions import InvalidMoveName, DuplicateMoveError
from Utilities.style import main_window_style
from Utilities.variables import Var
from Utilities.Enum.Authority import Authority

from Managers.builder import SetupBuilder
from Managers.display_manager import DisplayManager
from Managers.write_data_manager import WriteDataManager
from Managers.path_manager import PathManager


class MainController:
    def __init__(self):
        # =============== Build =============== #
        self.models             = SetupBuilder.build_models()                               # create all models
        self.views              = SetupBuilder.build_views()                                # create all views
        self.controllers        = SetupBuilder.build_controllers(self.models, self.views)   # create all controllers

        self.path_manager       = PathManager()                                             # create path manager
        self.display_manager    = DisplayManager(self.controllers)                          # create display manager

        self._set_all_methods()                                                              # set all methods

        # =============== Root Widget =============== #
        self.root = QMainWindow()
        self.root.setWindowTitle("Config Tool")
        self.root.setCentralWidget(self.views["main"])
        self.root.setStyleSheet(main_window_style)
        self.root.show()
        self.root.showMaximized()  # show in full-screen

    def show_view(self, act):
        # Initialize app
        if act == "init":
            if self._initialize_app():
                Var.AUTHORITY = self.views["navigator"].authority_combo.currentData()
                self.views["navigator"].authority_combo.setDisabled(True)
                self.display_manager.show("settings")
            return

        # Check if the project initialized
        if self.path_manager.path_project is None:
            self.views["main"].show_error("פרויקט לא מאותחל")
            return

        # Choose which view to show
        if act == "matrix":
            self.display_manager.show(act, self.models["move"].all_moves)
        elif act == "image":
            self.display_manager.show(act, self.controllers["move"].get_all_moves_names())
        elif act == "phue":
            self.display_manager.show(act, self.models["image"].all_images, self.controllers["move"].get_all_moves_names())
        elif act == "parameters_ta":
            self.display_manager.show(act, self.models["image"].all_images)
        elif act == "sk":
            self.display_manager.show(act, self.models["move"].all_moves)
        else:
            self.display_manager.show(act)

    # ============================== CRUD ============================== #
    def rename_move(self, old_name, new_name):
        """
        This method rename the move's name from all the relevant controllers
        :param old_name: The old name of the move
        :param new_name: The new name of the move
        :return: None
        """
        try:
            self.controllers["move"].rename_move(old_name, new_name)  # must be first
            self.controllers["matrix"].rename_move(old_name, new_name)
            self.controllers["sk"].rename_move(old_name, new_name)
            self.controllers["detector"].rename_move(old_name, new_name)
            self.controllers["image"].rename_move(old_name, new_name)
            self.controllers["phue"].rename_move(old_name, new_name)
        except (InvalidMoveName, DuplicateMoveError):
            return

    def global_remove_move(self, move_name):
        """
        This method removes the move from all the relevant controllers
        :param move_name: The name of the move
        :return: None
        """
        # self.detector_model.remove_move(move_name)
        self.controllers["image"].remove_move(move_name)
        self.controllers["phue"].remove_move(move_name)
        self.controllers["matrix"].remove_move(move_name)
        self.controllers["sk"].remove_move(move_name)
        self.controllers["detector"].remove_move(move_name)

    # ============================== Logic ============================== #
    def start_new_project(self):
        """
        This method starts a new empty project
        :return: None
        """
        reply = QMessageBox.question(self.views["main"], "אישור", "האם אתה בטוח?",
            QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No,
            QMessageBox.StandardButton.No           # default
        )

        if reply == QMessageBox.StandardButton.No:
            return

        self.path_manager.path_project              = True
        # self.views["schedule"].is_copy_sunday       = True
        # self.views["navigator"].authority_combo.setDisabled(True)
        self._reset_models()
        self.show_view("settings")
        QMessageBox.information(self.views["main"],"פרויקט חדש","הופעל פרויקט חדש")

    def close_project(self):
        """
        This method close the application (not exit)
        :return:
        """
        reply = QMessageBox.question(self.views["main"], "אישור", "האם אתה בטוח?",
            QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No,
            QMessageBox.StandardButton.No           # default
        )

        if reply == QMessageBox.StandardButton.No:
            return

        self.show_view("")
        self._reset_models()
        self.path_manager.reset()
        self.path_manager.path_project          = None
        # self.views["schedule"].is_copy_sunday       = True
        self.views["navigator"].authority_combo.setDisabled(False)

        QMessageBox.information(self.views["main"],"פרויקט חדש","הפרויקט נסגר")
        pass

    # ============================== Write to file ============================== #
    def write_to_code(self):
        """
        This method writes all the data from the controllers to new project
        :return: None
        """
        if not self._is_app_valid():
            return

        if not self.path_manager.create_project(self.root):
            return

        # if maatz falcon


        # self.write_phase_imports()
        # WriteDataManager.write_phase_imports(self.path_manager.path_init_tk1_dst, self.controllers["image"].get_all_images())

        self.controllers["settings"].write_settings_to_project(self.path_manager.path_init_dst)
        # self.controllers["move"].write_moves_to_project(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst)
        # self.controllers["matrix"].write_matrix_to_file(self.path_manager.path_init_tk1_dst)
        # self.controllers["sk"].write_to_file(self.path_manager.path_init_dst)
        # self.controllers["schedule"].write_to_file(self.path_manager.path_init_tk1_dst)
        # self.controllers["image"].write_to_file(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst, self.path_manager.path_phase_folder_dst)
        # self.controllers["phue"].write_to_file(self.path_manager.path_tk1_dst, self.path_manager.path_init_tk1_dst, self.path_manager.path_phue_folder_dst)
        # self.controllers["detector"].write_to_file(self.path_manager.path_init_tk1_dst, self.path_manager.path_tk1_dst)

        # WriteDataManager.write_json(self.path_manager.path_json_dst)
        #
        # if Var.AUTHORITY is Authority.TEL_AVIV:
        #     self.controllers["parameters_ta"].write_to_file(self.path_manager.path_parameters_ta_dst, self.path_manager.path_init_tk1_dst, self.controllers["image"].fetch_images_by_sp())
        #     WriteDataManager.write_ta_docs(self.path_manager)

    # ============================== Inner methods ============================== #
    def _is_app_valid(self):
        """
        This method checks if the data in all the application is valid.
        :return: True if the data is valid, False otherwise.
        """
        # if not self.controllers["matrix"].is_matrix_valid():
        #     self.show_view("matrix")
        #     return False
        #
        # if not self.controllers["phue"].is_names_valid():
        #     self.show_view("phue")
        #     return False
        #
        # if not self.controllers["image"].is_sp_valid():
        #     self.show_view("image")
        #     return False
        #
        # if not self.controllers["detector"].is_detectors_valid():
        #     self.show_view("detector")
        #     return False
        return True

    def _reset_models(self):
        """
        This method resets all the models in the app
        :return: None
        """
        for controller in self.controllers.values():
            controller.reset()

    def _initialize_app(self):
        # Set Project Path
        if not self.path_manager.set_folder_path():
            self.views["main"].show_error("לא נבחרה תיקייה")
            return False

        # Reset
        self._reset_models()

        # Initialize Controllers
        # self.path_manager.set_files_path(self.models["phue"].phue_paths)
        self.controllers["settings"].init_model(self.path_manager.path_init_src)
        # self.controllers["move"].init_model(self.path_manager.path_init_tk1_src)
        # self.controllers["matrix"].init_model(self.path_manager.path_init_tk1_src)
        # self.controllers["sk"].init_model(self.path_manager.path_init_src)
        # self.controllers["io24"].init_model(self.path_manager.path_init_src)
        # self.controllers["io64"].init_model(self.path_manager.path_init_src)
        # self.controllers["detector"].init_model(self.path_manager.path_init_tk1_src)
        # self.controllers["image"].init_model(self.path_manager.path_init_tk1_src, self.controllers["move"].get_all_moves_names())
        # self.controllers["phue"].init_model(self.models["phue"].phue_paths, self.path_manager.path_init_tk1_src)
        # self.controllers["schedule"].init_model(self.path_manager.path_init_tk1_src)
        # self.controllers["parameters_ta"].init_model(self.path_manager.path_parameters_ta_src, len(self.models["image"].all_images))

        # self.path_manager.load_project_number_and_name()
        return True

    def _set_all_methods(self):
        """
        This method set all the methods between the controllers and main controller
        :return: None
        """
        SetupBuilder.connect_controllers_methods(self.controllers)

        # self.controllers["move"].view.rename_move_method    = self.rename_move
        # self.controllers["move"].global_remove_move         = self.global_remove_move
        self.views["navigator"].write_to_code_method        = self.write_to_code
        self.views["navigator"].show_view_method            = self.show_view
        self.views["navigator"].start_new_project_method    = self.start_new_project
        self.views["navigator"].close_project_method        = self.close_project

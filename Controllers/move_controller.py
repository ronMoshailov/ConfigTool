import re

from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class MoveController:
    def __init__(self, view, model):
        # Fields
        self.view   = view
        self.model  = model

        # Set View Methods
        self.view.add_move_method               = self.add_new_move
        self.view.remove_move_method            = self.remove_move
        self.view.update_type_method            = self.update_type
        self.view.update_min_green_method       = self.update_min_green
        self.view.update_main_method            = self.update_main

        # Set Main Controller Methods
        self.remove_move_from_matrix_method     = None
        self.global_remove_move                 = None

    def init_model(self, path):
        all_moves = LoadDataManager.load_moves_data(path)
        for variable_name, move_type, is_main, min_green in all_moves:
            self.model.add_move(variable_name, move_type, is_main, int(min_green))

    def show_view(self):
        self.view.show_view(self.model.all_moves, self.model.get_all_types())

    def hide_view(self):
        self.view.hide_view()

    # ============================== CRUD ============================== #
    def add_new_move(self):
        """
        This method add new move to the model
        """
        self.model.add_move("k0", "Traffic", False, 0)
        self.show_view()

    def get_move_type(self, move_name):
        # Used by Matrix Controller
        """
        This method return the type of the move
        """
        return self.model.get_move_type(move_name)

    def get_all_moves_names(self):
        # Used also in Main Controller
        """
        This method return all the moves names
        """
        return self.model.get_all_moves_names()

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move
        """
        if old_name == new_name:
            return None

        # Check if start with k/p/B
        if not (new_name.startswith("k") or new_name.startswith("p") or new_name.startswith("B")):
            error_str = "מופע חייב להתחיל עם k/p/B"
            self.view.show_error(error_str)
            self.show_view()
            raise Exception(error_str)

        # Check if new name contain just words and numbers
        if not re.fullmatch(r"[A-Za-z0-9]+", new_name):
            error_str = "השם יכול להכיל רק אותיות, מספרים"
            self.view.show_error(error_str)
            self.show_view()
            raise Exception(error_str)

        # Update model
        try:
            self.model.update_name(old_name, new_name)
        except Exception as e:
            self.show_view()
            raise e

        # If it's blinker fix the matrix and min green time
        if new_name.startswith("B"):
            self.remove_move_from_matrix_method(new_name)
            self.model.set_min_green(new_name, 0)

        # Refresh view
        self.show_view()
        return None

    def update_type(self, move_name,  new_type):
        """
        This method set a type to move
        """
        self.model.set_type(move_name,  new_type)

    def update_main(self, move_name, new_state):
        """
        This method set the new state to the move
        """
        self.model.set_main(move_name, new_state)

    def update_min_green(self, move, time):
        """
        This method set the new min green time to the move
        """
        if not time.isdigit():
            self.view.show_error("ערך לא תקין")
            self.show_view()
        else:
            self.model.set_min_green(move, int(time))

    def remove_move(self, move_name):
        """
        This method removes the move from all the models
        """
        self.model.remove_move(move_name)
        self.global_remove_move(move_name)

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_move_model()

    # ============================== Write To File ============================== #
    def write_moves_to_project(self, path_tk1, path_init_tk1):
        # update tk1.java file
        code = WriteDataManager.create_moves_tk1_code(path_tk1, self.model.get_all_moves_names())
        WriteDataManager.write_code(path_tk1, code)

        # update init_tk1.java file
        code = WriteDataManager.create_moves_init_tk1_code(path_init_tk1, self.model.all_moves)
        WriteDataManager.write_code(path_init_tk1, code)



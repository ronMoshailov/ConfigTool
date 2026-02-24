import re

from PyQt6.QtWidgets import QMessageBox
from pathlib import Path

import Config.constants
from Managers.load_data_manager import LoadDataManager


class PhueController:

    def __init__(self, view, model):
        # Fields
        self.view               = view
        self.model              = model

        # Data
        self.all_images         = None
        self.all_moves_names    = None

        # Set View Methods
        self.view.add_phue_method                   = self.add_phue
        self.view.add_transition_method             = self.add_transition
        self.view.remove_phue_method                = self.remove_phue
        self.view.remove_transition_method          = self.remove_transition
        self.view.update_transition_move_method     = self.update_transition_move
        self.view.update_color_method               = self.update_color
        self.view.update_duration_method            = self.update_duration
        self.view.update_phue_len_method            = self.update_phue_len



    def init_model(self, phue_paths, path_init_tk1): # self.phue_model.phue_paths, self.path_init_tk1

        data = LoadDataManager.load_phue_paths(phue_paths)
        for img_out, img_in, transitions_data in data:
            transitions = []
            for move, state, time in transitions_data:
                transition = self.model.create_new_transition(move, state, time)
                transitions.append(transition)
            self.model.create_new_phue(img_out, img_in, 0, transitions)

        data = LoadDataManager.load_phue_length(path_init_tk1)
        for img_out, img_in, length in data:
            self.model.set_phue_len(img_out, img_in, length)

    def show_view(self, all_images, all_moves_names):
        self.all_images = all_images
        self.all_moves_names = all_moves_names
        self.view.show_view(self.model.all_phue, self.all_images, self.all_moves_names)

    # ============================== CRUD ============================== #
    def add_phue(self, img_out, img_in):
        """
        This method add new phue to the model
        """
        # check if image out or image out is empty
        if img_out == "-" or img_in == "-":
            QMessageBox.critical(self.view, "שגיאה", f"מעבר לא יכול להיות ריק")
            return

        # check if image out is the same as image out
        if img_out == img_in:
            QMessageBox.critical(self.view, "שגיאה", f"מעבר לא תקין [{img_out} -> {img_in}]")
            return

        # add the new phue
        if not self.model.create_new_phue(img_out, img_in, 0, []):
            QMessageBox.critical(self.view, "שגיאה", "המעבר כבר קיים במערכת")
            return

        # refresh the view
        self.show_view(self.all_images, self.all_moves_names)

    def add_transition(self, img_out, img_in):
        """
        This method add new transition to the phue
        """
        self.model.add_transition(img_out, img_in)
        self.show_view(self.all_images, self.all_moves_names)

    def remove_phue(self, img_out, img_in):
        """
        This method remove phue from the model
        """
        self.model.remove_phue(img_out, img_in)
        self.show_view(self.all_images, self.all_moves_names)

    def remove_transition(self, img_out, img_in, move_name):
        """
        This method removes transition from a phue
        """
        self.model.remove_transition(img_out, img_in, move_name)
        self.show_view(self.all_images, self.all_moves_names)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method remove all transitions that has the same 'move_name'
        """
        self.model.rename_move(move_name)

    def update_transition_move(self, image_out, image_in, old_name, new_name):
        """
        This method update the transition move
        """
        try:
            self.model.set_transition_move(image_out, image_in, old_name, new_name)
            self.show_view(self.all_images, self.all_moves_names)
        except Exception as e:
            QMessageBox.critical(self.view, "שגיאה", f"{e}")
            self.show_view(self.all_images, self.all_moves_names)

    def update_duration(self, img_out, img_in, move_name, duration):
        """
        This method set a new value for the duration
        """
        if '-' in duration:
            first, second = duration.split("-")
            first = int(first)
            second = int(second)
            value = first - second
        else:
            value = int(duration)

        self.model.set_duration(img_out, img_in, move_name, value)
        self.show_view(self.all_images, self.all_moves_names)

    def update_color(self, img_out, img_in, move_name):
        """
        This method swap the value for the color
        """
        self.model.swap_move_color(img_out, img_in, move_name)
        self.show_view(self.all_images, self.all_moves_names)

    def update_phue_len(self, img_out, img_in, new_len):
        """
        This method set a new value for the phue length
        """
        self.model.set_phue_len(img_out, img_in, new_len)

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_phue_model()

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move in all the cells
        """
        self.model.rename_move(old_name, new_name)

    def is_names_valid(self):
        """
        This method check if the names contain "-"
        """
        if not self.model.is_names_valid():
            QMessageBox.critical(self.view, "הודעה", "המעברים כוללים שם מופע לא תקין")
            return False
        return True

    # ============================== Write To File ============================== #
    def write_to_file(self, init_tk1_dst, phue_folder_dst):
        # create files
        for phue in self.model.all_phue:
            self.create_file(phue.image_out, phue.image_in, phue.transitions, phue_folder_dst)

        with open(init_tk1_dst, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        code = []
        for line in lines:
            if "write phues here" in line:
                self.get_code(code)
                continue
            code.append(line)

        with open(init_tk1_dst, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def get_code(self, code):
        """
        This method create a code of all phues for InitTk1
        """
        # code = []
        line = ""

        for phue in self.model.all_phue:
            line += f"\t\ttk.Phue{phue.image_out}_{phue.image_in}"          # add code
            line += " " * (16 - len(line))                                  # add spaces
            line += f"= new Phue{phue.image_out}_{phue.image_in}"           # add code
            line += " " * (36 - len(line))                                  # add spaces
            line += f"(tk, \"Phue{phue.image_out}_{phue.image_in}\""        # add code
            line += " " * (53 - len(line))                                  # add spaces
            if int(phue.length) >= 10:
                line += f",  {phue.length}"                                 # add code
            else:
                line += f",   {phue.length}"                                # add code
            line += f" , tk.Ph{phue.image_out}"                             # add code
            line += " " * (71 - len(line))                                  # add spaces
            line += f", tk.Ph{phue.image_in}"                               # add code
            line += " " * (84 - len(line))                                  # add spaces
            line += ");\n"

            code.append(line)
            line = ""




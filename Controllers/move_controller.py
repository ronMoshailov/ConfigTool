import re

from PyQt6.QtWidgets import QMessageBox

from Config.patterns import move_pattern


class MoveController:
    """
    This class represents controller of a traffic signal move.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

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
        # path: initTk1.java

        pattern = move_pattern

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if line.startswith("//") or not line.startswith("tk."):
                    continue
                match = pattern.match(line)
                if match:
                    phase, move_type, min_green, is_main = match.groups()
                    is_main = True if is_main == "true" else False

                    # Add move to the model
                    self.model.add_move(phase, move_type, is_main, int(min_green))

    def show_view(self):
        self.view.show_view(self.model.all_moves, self.model.get_all_types())

    # ============================== CRUD ============================== #
    def add_new_move(self):
        self.model.add_move("k0", "Traffic", False, 0)
        self.show_view()

    def get_move_type(self, move_name): # Used by Matrix Controller for fixing matrix time
        return self.model.get_move_type(move_name)

    def get_all_moves_names(self):
        return self.model.get_all_moves_names()

    def rename_move(self, old_name, new_name):
        if old_name == new_name:
            return
        try:
            if not (new_name.startswith("k") or new_name.startswith("p") or new_name.startswith("B")):
                raise Exception("מופע חייב להתחיל עם k/p/B")
            if not re.fullmatch(r"[A-Za-z0-9]+", new_name):
                raise ValueError("השם יכול להכיל רק אותיות, מספרים")

            self.model.update_name(old_name, new_name)
            if new_name.startswith("B"):
                self.remove_move_from_matrix_method(new_name)
                self.update_min_green(new_name, 0)
            self.show_view()
        except Exception as e:
            QMessageBox.critical(self.view, "שגיאה", str(e))
            self.show_view()

    def update_type(self, move_name,  new_type):
        self.model.update_type(move_name,  new_type)

    def update_main(self, move, state):
        self.model.update_main(move, state)
        move.is_main = True if state == 2 else False

    def update_min_green(self, move, time):
        self.model.update_min_green(move, time)

    def remove_move(self, table, btn):
        row_count = table.rowCount()
        for row in range(row_count):
            item = table.cellWidget(row, 0)
            if item is btn:
                move_name = table.cellWidget(row, 1).text()
                self.model.remove_move(move_name)
                self.global_remove_move(move_name)
                table.removeRow(row)
                break

    # ============================== Logic ============================== #
    def reset(self):
        self.model.reset()

    # ============================== Write To File ============================== #
    def write_to_file(self, path_tk, path_init_tk1):
        # data
        code = []

        # update tk1.java file
        with open(path_tk, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write moves here" in line:
                self.add_tk1_lines(code)
                continue
            code.append(line)

        with open(path_tk, 'w', encoding='utf-8') as f:
            f.writelines(code)

        # data
        code = []

        # update initTk1.java file
        with open(path_init_tk1, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write moves here" in line:
                self.add_initTk1_lines(code)
                continue
            code.append(line)

        with open(path_init_tk1, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def add_tk1_lines(self, new_lines):
        cars_line = "\tpublic Move "
        pedestrians_line = "\tpublic Move "
        blinkers_line = "\tpublic Move "

        moves_dictionary = {"k": [], "p": [], "B": []}
        for name in self.model.get_all_moves_names():
            moves_dictionary[name[0]].append(name)

        for item in moves_dictionary["k"]:
            cars_line += f"{item}, "
        cars_line = cars_line[:-2] + ";  // traffic\n"

        for item in moves_dictionary["p"]:
            pedestrians_line += f"{item}, "
        pedestrians_line = pedestrians_line[:-2] + "; // pedestrians\n"

        for item in moves_dictionary["B"]:
            blinkers_line += f"{item}, "
        blinkers_line = blinkers_line[:-2] + ";	// blinkers\n"

        new_lines.append(cars_line)
        new_lines.append(pedestrians_line)
        new_lines.append(blinkers_line)

    def add_initTk1_lines(self, new_lines):
        car_lines = []
        pedestrians_lines = []
        blinkers_lines = []

        moves_dictionary = {"k": [], "p": [], "B": []}
        for name in self.model.get_all_moves_names():
            moves_dictionary[name[0]].append(name)

        for move in self.model.all_moves:
            # calc min green
            min_green = move.min_green
            if move.type == "Traffic":
                if not move.is_main:
                    if min_green > 5:
                        min_green = 5
            elif move.type == "Traffic_Flashing":
                min_green -= 3
                if not move.is_main:
                    if min_green > 5:
                        min_green = 5
            elif move.type == "Pedestrian":
                if min_green < 6:
                    min_green = 6
            else:
                min_green = 0

            line = ""
            line += f"\t\ttk.{move.name}"
            line += " " * (12 - (len(line)))
            line += f"=  new Move(   tk    , \"_{move.name}\""
            line += " " * (42 - (len(line)))
            line += f", MoveType.{move.type}"
            line += " " * (72 - (len(line)))
            line += ",     "
            if min_green >= 10:
                line += f"{min_green}"
            else:
                line += f" {min_green}"
            line += " ,   0 , "
            if move.is_main:
                line += "true "
            else:
                line += "false"
            line += ");\n"

            if move.name.startswith("k"):
                car_lines.append(line)
            elif move.name.startswith("p"):
                pedestrians_lines.append(line)
            else:
                blinkers_lines.append(line)

        new_lines.extend(car_lines)
        new_lines.append("\n")
        new_lines.extend(pedestrians_lines)
        new_lines.append("\n")
        new_lines.extend(blinkers_lines)


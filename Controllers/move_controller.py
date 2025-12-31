from PyQt6.QtWidgets import QMessageBox

from Config.patterns import move_pattern


class MoveController:
    """
    This class represents controller of a traffic signal move.
    """
    def __init__(self, view, model):
        """
        Constructor.

        :param view: Moves view.
        :param model: Move model.
        """
        self.view = view
        self.model = model

        self.view.add_move_method = self.add_move
        self.view.remove_move_method = self.remove_move

    def show_view(self):
        """
        This method show the view.
        :return: None
        """
        self.view.show_view(self.model.all_moves)

    def add_move(self, move_name, move_type, is_main, min_green):
        """
        This method add a move.

        :param move_name: Move name without [k, p, B].
        :param move_type: Move type.
        :param is_main: Move is main or not.
        :param min_green: Move minimum green time.
        :return:
        """
        # check if the name is empty
        if move_name.strip() == '':
            QMessageBox.critical(self.view, "שגיאה", f"שם ריק")
            return

        # check if move name contain numbers and digits
        if any(c.isalpha() for c in move_name) and any(c.isdigit() for c in move_name):
            QMessageBox.critical(self.view, "שגיאה", f"שם המופע לא תקין [{move_name}]")
            return

        # fix 'name' depend on 'type'
        if move_type == "Traffic" or move_type == "Traffic_Flashing":
            move_name = "k" + move_name
        elif move_type == "Pedestrian":
            move_name = "p" + move_name
        elif move_type == "Blinker" or move_type == "Blinker_Conditional":
            move_name = "B" + move_name

        # add move (if failed it's because the move already exist)
        if not self.model.add_move(move_name, move_type, is_main, min_green):
            QMessageBox.critical(self.view, "שגיאה", "מופע כבר קיים במערכת")
            return

        # refresh the view
        self.show_view()

    def remove_move(self, move_name):
        """
        This method remove a move.

        :param move_name: Move name.
        :return: None
        """
        if self.model.remove_move(move_name):
            self.show_view()
            return
        QMessageBox.critical(self.view, "שגיאה", "שגיאה שלא אמורה להתרחש")

    def init_model(self, path):
        """
        This method set from path the moves in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        pattern = move_pattern

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if line.startswith("//") or not line.startswith("tk."):
                    continue
                match = pattern.match(line)
                if match:
                    phase, move_type, min_green, is_main = match.groups()
                    self.model.add_move(phase, move_type, True if is_main == "true" else False, int(min_green))

    def write_to_file(self, path_tk, path_initTk1):
        # data
        is_found = False
        new_lines = []

        # update tk1.java file
        with open(path_tk, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if line.strip().startswith("public Move"):
                if not is_found:
                    is_found = True
                    self.add_tk1_lines(new_lines)
                continue

            new_lines.append(line)

        with open(path_tk, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)

        # data
        is_found = False
        new_lines = []
        backslash_N = 0

        # update initTk1.java file
        with open(path_initTk1, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "new Move" in line.strip():
                if not is_found:
                    is_found = True
                    self.add_initTk1_lines(new_lines)
                continue

            if is_found and line == "\n" and backslash_N != 2:
                backslash_N += 1
                continue
            new_lines.append(line)

        with open(path_initTk1, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)

    def add_tk1_lines(self, new_lines):
        cars = []
        pedestrians = []
        blinkers = []

        for move in self.model.all_moves:
            if move.name.startswith("k"):
                cars.append(move.name)
            elif move.name.startswith("p"):
                pedestrians.append(move.name)
            else:
                blinkers.append(move.name)

        cars_line = "\tpublic Move "
        pedestrians_line = "\tpublic Move "
        blinkers_line = "\tpublic Move "

        for item in cars:
            cars_line += f"{item}, "
        cars_line = cars_line[:-2] + ";  // traffic\n"

        for item in pedestrians:
            pedestrians_line += f"{item}, "
        pedestrians_line = pedestrians_line[:-2] + "; // pedestrians\n"

        for item in blinkers:
            blinkers_line += f"{item}, "
        blinkers_line = blinkers_line[:-2] + ";	// blinkers\n"

        new_lines.append(cars_line)
        new_lines.append(pedestrians_line)
        new_lines.append(blinkers_line)

    def add_initTk1_lines(self, new_lines):
        start = 'k'
        for move in self.model.all_moves:
            spaces_name = " " * max(1, 6 - len(move.name))
            spaces_name2 = " " * max(1, 3 - len(move.name[1:]))
            spaces_name5 = " " * max(1, 4 - len(move.name[1:]))
            spaces_name3 = " " * max(0,19 - len(move.type))
            spaces_name4 = " " * max(1,7 - len(str(move.min_green)))
            if move.name == "Bd":
                print()

            line = "\t\t"
            if not move.name.startswith(start):
                line = "\n\t\t"
                start = move.name[0]
            line += f"tk.{move.name}{spaces_name} "
            if move.name.startswith("B"):
                line += f"=  new Move(   tk    , \"_{move.name}\"{spaces_name2}, "
            else:
                line += f"=  new Move(   tk    , \"_{move.name[1:]}\"{spaces_name5}, "
            line += f"MoveType.{move.type}{spaces_name3},"
            line += f"{spaces_name4}{move.min_green} ,   0 , {str(move.is_main).lower()}"
            if move.is_main:
                line += " );\n"
            else:
                line += ");\n"
            new_lines.append(line)
            #
            #
            #     cars_line += f"{item}, "
            # cars_line = cars_line[:-2] + ";  // traffic\n"
            #
            # for item in pedestrians:
            #     pedestrians_line += f"{item}, "
            # pedestrians_line = pedestrians_line[:-2] + "; // pedestrians\n"
            #
            # for item in blinkers:
            #     blinkers_line += f"{item}, "
            # blinkers_line = blinkers_line[:-2] + ";	// blinkers\n"
            #
            # new_lines.append(cars_line)
            # new_lines.append(pedestrians_line)
            # new_lines.append(blinkers_line)


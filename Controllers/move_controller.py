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
        self.view.add_move_method = self.add_move
        self.view.remove_move_method = self.remove_move
        self.view.update_type_method = self.update_type
        self.view.update_min_green_method = self.update_min_green
        self.view.update_main_method = self.update_main
        self.view.get_min_green_tooltip_method = self.get_min_green

        #
        self.global_remove_move = None

    def show_view(self):
        """
        This method show the view.
        :return: None
        """
        self.view.show_view(self.model.all_moves, self.model.get_all_types())

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

    ####################################################################################
    #                                     CRUD                                         #
    ####################################################################################
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
            if move_name[0].isalpha():
                QMessageBox.critical(self.view, "שגיאה", "מופע תנועה לא יכול להיות עם אות בשם")
                return
            move_name = "k" + move_name
        elif move_type == "Pedestrian":
            if move_name[0].isdigit():
                QMessageBox.critical(self.view, "שגיאה", "מופע הולך רגל לא יכול להיות עם מספר בשם")
                return
            move_name = "p" + move_name
        elif move_type == "Blinker" or move_type == "Blinker_Conditional":
            if move_name[0].isdigit():
                QMessageBox.critical(self.view, "שגיאה", "בלינקר לא יכול להיות עם מספר בשם")
                return
            move_name = "B" + move_name

        # add move (if failed it's because the move already exist)
        if not self.model.add_move(move_name, move_type, is_main, min_green):
            QMessageBox.critical(self.view, "שגיאה", "מופע כבר קיים במערכת")
            return

        # refresh the view
        self.show_view()

    def get_min_green(self, move):
        return str(move.min_green)

    def get_all_moves_names(self):
        return self.model.get_all_moves_names()

    def update_names(self, old_name, new_name):
        self.model.update_name(old_name, new_name)

    def update_type(self, text,  move):
        move.type=text

    def update_min_green(self, move, time):
        move.min_green = int(time)

    def update_main(self, move, state):
        move.is_main = True if state == 2 else False

    def remove_move(self, table, btn):
        """
        This method remove a move.

        :param move_name: Move name.
        :return: None
        """
        row_count = table.rowCount()
        for row in range(row_count):
            item = table.cellWidget(row, 0)
            if item is btn:
                move_name = table.cellWidget(row, 1).text()
                self.model.remove_move(move_name)
                self.global_remove_move(move_name)
                table.removeRow(row)
                break

    ####################################################################################
    #                               Logic                                              #
    ####################################################################################

    def reset(self):
        self.model.reset()


    ####################################################################################
    #                           Write to file                                          #
    ####################################################################################
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
            line = ""
            line += f"\t\ttk.{move.name}"
            line += " " * (12 - (len(line)))
            line += f"=  new Move(   tk    , \"_{move.name}\""
            line += " " * (42 - (len(line)))
            line += f", MoveType.{move.type}"
            line += " " * (72 - (len(line)))
            line += ",     "
            if move.min_green >= 10:
                line += f"{move.min_green}"
            else:
                line += f" {move.min_green}"
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


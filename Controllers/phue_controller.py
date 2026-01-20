import re

from PyQt6.QtWidgets import QMessageBox
from pathlib import Path

import Config.constants


class PhueController:

    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.add_phue_method = self.add_phue
        self.view.remove_phue_method = self.remove_phue
        self.view.update_phue_method = self.update_phue
        self.view.update_transition_move_method = self.update_transition_move
        self.view.update_duration_method = self.update_duration
        self.view.update_color_method = self.update_color
        self.view.update_phue_len_method = self.update_phue_len

        self.all_images = None
        self.all_moves_names = None

    def init_model(self, phue_paths, path_init_tk1): # self.phue_model.phue_paths, self.path_init_tk1

        class_pattern = re.compile(r"public\s+class\s+Phue([A-Za-z0-9]+)_?([A-Za-z0-9]+)")
        sg_pattern = re.compile( r"_tk\.(\w+)\.(TurnOn|TurnOff)\s*\(\s*(\d+)\s*\)")
        phue_len_pattern = re.compile(r'tk\.Phue([A-Za-z0-9]+)_([A-Za-z0-9]+)\s*=.*?\(\s*tk\s*,\s*"[^"]+"\s*,\s*([0-9]+)')

        for path in phue_paths:
            path = Path(path)
            with open(path, "r", encoding="utf-8", errors="ignore") as f:
                content = f.read()

            # find image_out and image_in
            class_match = class_pattern.search(content)
            if not class_match:
                continue
            img_out, img_in = class_match.groups()

            transitions = []
            # new_interstage = InterStage(img_out, img_in, 0)

            # find turn of and turn off moves
            for m in sg_pattern.finditer(content):
                move, state, time = m.groups()
                transition = self.model.new_transition(move, state, time)
                transitions.append(transition)
            self.model.new_phue(img_out, img_in, 0, transitions)

        with open(path_init_tk1, "r", encoding="utf-8") as f:
            content = f.read()

            for match in phue_len_pattern.finditer(content):
                img_out = match.group(1)
                img_in = match.group(2)
                length = match.group(3)
                self.model.update_length(img_out, img_in, int(length))

    def show_view(self, all_images, all_moves_names):
        self.all_images = all_images
        self.all_moves_names = all_moves_names
        self.view.show_view(self.model.all_phue, self.all_images, self.all_moves_names)

    # ============================== CRUD ============================== #
    def add_phue(self, img_out, img_in):
        if img_out == img_in:
            QMessageBox.critical(self.view, "שגיאה", f"מעבר לא תקין [{img_out} -> {img_in}]")
            return
        if not self.model.new_phue(img_out, img_in, 0, []):
            QMessageBox.critical(self.view, "שגיאה", "המעבר כבר קיים במערכת")
            return
        self.show_view(self.all_images, self.all_moves_names)

    def remove_phue(self, img_out, img_in):
        self.model.remove_phue(img_out, img_in)
        self.show_view(self.all_images, self.all_moves_names)

    def update_phue(self, table_wrap_list):
    # check if move exist twice
        for wrap in table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            table = wrap.table

            seen = set()  # מאגר של ערכים שכבר הופיעו
            row_count = table.rowCount()
            for row in range(row_count):
                combo = table.cellWidget(row, 0)  # נניח שזה QComboBox
                value = combo.currentText()  # הערך הנבחר
                if value in seen:
                    QMessageBox.critical(self.view, "שגיאה", f"המופע [{value}] מופיע לפחות פעמיים במעבר [{img_out} → {img_in}]")
                    return
                seen.add(value)

        for wrap in table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            table = wrap.table
            row_count = table.rowCount()
            length = int(wrap.len_textbox.text())

            move_name_list = []
            color_list = []
            time_list = []

            for row in range(row_count):
                # move_name
                combo = table.cellWidget(row, 0)
                move_name = combo.currentText()  # הערך הנבחר
                move_name_list.append(move_name)

                # color
                color_mapping = {"🔴": "ROT", "🟢": "GRUEN"}
                color = color_mapping[table.item(row, 1).text()]
                color_list.append(color)

                time = table.item(row, 2).text()
                time_list.append(time)

            self.model.update_phue(img_out, img_in, length, move_name_list, color_list, time_list)

        # update the DB
        # success, message = self.data_controller.update_inter_stage(self.table_wrap_list)
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    def update_names(self, old_name, new_name):
        self.model.update_names(old_name, new_name)

    def update_phue_len(self, img_out, img_in, new_len):
        self.model.update_phue_len(img_out, img_in, new_len)

    def update_transition_move(self, image_out, image_in, old_name, new_name):
        try:
            self.model.update_move_name(image_out, image_in, old_name, new_name)
            self.show_view(self.all_images, self.all_moves_names)
        except Exception as e:
            QMessageBox.critical(self.view, "שגיאה", f"{e}")
            self.show_view(self.all_images, self.all_moves_names)

    def update_duration(self, img_out, img_in, move_name, duration):
        self.model.update_duration(img_out, img_in, move_name, duration)

    def update_color(self, img_out, img_in, move_name):
        self.model.update_move_color(img_out, img_in, move_name)
        self.show_view(self.all_images, self.all_moves_names)

    # ============================== Logic ============================== #
    def reset(self):
        self.model.reset()

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

        :return: List of code of the phues
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
            if phue.length >= 10:
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

    def create_file(self, image_out, image_in, transitions, phue_folder_dst):
        line = ""
        line += "package phue;\n"
        line += "\n"
        line += "\n"
        line += "import vt.*;\n"
        line += "import special.InterStage;\n"
        line += f"import {Config.constants.PROJECT_NUMBER}.Tk1;"
        line += "\n"
        line += f"public class Phue{image_out}_{image_in} "
        line += "extends InterStage {\n"
        line += "\tprivate static Tk1 _tk;\n"
        line += f"public Phue{image_out}_{image_in}(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) "
        line += "{\n"
        line += "\t\tsuper(tk, name, laenge, quelle, ziel);\n"
        line += "\t\t_tk = tk;\n"
        line += "\t\t}\n"
        line += "\n"
        line += "\tpublic Phase phasenFunktion() {\n"

        for transition in transitions:
            if transition.state == "TurnOff":
                line += f"\t\t_tk.{transition.move_name}.TurnOff("
                if int(transition.duration) >= 10:
                    line += f"{transition.duration});\n"
                else:
                    line += f" {transition.duration});\n"

        line += "\n"

        for transition in transitions:
            if transition.state == "TurnOn":
                line += f"\t\t_tk.{transition.move_name}.TurnOn ("
                if int(transition.duration) >= 10:
                    line += f"{transition.duration});\n"
                else:
                    line += f" {transition.duration});\n"

        line += "\n"
        line += "\t\tif (isTargetStageBuilt()) {\n"
        line += "\t\t\t_tk.lenPhue = this.getPhasenZeit();\n"
        line += "\t\t\tthis.entfernen();\n"
        line += "\t\t}\n"
        line += "\t\treturn KEINE_UMSCHALTUNG;\n"
        line += "\t}\n"
        line += "}\n"
        line += "\n"
        line += "\n"

        # create file
        file_path = phue_folder_dst / f"Phue{image_out}_{image_in}.java"
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(line)

        return line






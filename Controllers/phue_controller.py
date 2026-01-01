import re

from PyQt6.QtWidgets import QWidget, QMessageBox
from pathlib import Path


class PhueController:

    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.add_phue_method = self.add_phue
        self.view.remove_phue_method = self.remove_phue
        self.view.update_phue_method = self.update_phue

        self.all_images = None
        self.all_moves = None

    def init_model(self, path_list, path_len):
        class_pattern = re.compile(r"public\s+class\s+Phue([A-Za-z0-9]+)_([A-Za-z0-9]+)")
        sg_pattern = re.compile(r"_tk\.(\w+)\.setSg\s*\(Zustand\.(ROT|GRUEN)\s*,\s*(\d+)\s*\)")
        phue_len_pattern = re.compile(r'tk\.Phue([A-Za-z0-9]+)_([A-Za-z0-9]+)\s*=.*?\(\s*tk\s*,\s*"[^"]+"\s*,\s*([0-9]+)')

        for path in path_list:
            path = Path(path)
            with open(path, "r", encoding="utf-8") as f:
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

        with open(path_len, "r", encoding="utf-8") as f:
            content = f.read()

            for match in phue_len_pattern.finditer(content):
                img_out = match.group(1)
                img_in = match.group(2)
                length = match.group(3)
                self.model.update_length(img_out, img_in, int(length))

    def add_phue(self, img_out, img_in):
        if img_out == img_in:
            QMessageBox.critical(self.view, "שגיאה", f"מעבר לא תקין [{img_out} -> {img_in}]")
            return
        if not self.model.new_phue(img_out, img_in, 0, []):
            QMessageBox.critical(self.view, "שגיאה", "המעבר כבר קיים במערכת")
            return
        self.show_view(self.all_images, self.all_moves)

    def remove_phue(self, img_out, img_in):
        self.model.remove_phue(img_out, img_in)
        self.show_view(self.all_images, self.all_moves)

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

    def show_view(self, all_images, all_moves):
        self.all_images = all_images
        self.all_moves = all_moves
        self.view.show_view(self.model.all_phue, self.all_images, self.all_moves)

    def get_code(self):
        """
        This method create a code of all phues for InitTk1

        :return: List of code of the phues
        """
        code = []
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

        for c in code:
            print(c)

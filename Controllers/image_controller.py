import re

from PyQt6.QtWidgets import QCheckBox, QMessageBox

from Config.patterns import image_pattern


class ImageController:
    def __init__(self, view, model):
        self.view = view
        self.model = model
        self.all_moves = None

        self.view.add_image_method = self.add_image
        self.view.remove_image_method = self.remove_image
        self.view.update_image_method = self.update_image

    def init_model(self, path, all_moves):
        pattern = image_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                m = pattern.search(line)
                if m:
                    image_name = m.group(1)
                    image_num = m.group(2).strip()
                    image_skeleton = m.group(3).strip()
                    image_sp = m.group(4).strip()
                    is_police = True if m.group(5).strip() == 'true' else False
                    moves_raw = m.group(6)
                    image_moves = re.findall(r'tk\.([A-Za-z0-9_]+)', moves_raw)

                    if image_name == 'A':
                        image_num = 10
                        image_skeleton = re.search(r'\{([^}]*)\}', m.group(3)).group(1).strip()
                        image_sp = 0

                    collection = []

                    for move in all_moves:
                        if move.name in image_moves:
                            collection.append(move)
                    self.model.new_image(image_name, image_num, image_skeleton, image_sp, is_police, collection)
                    # self.image_model.new_image(image_name, image_num, image_skeleton, image_sp, is_police)

    def show_view(self, all_moves):
        self.all_moves = all_moves
        self.view.show_view(self.model.all_images, self.all_moves)

    def add_image(self, name, num, skeleton, sp):
        name = name.capitalize()
        if not self.model.new_image(name, num, skeleton, sp):
            QMessageBox.critical(self.view, "שגיאה", "התמונה כבר קיימת במערכת")
        self.show_view(self.all_moves)

    def remove_image(self, name):
        self.model.remove_image(name)
        self.show_view(self.all_moves)

    def update_image(self, table_dict):
        for image_name, table in table_dict.items():
            skeleton_num = int(table.skeleton_textbox.text())
            image_number = int(table.image_number.text())
            results = []
            for row in range(table.rowCount()):
                container = table.cellWidget(row, 1)
                if not container:
                    continue

                checkbox = container.findChild(QCheckBox)
                if checkbox and checkbox.isChecked():
                    label = table.cellWidget(row, 0)
                    if label:
                        for move in self.all_moves:
                            if move.name == label.text():
                                results.append(move)
                                break
            self.model.update_image(image_name, skeleton_num, image_number, results)
        self.show_view(self.all_moves)
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")


    def write_to_file(self, path_tk, path_init_tk):
        # # create files
        # for image in self.model.all_images:
        #     self.create_file(image.image_name, image.image_num, image.skeleton, image.sp, image.is_police, image.move_list)

        # data
        code = []

        # update tk1.java file
        with open(path_tk, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write images here" in line:
                self.add_tk_code(code)
                continue

            code.append(line)

        with open(path_tk, 'w', encoding='utf-8') as f:
            f.writelines(code)


        # data
        code = []

        # update tk1.java file
        with open(path_init_tk, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write image A here" in line:
                for image in self.model.all_images:
                    if image.image_name == "A":
                        line = "\t\ttk.MainPhase = tk.PhA = new PhaseA(tk, \"PhaseA\"    , 10  , new int[] "
                        if int(image.skeleton) >= 10:
                            line += "{"
                            line += f"{image.skeleton}"
                            line += " }"
                        else:
                            line += "{ "
                            line += f"{image.skeleton}"
                            line += " }"
                        line += " , new int[] { 0 } , false      , new Move[] {"
                        for move_name in image.move_list:
                            line += f"tk.{move_name.name}, "
                        line = line[:-2] + "});\n"
                        break

            if "write images here" in line:
                self.add_init_tk_code(code)
                continue
            code.append(line)

        with open(path_init_tk, 'w', encoding='utf-8') as f:
            f.writelines(code)

#          10        20        30        40        50        60        70        80        90       100       110       120       130       140
#### tk.PhEQA       = new PhaseEQA     (tk, "PhaseEQA"  , 11  ,    3 ,  1 , true       , new Move[] {tk.k1, tk.pc, tk.pf});

    def add_tk_code(self, code):
        line = "\tpublic Stage "
        for image in self.model.all_images:
            if image.image_name == "A":
                continue
            line += f"Ph{image.image_name}, "
        line = line[:-2]
        line += ";\n"
        code.append(line)

    def add_init_tk_code(self, code):
        all_images = self.model.all_images

        for image in all_images:
            if image.image_name == "A":
                continue
            line = "\t\ttk.Ph"                                  # tk.Ph
            line += f"{image.image_name}"                       # tk.PhEQA
            line += " " * (17 - len(line))                      # add spaces
            line += f"= new Phase{image.image_name}"            # tk.PhEQA = new PhaseEQA
            line += " " * (36 - len(line))                      # add spaces
            line += f"(tk, \"Phase{image.image_name}\""         # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA"
            line += " " * (53 - len(line))                      # add spaces
            if int(image.image_num) >= 10:                      # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11
                line += f", {image.image_num}  "                #
            else:                                               #
                line += f",  {image.image_num}  "               #
            if int(image.skeleton) >= 10:
                line += f",   {image.skeleton} "                # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3
            else:
                line += f",    {image.skeleton} "               # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3
            line += f",  {image.sp} , "                         # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3, 1
            line += f", {image.is_police}"                      # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3, 1, true
            line += " " * (84 - len(line))                      # add spaces
            line += ", new Move[] {"
            for move_name in image.move_list:
                line += f"tk.{move_name.name}, "
            line = line[:-2] + "});\n"
            code.append(line)                                   #

    # def create_file(self):
    #     pass
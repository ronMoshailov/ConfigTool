import re

from PyQt6.QtWidgets import QCheckBox, QMessageBox

import Config.constants
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


    def write_to_file(self, path_tk, path_init_tk, phase_folder_dst):
        # create files
        for image in self.model.all_images:
            self.create_file(image.image_name, phase_folder_dst)

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
            line += f"  {str(image.is_police).lower()}"                      # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3, 1, true
            line += " " * (84 - len(line))                      # add spaces
            line += ", new Move[] {"
            for move_name in image.move_list:
                line += f"tk.{move_name.name}, "
            line = line[:-2] + "});\n"
            code.append(line)                                   #

    # def create_file(self):
    #     pass


    def create_file(self, image_name, path_dst):
        line = ""

        line += "package phase;\n"
        line += "\n"
        line += "import vt.*;\n"
        line += "import enums.ExtensionType;\n"
        line += "import special.Move;\n"
        line += "import special.Stage;\n"
        line += f"import {Config.constants.PROJECT_NUMBER}.Tk1;\n"
        line += f"import {Config.constants.PROJECT_NUMBER}.ParametersTelAviv;\n"
        line += f"import tk.Var;\n"
        line += "\n"
        line += f"public class Phase{image_name}"
        line += " extends Stage {\n"
        line += "\tprivate Tk1 node;"
        line += "\n"
        line += "/**\n"
        line += "\t * Constructor for Haifa applications or for Jerusalem preemption applications\n"
        line += "\t * @param node - the node to which the stage refers\n"
        line += "\t * @param name - the name of the stage\n"
        line += "\t * @param number - the number of the stage\n"
        line += "\t * @param length - the minimal (skeleton) length of the stage\n"
        line += "\t * @param isStopInPolice - whether a non-fixed police program should stop in this stage\n"
        line += "\t * @param sgs - list of moves that must open in this stage\n"
        line += "\t */\n"
        line += f"\tpublic Phase{image_name}(Tk1 node, String name, int number, int length, boolean isStopInPolice, Move[] sgs) \n"
        line += "{\n"
        line += "\t\tsuper(node, name, number, length, isStopInPolice, sgs);\n"
        line += "\t\tthis.node = node;\n"
        line += "}\n"
        line += "\n"
        line += "/**\n"
        line += " * Constructor for applications with parameters based on stop-points\n"
        line += " * @param node - the node to which the stage refers\n"
        line += " * @param name - the name of the stage\n"
        line += " * @param number - the number of the stage\n"
        line += " * @param length - the minimal (skeleton) length of the stage\n"
        line += " * @param sp - this stage's stop-point number\n"
        line += " * @param isStopInPolice - whether a non-fixed police program should stop in this stage\n"
        line += " * @param sgs - list of moves that must open in this stage\n"
        line += " */\n"
        line += f"\tpublic Phase{image_name}(Tk1 node, String name, int numberr, int length, int sp, boolean isStopInPolice, Move[] sgs) "
        line += "{\n"
        line += "\t\tsuper(node, name, numberr, length, sp, isStopInPolice, sgs);\n"
        line += "\t\tthis.node = node;\n"
        line += "}\n"
        line += "\n"
        line += "\t/**\n"
        line += "\t * This methods does two things:<br>\n"
        line += "\t * <ol>\n"
        line += "\t * 	<li>\n"
        line += "\t * 		Sets the type of Minimum extension:\n"
        line += "\t * 		<ul>\n"
        line += "\t * 			<li>duration</li>\n"
        line += "\t * 			<li>absolute</li>\n"
        line += "\t * 			<li>complement</li>\n"
        line += "\t * 		</ul>\n"
        line += "\t * 	</li>\n"
        line += "\t * 	<li>Returns the required value of the Minimum extension</li>\n"
        line += "\t * </ol>\n"
        line += "\t * @return returns the Minimum extension value\n"
        line += "\t */\n"
        line += "\tpublic int getMinExt() {\n"
        line += "\t\tminType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;\n"
        line += "\t\treturn ((ParametersTelAviv)Var.controller.dvi35Parameters).getMinimum(spNum);\n"
        line += "\t}\n"
        line += "\n"
        line += "\t/**\n"
        line += "\t * This methods does two things:<br>\n"
        line += "\t * <ol>\n"
        line += "\t * 	<li>\n"
        line += "\t * 		Sets the type of Maximum extension:\n"
        line += "\t * 		<ul>\n"
        line += "\t * 			<li>duration</li>\n"
        line += "\t * 			<li>absolute</li>\n"
        line += "\t * 			<li>complement</li>\n"
        line += "\t * 		</ul>\n"
        line += "\t * 	</li>\n"
        line += "\t * 	<li>Returns the required value of the Maximum extension</li>\n"
        line += "\t * </ol>\n"
        line += "\t * @return returns the Maximum extension value\n"
        line += "\tpublic int getMaxExt() {\n"
        line += "\t\tmaxType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;\n"
        line += "\t\treturn ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum);\n"
        line += "\t}\n"
        line += "\n"
        line += "\t/**\n"
        line += "\t * This method is called when the stage is terminated\n"
        line += "\t */\n"
        line += "\tprotected void deactivated() {\n"
        line += "\t}\n"
        line += "\n"
        line += "\t/**\n"
        line += "\t * This method is called every scan-cycle while the stage is active\n"
        line += "\t */\n"
        line += "\tpublic Phase phasenFunktion() {\n"
        line += "\t\tstopInPolice();\n"
        line += "\n"
        line += "\t\tif (getPhasenZeit() >= node.lenPhue + getPhLenMS()) \n"
        line += "\t\t{\n"
        line += "\t\t\tgaps = true;\n"
        line += "//\t\t\tgaps = node.e_6.IsActive();\n"
        line += "\n"
        line += "\t\t\tisStageDoneFlag = isStageDone(gaps);\n"
        line += "\n"
        line += "\t\t\tif (isStageDoneFlag) \n"
        line += "\t\t\t{\n"
        line += "//\t\t\tif (node.d_8.IsActive())\n"
        line += "//\t\t\t\t\treturn startNextPhase(node.PhueC_D);\n"
        line += "\t\t\t}\n"
        line += "\t\t}\n"
        line += "\t\treturn KEINE_UMSCHALTUNG;\n"
        line += "\t}\n"
        line += "}\n"

        # create file
        file_path = path_dst / f"Phase{image_name}.java"
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(line)


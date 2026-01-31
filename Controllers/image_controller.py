import re
import Config

from PyQt6.QtWidgets import QMessageBox

class ImageController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

        # Set View Methods
        self.view.add_image_method                  = self.add_image
        self.view.remove_image_method               = self.remove_image
        self.view.update_stop_point_method          = self.update_stop_point
        self.view.update_skeleton_method            = self.update_skeleton
        self.view.update_image_number_method        = self.update_image_number
        self.view.update_move_assignment_method     = self.update_move_assignment

        # Data
        self.all_moves_names = None

    def init_model(self, path, all_moves_names):
        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                m = Config.patterns.image_pattern.search(line)
                if m:
                    image_name = m.group(1)
                    image_num = int(m.group(2).strip())
                    image_skeleton = m.group(3).strip()
                    image_sp = m.group(4).strip()
                    is_police = True if m.group(5).strip() == 'true' else False
                    moves_raw = m.group(6)
                    image_moves = re.findall(r'tk\.([A-Za-z0-9_]+)', moves_raw)

                    if image_name == 'A':
                        image_num = 10
                        image_skeleton = int(re.search(r'\{([^}]*)\}', m.group(3)).group(1).strip())
                        image_sp = 0

                    collection = []

                    for move_name in all_moves_names:
                        if move_name in image_moves:
                            collection.append(move_name)
                    self.model.new_image(image_name, image_num, int(image_skeleton), int(image_sp), is_police, collection)

    def show_view(self, all_moves_names):
        self.all_moves_names = all_moves_names
        self.view.show_view(self.model.all_images, self.all_moves_names)

    # ============================== CRUD ============================== #
    def add_image(self, name):
        """
        This method add new image to the model
        """
        name = name.capitalize()
        name = "EQA" if name.lower == "Eqa" else name

        if not self.model.new_image(name, (len(self.model.all_images) - 1) * 10, 1, len(self.model.all_images)):
            QMessageBox.critical(self.view, "שגיאה", "התמונה כבר קיימת במערכת")
        self.show_view(self.all_moves_names)

    def fetch_images_by_sp(self):
        # Used By Parameters ta Controller
        """
        This method fetch all the images from the model in the order of SP
        """
        return self.model.get_images_by_sp()

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move
        """
        self.model.rename_move(old_name, new_name)

    def remove_image(self, name):
        """
        This method removes image from the model
        """
        self.model.remove_image(name)
        self.show_view(self.all_moves_names)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method removes a move from all the images of the model
        """
        self.model.remove_move(move_name)

    def update_skeleton(self, image_name, skeleton):
        """
        This method update the skeleton number of the image
        """
        self.model.update_skeleton(image_name, skeleton)

    def update_image_number(self, image_name, skeleton):
        """
        This method update the image number of the image
        """
        self.model.update_image_number(image_name, skeleton)
        self.show_view(self.all_moves_names)

    def update_move_assignment(self, image_name, move_name):
        """
        This method toggle the assignment of a move to the image
        """
        self.model.update_move_assignment(image_name, move_name)

    def get_sp_by_image(self, image_name):
        # Used By Parameters ta Controller
        """
        This method returns a stop point by the image name
        """
        return self.model.get_sp_by_image(image_name)

    def update_stop_point(self, image_name, sp):
        """
        This method updates the stop point of the image
        """
        for image in self.model.all_images:
            if image.image_name == image_name:
                image.sp = int(sp)
                break

    # ============================== Logic ============================== #

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_image_model()

    # ============================== Write To File ============================== #
    def write_to_file(self, path_tk, path_init_tk, phase_folder_dst):
        """
        This method write the data from the model to the project
        """
        # create files
        for image in self.model.all_images:
            if image.image_name == "A":
                continue
            self._create_file(image.image_name, phase_folder_dst)

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
                        for move_name in image.move_names_list:
                            line += f"tk.{move_name}, "
                        line = line[:-2] + "});\n"
                        break

            if "write images here" in line:
                self.add_init_tk_code(code)
                continue
            code.append(line)

        with open(path_init_tk, 'w', encoding='utf-8') as f:
            f.writelines(code)

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
            for move_name in image.move_names_list:
                line += f"tk.{move_name}, "
            line = line[:-2] + "});\n"
            code.append(line)                                   #

    def _create_file(self, image_name, path_dst):
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



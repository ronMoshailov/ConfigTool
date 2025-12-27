from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget, QHBoxLayout, QFileDialog, QMessageBox, QMainWindow, QTextEdit, QPushButton, \
    QVBoxLayout, QDialog
import os
import re
from pathlib import Path

from Config.patterns import move_pattern, detectors_pattern, image_pattern, matrix_pattern, schedule_pattern, \
    settings_pattern, sk_pattern
from Config.style import main_window_style
from Controllers.parameters_controller import ParametersTaController
from Models.detector_model import DetectorModel
from Models.image_model import ImageModel
from Models.io_model import IoModel
from Models.matrix_model import MatrixModel
from Models.move_model import MoveModel
from Models.parameters_ta_model import ParametersTaModel
from Models.phue_model import PhueModel
from Models.schedule_model import ScheduleModel
from Models.settings_model import SettingsModel
from Models.sk_model import SkModel
from View.image_view import ImageView

from View.matrix_view import MatrixView
from View.navigator_view import NavigatorView
from View.detector_view import DetectorView
from View.move_view import MoveView
from View.min_green_view import MinGreenView

from Controllers.detector_controller import DetectorController
from Controllers.image_controller import ImageController
from Controllers.io_controller import IoController
from Controllers.matrix_controller import MatrixController
from Controllers.min_green_controller import MinGreenController
from Controllers.move_controller import MoveController
from Controllers.phue_controller import PhueController
from Controllers.schedule_controller import ScheduleController
from Controllers.settings_controller import SettingsController
from Controllers.sk_controller import SkController
from View.parameters_ta_view import ParametersTaView
from View.phue_view import PhueView
from View.schedule_view import ScheduleView
from View.settings_view import SettingsView
from View.sk_view import SkView


class MainController:
    """
    Main application window for the Config Tool.
    Holds all panels in a single layout.
    """
    def __init__(self):

        self.root = QMainWindow()

        self.main_root = QWidget()
        self.main_root.setWindowTitle("Tel Aviv Version")
        self.path_project = None

        # =============== models =============== #
        self.move_model = MoveModel()
        self.detector_model = DetectorModel()
        self.image_model = ImageModel()
        self.phue_model = PhueModel()
        self.matrix_model = MatrixModel()
        self.schedule_model = ScheduleModel()
        self.settings_model = SettingsModel()
        self.sk_model = SkModel()
        self.parameters_ta_model = ParametersTaModel()

        # =============== views =============== #
        self.navigator_view = NavigatorView(self.show_view, self.print_all)
        self.settings_view = SettingsView()
        self.detector_view = DetectorView()
        self.min_green_view = MinGreenView()
        self.matrix_view = MatrixView()
        self.move_view = MoveView()
        self.sk_view = SkView()
        self.schedule_view = ScheduleView()
        self.image_view = ImageView()
        self.phue_view = PhueView()
        self.parameters_ta_view = ParametersTaView()

        # =============== controllers =============== #
        self.image_controller = ImageController(self.image_view, self.image_model)
        # self.io_controller = IoController(root)
        self.matrix_controller = MatrixController(self.matrix_view, self.matrix_model)
        self.move_controller = MoveController(self.move_view, self.move_model)
        self.min_green_controller = MinGreenController(self.min_green_view, self.move_model)
        self.phue_controller = PhueController(self.phue_view, self.phue_model)
        self.schedule_controller = ScheduleController(self.schedule_view, self.schedule_model)
        self.settings_controller = SettingsController(self.settings_view, self.settings_model)
        self.sk_controller = SkController(self.sk_view, self.sk_model)
        self.detector_controller = DetectorController(self.detector_view, self.detector_model)
        self.parameters_ta_controller = ParametersTaController(self.parameters_ta_view, self.parameters_ta_model)

        # =============== layout =============== #
        root_layout = QHBoxLayout()
        root_layout.setAlignment(Qt.AlignmentFlag.AlignRight)
        root_layout.addWidget(self.move_view)
        root_layout.addWidget(self.min_green_view)
        root_layout.addWidget(self.detector_view)
        root_layout.addWidget(self.settings_view)
        root_layout.addWidget(self.matrix_view)
        root_layout.addWidget(self.sk_view)
        root_layout.addWidget(self.schedule_view)
        root_layout.addWidget(self.image_view)
        root_layout.addWidget(self.phue_view)
        root_layout.addWidget(self.parameters_ta_view)
        root_layout.addWidget(self.navigator_view)

        self.navigator_view.write_to_code_method = self.write_to_code

        # =============== self =============== #
        self.main_root.setLayout(root_layout)
        self.root.setCentralWidget(self.main_root)
        self.root.setStyleSheet(main_window_style)
        self.root.show()
        self.root.showMaximized()          # show in full-screen

    def show_view(self, view):
        # hide all views
        self.settings_view.hide_view()
        self.move_view.hide_view()
        self.min_green_view.hide_view()
        self.matrix_view.hide_view()
        self.detector_view.hide_view()
        self.schedule_view.hide_view()
        self.sk_view.hide_view()
        self.image_view.hide_view()
        self.phue_view.hide_view()
        self.parameters_ta_view.hide_view()

        if view == "init":
            self.initialize_app()
            return

        if self.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "פרויקט לא מאותחל")
            return

        if view == "settings":
            self.settings_controller.show_view()
        elif view == "move":
            self.move_controller.show_view()
        elif view == "min_green":
            self.min_green_controller.show_view()
        elif view == "matrix":
            self.matrix_controller.show_view(self.move_model.all_moves)
        elif view == "detector":
            self.detector_controller.show_view()
        elif view == "sk":
            self.sk_controller.show_view(self.move_model.all_moves)
        elif view == "schedule":
            self.schedule_controller.show_view()
            self.schedule_view.show_view()
        elif view == "image":
            self.image_controller.show_view(self.move_model.all_moves)
        elif view == "phue":
            self.phue_controller.show_view(self.image_model.all_images, self.move_model.all_moves)
        elif view == "parameters_ta":
            self.parameters_ta_controller.show_view([img for img in self.image_model.all_images])

    def initialize_app(self):
        # set project path
        self._set_folder_path()
        if self.path_project is None:
            QMessageBox.critical(self.main_root, "שגיאה", "לא נבחרה תיקייה")
            return

        # set files of use
        self._set_files_path()

        # init settings moves
        self._init_settings(self.path_init)

        # init sk moves
        self._init_sk(self.path_init)

        # init project moves
        self.move_controller.init_model(self.path_init_tk1)

        # init detectors moves
        self._init_detectors(self.path_tk1)

        # init images moves
        self._init_images(self.path_init_tk1)

        # init phue moves
        self._init_phue(self.phue_model.phue_paths, self.path_init_tk1)

        # init matrix
        self.matrix_controller.init_model(self.path_init_tk1)

        # init schedule
        self._init_schedule(self.path_init_tk1)

        #
        self.init_ta_parameters(self.path_parameters_ta)

    def print_all(self):
        if self.path_project is None:
            return

        out = []

        out.append("\n============================== Moves ==============================")
        for move in self.move_model.all_moves:
            out.append(
                f"name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")

        out.append("\n============================== Matrix ==============================")
        for cell in self.matrix_model.all_cells:
            out.append(f"out: {cell.move_out:<5}, in: {cell.move_in:<5}, wait: {cell.wait_time:<5}")

        out.append("\n============================== SK ==============================")
        for sk_card in self.sk_model.sk_list:
            out.append(f"----------------------------- sk:{sk_card.card_number} -----------------------------")
            for channel in sk_card.all_channels:
                out.append(
                    f"name: {channel.name:<5}, color: {channel.color:<10}, channel: {channel.channel:<5}, is_comment: {channel.is_comment}")
            out.append("")

        out.append("\n============================== Detector ==============================")
        for detector in self.detector_model.all_detectors:
            ext = detector.ext_unit if detector.ext_unit else "None"
            out.append(f"name: {detector.name:<5}, type: {detector.type:<10}, extension unit: {ext:<5}")

        out.append("\n============================== Schedule ==============================")
        for schedule_table in self.schedule_model.all_schedule_tables:
            out.append(f"----- table: {schedule_table.table_num} -----")
            for cell in schedule_table.cell_list:
                out.append(f"hour: {cell.hour:<5}, minute: {cell.minute:<5}, program_num: {cell.prog_num:<5}")

        out.append("\n============================== Image ==============================")
        for image in self.image_model.all_images:
            if image.image_name == 'A':
                out.append(
                    f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, is_police: {image.is_police:<5}")
            else:
                out.append(
                    f"name: {image.image_name:<5}, number: {image.image_num:<5}, skeleton: {image.skeleton:<5}, sp: {image.sp:<5}, is_police: {image.is_police:<5}")
            move_str = ", ".join([m.name for m in image.move_list])
            out.append(f"moves: {move_str}\n")

        out.append("\n============================== Phue ==============================")
        for phue in self.phue_model.all_phue:
            out.append(f"{phue.image_out:<4} → {phue.image_in:<4}, length: {phue.length:<3}")
            for tran in phue.transitions:
                out.append(f"move: {tran.move:<4}, state: {tran.state:<5}, duration: {tran.duration:<5}")
            out.append("")

        out.append("\n============================== Parameters ==============================")
        for parameter in self.parameters_ta_model.parameters:
            out.append(f"Program number: {parameter.program_number:<4}")
            min_str = ""
            for min_param in parameter.min_list:
                min_str += f"{min_param:<4}"
            out.append(f"Min: {min_str}")
            max_str = ""
            for max_param in parameter.max_list:
                max_str += f"{max_param:<4}"
            out.append(f"Max: {max_str}")
            type_str = ""
            for type_param in parameter.type_list:
                type_str += f"{type_param:<4}"
            out.append(f"Type: {type_str}")

            out.append(f"str: {parameter.str:<4}")
            out.append(f"cycle: {parameter.cycle:<4}")
            out.append("")

        text_to_show = "\n".join(out)

        # -----------------------------
        # יצירת החלון בתוך הפונקציה
        # -----------------------------
        dialog = QDialog(self.root)
        dialog.setWindowTitle("Print All Output")
        dialog.resize(800, 600)

        layout = QVBoxLayout()

        text_edit = QTextEdit()
        text_edit.setReadOnly(True)
        text_edit.setText(text_to_show)

        layout.addWidget(text_edit)

        dialog.setLayout(layout)

        dialog.exec()
    # =============== inner methods =============== #
    def _set_folder_path(self):
        folder_path = QFileDialog.getExistingDirectory(None, "בחר תיקייה")
        if not folder_path:
            print(f"folder_path is false")
            return
        self.path_project = folder_path

    def _set_files_path(self):
        for root, dirs, files in os.walk(self.path_project):
            for file in files:
                if file.lower() == "init.java":  # בודק בלי תלות ברישיות
                    self.path_init = os.path.join(root, file)
                elif file.lower() == "tk1.java":
                    self.path_tk1 = os.path.join(root, file)
                elif file.lower() == "inittk1.java":
                    self.path_init_tk1 = os.path.join(root, file)
                elif file.lower() == "parameterstelaviv.java":
                    self.path_parameters_ta = os.path.join(root, file)
                elif file.lower().startswith("phue"):
                    self.phue_model.phue_paths.append(os.path.join(root, file))

    # =============== inner methods -> init methods =============== #
    def _init_settings(self, path):
        pattern = settings_pattern
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()

        for match in pattern.finditer(content):
            gd = match.groupdict()
            for key, value in gd.items():
                if not value:  # מדלג על None או ריק
                    continue

                if key == "anlagenName":
                    anlagenName = value
                elif key == "tk1Name":
                    tk1Name = value
                elif key == "version":
                    version = value
                elif key == "lastVersion":
                    m = re.match(r'(?P<date>\d{2}/\d{2}/\d{4})\s*-\s*(?P<author>.+)', value)
                    if m:
                        lastVersionDate = m.group("date")
                        lastVersionAuthor = m.group("author")
                elif key == "tk1Arg":
                    first_time_ext = value

        self.settings_model.set(lastVersionAuthor, anlagenName, tk1Name, version, lastVersionDate, first_time_ext)

    def _init_sk(self, path):
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line:
                    self.sk_model.add_sk()

        pattern = sk_pattern
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "new SchaltKanal" not in line:
                    continue
                match = pattern.match(line)
                if match:
                    is_commented = bool(match.group(1))
                    name = match.group(2)
                    color = match.group(3)
                    card_number = int(match.group(4))
                    channel = int(match.group(5))
                    self.sk_model.set_channel(card_number, name, color, channel, is_commented)
                    # if card == self.number_card:
                    #     self.sk_channel_list.append(SkChannel(name, color, channel, is_commented))

    def _init_detectors(self, path):
        """
        This method extracts detector declarations from InitTk1.java.

        :param path: path to "InitTk1.java"
        :return: None
        """
        pattern = detectors_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                matches = pattern.findall(line)
                for detector_type, instances in matches:
                    variables = [v.strip() for v in instances.split(",")]
                    for name in variables:
                        self.detector_model.new_detector(name, detector_type)

        # if len(self.moves) == 0:
        #     Log.warning(f"Warning: Moves not found")

    def _init_images(self, path):
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

                    all_moves = self.move_model.all_moves
                    collection = []

                    for move in all_moves:
                        if move.name in image_moves:
                            collection.append(move)
                    self.image_model.new_image(image_name, image_num, image_skeleton, image_sp, is_police, collection)
                    # self.image_model.new_image(image_name, image_num, image_skeleton, image_sp, is_police)

    def _init_phue(self, path_list, path_len):
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
                transition = self.phue_model.new_transition(move, state, time)
                transitions.append(transition)
            self.phue_model.new_phue(img_out, img_in, 0, transitions)

        with open(path_len, "r", encoding="utf-8") as f:
            content = f.read()

            for match in phue_len_pattern.finditer(content):
                img_out = match.group(1)
                img_in = match.group(2)
                length = match.group(3)
                self.phue_model.update_length(img_out, img_in, length)



    def _init_schedule(self, path):
        pattern = schedule_pattern
        mapping_day = {"sun_thur": [1,2,3,4,5], "fr": [6],"sa": [7]}

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue

                if match.group(1):  # שורת TagesPlan
                    var_name = match.group(1)
                    if var_name in ["kipurEve", "kipur", "blink"]:
                        return
                    days = mapping_day[var_name]

                    # if self.is_valid(var_name):
                    program_number = int(match.group(2))
                    for day in days:
                        self.schedule_model.add_cell(day, 0, 0, program_number)

                else:  # שורת initProgWunsch
                    var_name = match.group(3)
                    days = mapping_day[var_name]
                    hour = int(match.group(4))
                    minute = int(match.group(5))
                    program_number = int(match.group(6))

                    for day in days:
                        self.schedule_model.add_cell(day, hour, minute, program_number)

                    # if self.is_valid(var_name):
                    #     program_number = int(match.group(6))
                        # self.schedule_list.append(Schedule(hour, minute, program_number))
                        # print(f"{var_name}: שעה {hour}, דקה {minute}, תוכנית {program_number}")

    def init_ta_parameters(self, path):
        pattern = re.compile(r'^static int\[\]\s+DVI35_P(\d+)\s*=\s*\{([^}]*)\}')
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue
                index = int(match.group(1))       # 03
                values_str = match.group(2)  # "0, 0, 0, ..."

                # # הפיכת הטקסט של הערכים לרשימת מספרים
                values = [int(v.strip()) for v in values_str.split(",")]
                images_len = len(self.image_model.all_images)

                min_list = values[0: images_len]
                max_list = values[images_len: 2 * images_len]
                type_list = values[2 * images_len: 3 * images_len]

                str = values[3 * images_len]
                cycle = values[ 3 * images_len + 1]

                self.parameters_ta_model.add_program(index, min_list, max_list, type_list, str, cycle)
            print('')
                # print("index:", index)
                # print("values:", values)
                # print("---")


    # ============================================================
    # os.walk returns:
    #   root - current path in the scan
    #   dirs - list of all the folders in root
    #   files - list of all the files in root
    # ============================================================

    # ======================================================= #
    #    not needed for now but maybe in future I will use    #
    # ======================================================= #
    # def reset(self):
    #     """
    #     This method reset all the paths.
    #
    #     :return: None
    #     """
    #     self.path_project = None
    #     self.path_init = None
    #     self.path_tk1 = None
    #     self.path_init_tk1 = None

    def write_to_code(self):
        self.move_controller.write_to_file(self.path_tk1, self.path_init_tk1)
        # self.detector_controller.write_to_file(self.path_init_tk1)
        # self.matrix_controller.write_to_file(self.path_init_tk1)

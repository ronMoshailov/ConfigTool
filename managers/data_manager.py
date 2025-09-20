import re

from config.patterns import move_pattern, matrix_pattern, detectors_pattern, image_pattern, settings_pattern
from entities.detector import Detector
from entities.image import Image
from entities.interstage import InterStage
from entities.log import Log
from entities.matrix import MatrixCell
from entities.signal_group import SignalGroup
from pathlib import Path

from entities.transition import Transition


class DataManager:
    _instance = None

    # --------------- Construction --------------- #
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(DataManager, cls).__new__(cls)    # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                # run _init
        return cls._instance                                        # return _instance

    def __init__(self):
        # Data
        self.moves = []
        self.detectors = []
        self.MatrixCells = []
        self.images = []
        self.inter_stage = []

        # Settings
        self.anlagenName        = None
        self.tk1Name            = None
        self.version            = None
        self.lastVersionDate    = None
        self.lastVersionAuthor  = None
        self.first_time_ext     = None


    # --------------- add methods --------------- #
    def add_move(self, move_name, move_type, is_main):
        # check if move already exist in DB
        if self._get_move_by_name(move_name):
            return False, f"המופע {move_name} כבר קיים במערכת"

        # create move
        new_move = SignalGroup(move_name, move_type, is_main)

        # add move
        self.moves.append(new_move)
        return True, ""






    # --------------- general methods --------------- #
    def _get_move_by_name(self, name):
        for move in self.moves:
            if move.name == name:
                return move
        return None















    def init_moves(self, path):
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
                    new_move = SignalGroup(phase, move_type, True if is_main == "true" else False, min_green)
                    self.moves.append(new_move)
        if len(self.moves) == 0:
            Log.warning(f"Warning: Moves not found")

    def init_matrix(self, path):
        """
        This method set from path the matrix cells in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        pattern = matrix_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                line = line.split("//", 1)[0].strip() # ignore what after //, split maximum 1 time
                if not line:
                    continue

                match = pattern.search(line)
                if match:
                    out = match.group("out")
                    inn = match.group("inn")
                    t1 = int(match.group("t1"))
                    t2 = int(match.group("t2"))

                    self.MatrixCells.append(MatrixCell(out, inn, t1))
                    self.MatrixCells.append(MatrixCell(inn, out, t2))

        if len(self.MatrixCells) == 0:
            Log.warning(f"Warning: Matrix cells not found")


    def init_detectors(self, path):
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
                        self.detectors.append(Detector(name, detector_type))

    def init_phue(self, path_list):
        class_pattern = re.compile(r"public\s+class\s+Phue([A-Za-z0-9]+)_([A-Za-z0-9]+)")
        sg_pattern = re.compile(r"_tk\.(\w+)\.setSg\s*\(Zustand\.(ROT|GRUEN)\s*,\s*(\d+)\s*\)")

        for path in path_list:
            path = Path(path)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()

            # find image_out and image_in
            class_match = class_pattern.search(content)
            if not class_match:
                continue
            img_out, img_in = class_match.groups()
            new_interstage = InterStage(img_out, img_in)

            # find turn of and turn off moves
            for m in sg_pattern.finditer(content):
                move, state, time = m.groups()
                new_interstage.transitions.append(Transition(move, state, time))
            self.inter_stage.append(new_interstage)

    def add_detector(self, detector_name, move_type, ext_time):
        self.detectors.append(Detector(detector_name, move_type, ext_time))
        return True

    def init_settings(self, path):
        pattern = settings_pattern
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()

        for match in pattern.finditer(content):
            gd = match.groupdict()
            for key, value in gd.items():
                if not value:  # מדלג על None או ריק
                    continue

                if key == "anlagenName":
                    self.anlagenName = value
                elif key == "tk1Name":
                    self.tk1Name = value
                elif key == "version":
                    self.version = value
                elif key == "lastVersion":
                    m = re.match(r'(?P<date>\d{2}/\d{2}/\d{4})\s*-\s*(?P<author>.+)', value)
                    if m:
                        self.lastVersionDate = m.group("date")
                        self.lastVersionAuthor = m.group("author")
                elif key == "tk1Arg":
                    self.first_time_ext = value

    def add_detector(self, detector_name, move_type, ext_time):
        self.detectors.append(Detector(detector_name, move_type, ext_time))
        return True

    def add_inter_stage(self, move_out, move_in):
        for stage in self.inter_stage:
            if stage.image_out == move_out and stage.image_in == move_in:
                return False

        new_inter_stage = InterStage(move_out, move_in)
        self.inter_stage.append(new_inter_stage)
        return True

    # --------------- get methods --------------- #
    def get_all_moves(self):
        """
        This method return all the moves sorted combined.

        :return: list of all moves
        """
        self.moves = sorted(self.moves, key=lambda m: m.name)
        sorted_moves = []

        traffic_moves = []
        pedestrian_moves = []
        blinker_moves = []

        for move in self.moves:
            if move.name.startswith("k"):       # insert to list all moves that start with 'k'
                traffic_moves.append(move)
            elif move.name.startswith("p"):     # insert to list all moves that start with 'p'
                pedestrian_moves.append(move)
            elif move.name.startswith("B"):     # insert to list all moves that start with 'B'
                blinker_moves.append(move)
            else:
                Log.error("Error: There is a move with name that not start with 'k', 'p', 'B'")

        return traffic_moves + pedestrian_moves + blinker_moves

    def get_all_matrix_cells(self):
        """
        This method return list of the matrix cells.

        :return: list of all matrix cells.
        """
        return self.MatrixCells

    def get_all_detectors(self):
        return self.detectors

    def get_all_images(self):
        return self.images

    def get_image_count(self):
        return len(self.images)

    def get_all_inter_stages(self):
        return self.inter_stage

    # --------------- update methods --------------- #
    def update_min_green(self, name: str, value: int):
        """
        This method update the minimum green.

        :param name: name of the move.
        :param value: value of the move.
        :return: True if success, False otherwise
        """
        for move in self.moves:
            if move.name == name:
                prev = move.min_green
                move.min_green = value
                Log.success(f"The minimum green has changed from {prev} to {value} successfully")
                return True
        Log.error("Error: The move is not exist")
        return False

    def update_matrix(self, changes_list):
        is_found = False

        for row_name, col_name, value in changes_list:
            for cell in self.MatrixCells:
                if row_name == cell.move_out and col_name == cell.move_in:
                    cell.wait_time = value
                    is_found = True
                    break
            if not is_found:
                self.MatrixCells.append(MatrixCell(row_name, col_name, value))
            is_found = False

        return True
    # --------------- remove methods --------------- #
    def remove_move(self, move_name):
        """
        This method remove the move from the DB.

        :param move_name: name of the move
        :return: True if success, False otherwise
        """
        target = next((m for m in self.moves if m.name == move_name), None)
        if target:
            self.moves.remove(target)
            return True, f"המופע {move_name} נמחק בהצלחה"
        return False, f"לא נמחק מאיזשהי סיבה בקוד"


    def remove_detector(self, detector_name):
        target = next((d for d in self.detectors if d.name == detector_name), None)
        if target:
            self.detectors.remove(target)
            return True
        return False

    # --------------- general methods --------------- #
    def reset(self):
        """
        This method reset all fields

        :return: None
        """
        self.moves.clear()
        self.detectors.clear()
        self.MatrixCells.clear()
        self.images.clear()
        self.inter_stage.clear()

    def init_images(self, path):
        pattern = image_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                m = pattern.search(line)
                if m:
                    name = m.group(1)
                    moves = [re.sub(r'^tk\.', '', item.strip()) for item in m.group(2).split(",")]
                    all_moves = self.get_all_moves()
                    collection = []

                    for move in all_moves:
                        if move.name in moves:
                            collection.append(move)
                    self.images.append(Image(name, collection))

    def update_images(self, table_dict):
        # collect the moves that belong to the image
        for image_name, table in table_dict.items():
            checked_moves = []
            skeleton_time = int(table.skeleton_textbox.text())
            for row_num in range(table.rowCount()):
                move_name = table.cellWidget(row_num, 0).text()
                checkbox = table.cellWidget(row_num, 1)
                if checkbox.isChecked():
                    move = self._get_move_by_name(move_name)
                    if move:
                        checked_moves.append(move)

            is_found = False

            for image in self.images:
                if image.image_name == image_name:
                    image.move_list = checked_moves
                    image.skeleton = skeleton_time
                    is_found = True
            if not is_found:
                self.images.append(Image(image_name, checked_moves))


    def remove_images(self, image_name):
        for image in self.images:
            if image.image_name == image_name:
                self.images.remove(image)
                return True
        return False

    def update_inter_stage(self, table_wrap_list):

        self.inter_stage.clear()

        for wrap in table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            tbl = wrap.table

            new_inter_stage = InterStage(img_out, img_in)

            # find the instance
            for row in range(tbl.rowCount()):
                # עמודה 0 (ComboBox)
                combo_widget = tbl.cellWidget(row, 0)
                value0 = combo_widget.currentText()

                # עמודה 1 (color)
                item1 = tbl.item(row, 1)
                value1 = item1.text()

                value1 = "ROT" if value1 == "🔴" else "GRUEN"
                # עמודה 2 (duration)
                item2 = tbl.item(row, 2)
                value2 = item2.text()

                new_inter_stage.transitions.append(Transition(value0, value1, value2))

            self.inter_stage.append(new_inter_stage)





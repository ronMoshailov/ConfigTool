import re
import Config
from pathlib import Path

from Config.patterns import settings_pattern, move_pattern, matrix_pattern


class LoadDataManager:

    @staticmethod
    def load_settings_data(path):
        # Data
        anlagenName    = None
        tk1Name        = None
        version        = None
        first_time_ext = None
        history       = []

        # Read
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()                              # read all the data from the file

        for match in settings_pattern.finditer(content):    # return iterator each match
            gd = match.groupdict()                          # get dictionary from match [variable name, value of the variable]

            for key, value in gd.items():
                if not value:
                    continue

                if key == "anlagenName":        # junction number
                    anlagenName = value
                elif key == "tk1Name":          # junction name
                    tk1Name = value
                elif key == "version":          # version
                    version = value
                elif key == "versionsInside":   #
                    version_items = re.findall(r'"([^"]+)"', value)
                    for item in version_items:
                        m = re.match(r'(?P<date>\d{2}/\d{2}/\d{4})\s*-\s*(?P<author>.+)', item)
                        if m:
                            date = m.group("date")
                            author = m.group("author")
                            history.append((date, author))
                elif key == "tk1Arg":
                    first_time_ext = value

        return anlagenName, tk1Name, version, first_time_ext, history

    @staticmethod
    def load_moves_data(path):
        # path: initTk1.java
        all_moves = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = move_pattern.match(line)
                if match:
                    variable_name, move_name, move_type, min_green, is_main = match.groups()
                    is_main = True if is_main == "true" else False
                    all_moves.append((variable_name, move_type, is_main, int(min_green)))
        return all_moves

    @staticmethod
    def load_matrix_data(path):
        # path: initTk1.java
        all_cells = []

        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                line = line.split("//", 1)[0].strip() # ignore what after //, split maximum 1 time
                if not line:
                    continue

                match = matrix_pattern.match(line)
                if match:
                    move_out, move_in, t1, t2 = match.groups()
                    all_cells.append((move_out, move_in, t1, t2))

        return all_cells

    @staticmethod
    def load_sk_data(path):
        all_cells = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if line.startswith("package"):
                    Config.constants.PROJECT_NUMBER = line.replace("package", "").replace(";", "").strip()
                    continue
                line = line.strip()
                if "new SchaltKanal" not in line:
                    continue
                match = Config.patterns.sk_pattern.match(line)
                if match:
                    move_name, color, card_number, channel = match.groups()
                    is_commented = line.startswith("//")
                    all_cells.append((int(card_number), move_name, color, int(channel), is_commented))
        return all_cells
                    # if card == self.number_card:
                    #     self.sk_channel_list.append(SkChannel(name, color, channel, is_commented))
    @staticmethod
    def check_sk_count(path):
        count = 0

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line and not line.startswith("//"):
                    count += 1
        return count

    @staticmethod
    def load_schedule_data(path, is_copy_sunday):
        mapping_day = {"sun_thur": [1, 2, 3, 4, 5], "sunday": [1], "monday": [2], "tuesday": [3], "wednesday": [4],
                       "thursday": [5], "fr": [6], "sa": [7]}
        data = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = Config.patterns.schedule_pattern.match(line)
                if not match:
                    continue

                # line with "TagesPlan" (first set of hour-program)
                if match.group(1):
                    var_name = match.group(1)
                    if match.group(1) in ["kipurEve", "kipur", "blink"]:
                        return data

                    # check if each day have different schedule
                    if not is_copy_sunday or (var_name == "monday"):
                        is_copy_sunday = False
                    else:
                        is_copy_sunday = True

                    #
                    program_number = int(match.group(2))
                    for day in mapping_day[var_name]:
                        data.append((day, 0, 0, program_number))
                        # self.model.create_cell(day, 0, 0, program_number)

                # line with "initProgWunsch" (not first set of hour-program)
                else:
                    var_name = match.group(3)
                    days = mapping_day[var_name]
                    hour = int(match.group(4))
                    minute = int(match.group(5))
                    program_number = int(match.group(6))

                    for day in days:
                        data.append((day, hour, minute, program_number))

                        # self.model.create_cell(day, hour, minute, program_number)
            return data

    @staticmethod
    def load_detectors_data(path):
        all_detectors = []

        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                if line.startswith("//"):
                    continue

                match = Config.patterns.detectors_pattern.match(line)
                if match:
                    var_name, class_name, detector_name, move_name = match.groups()
                    all_detectors.append((var_name, class_name, detector_name, move_name))

        return all_detectors

    @staticmethod
    def load_images_data(path, all_moves_names):
        all_images_data = []

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

                    all_images_data.append((image_name, image_num, int(image_skeleton), int(image_sp), is_police, collection))

        return all_images_data

    @staticmethod
    def load_phue_paths(phue_paths):
        class_pattern       = re.compile(r"public\s+class\s+Phue([A-Za-z0-9]+)_?([A-Za-z0-9]+)")
        sg_pattern          = re.compile( r"_tk\.(\w+)\.(TurnOn|TurnOff)\s*\(\s*(\d+)\s*\)")

        data = []

        for path in phue_paths:
            path = Path(path)
            with open(path, "r", encoding="utf-8", errors="ignore") as f:
                content = f.read()

            # find image_out and image_in
            class_match = class_pattern.search(content)
            if not class_match:
                continue
            img_out, img_in = class_match.groups()

            transitions_data = []

            # find turn of and turn off moves
            for m in sg_pattern.finditer(content):
                move, state, time = m.groups()
                transitions_data.append((move, state, time))
            data.append((img_out, img_in, transitions_data))

        return data

    @staticmethod
    def load_phue_length(path_init_tk1):
        phue_len_pattern    = re.compile(r'tk\.Phue([A-Za-z0-9]+)_([A-Za-z0-9]+)\s*=.*?\(\s*tk\s*,\s*"[^"]+"\s*,\s*([0-9]+)')

        data = []

        with open(path_init_tk1, "r", encoding="utf-8") as f:
            content = f.read()

            for match in phue_len_pattern.finditer(content):
                img_out = match.group(1)
                img_in = match.group(2)
                length = match.group(3)

                data.append((img_out, img_in, int(length)))

        return data

    @staticmethod
    def load_parameters_ta(path, images_len):
        pattern = re.compile(r'^static int\[\]\s+DVI35_P(\d+)\s*=\s*\{([^}]*)\}', re.IGNORECASE)
        data = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                match = pattern.match(line)
                if not match:
                    continue
                index = int(match.group(1))       # 03
                values_str = match.group(2)  # "0, 0, 0, ..."
                is_active = 'active' in line.lower()

                # # הפיכת הטקסט של הערכים לרשימת מספרים
                values = [int(v.strip()) for v in values_str.split(",")]

                min_list = values[0: images_len]
                max_list = values[images_len: 2 * images_len]
                type_list = values[2 * images_len: 3 * images_len]

                str = values[3 * images_len]
                cycle = values[ 3 * images_len + 1]

                data.append((index, min_list, max_list, type_list, str, cycle, is_active))

            return data


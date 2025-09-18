from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QLineEdit

from config.special import is_move_valid
from managers.paths_manager import PathsManager
from managers.data_manager import DataManager
from managers.schedule_manager import ScheduleManager
from managers.sk_manager import SkManager
from entities.log import Log

class DataController:
    sk_manager = None
    _instance = None

    # --------------- Construction --------------- #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:                   # checks if there is an instance of the class
            cls._instance = super().__new__(cls)    # create new instance and store him in _instance before __init__
            cls.data_manager = DataManager()
            cls.path_manager = PathsManager()
            cls.sk_manager = []
            cls.schedule_manager = []
            cls.log_textbox = None
        return cls._instance

    # --------------- add methods --------------- #
    def add_move(self, move_name: str, move_type: str, is_main: bool):
        # check if move name empty
        if move_name == "":
            # QMessageBox.critical(self, "שגיאה", "שם המופע ריק")
            return False, "שם המופע ריק"

        # check if move name contain numbers and digits
        if any(c.isalpha() for c in move_name) and any(c.isdigit() for c in move_name):
            # QMessageBox.critical(self, "שגיאה", "שם המופע לא תקין")
            return False, "שם המופע לא תקין"

        # fix the value to fit the DB
        if move_type == "Traffic":
            move_name = "k" + move_name
        elif move_type == "Traffic_Flashing":
            move_name = "k" + move_name
        elif move_type == "Pedestrian":
            move_name = "p" + move_name
        elif move_type == "Blinker":
            move_name = "B" + move_name
        elif move_type == "Blinker_Conditional":
            move_name = "B" + move_name


        # check if the name is valid for the type
        success, message = is_move_valid(move_name, move_type)
        if not success:
            return False, message

        # add move
        success, message = self.data_manager.add_move(move_name, move_type, is_main)
        if not success:
            return False, message

        return True, ""





















    def add_sk(self):
        card_num = len(self.sk_manager) + 1
        new_sk = SkManager(card_num)
        new_sk.initialize_channels()
        self.sk_manager.append(new_sk)

    def add_detector(self, detector_name, move_type, ext_time):
        for detector in self.data_manager.get_all_detectors():
            if detector.name == detector_name:
                self.write_log("Detector already exists", "r")
                return False
        return self.data_manager.add_detector(detector_name, move_type, ext_time)

    def add_schedule_row(self, table_num):
        for manager in self.schedule_manager:
            if manager.table_num == table_num:
                manager.add_schedule()
        pass


    # --------------- get methods --------------- #
    def get_all_moves(self):
        """
        This method returns all the moves.

        :return: list of all moves.
        """
        return self.data_manager.get_all_moves()

    def get_all_matrix_cells(self):
        """
        This method returns the matrix cells.

        :return: list of the matrix cells.
        """
        return self.data_manager.get_all_matrix_cells()

    def get_sk_count(self):
        """
        This method returns the sk count.

        :return: sk count.
        """
        return len(self.sk_manager)

    def get_all_sk_channels(self, number_card: int):
        """
        This method returns all the sk channels in the number card.

        :param number_card: number of the card.
        :return: if found channel list, otherwise None.
        """
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == number_card:
                return sk_manager.get_sk_channel_list()
        return None

    def get_pervious_name(self, number_card: int, channel: int):
        for sk in self.sk_manager:
            if sk.number_card == number_card:
                return sk.get_pervious_name(channel)

    def get_all_detectors(self):
        return self.data_manager.get_all_detectors()

    def get_all_images(self):
        return self.data_manager.get_all_images()

    def get_image_count(self):
        return self.data_manager.get_image_count()

    def get_all_inter_stages(self):
        return self.data_manager.get_all_inter_stages()

    # --------------- update methods --------------- #
    def update_min_green(self, dictionary: dict[str, QLineEdit]):
        """
        This method updates the minimum green time of a move.

        :param dictionary: dictionary that told as key the move name and in value the textBox
        :return: True is success, otherwise False.
        """
        # check if the values are empty
        for key, value in dictionary.items():
            if value.text() == "":
                return False, f"המופע {key} ללא ערך"

        # update values
        for key, value in dictionary.items():
            self.data_manager.update_min_green(key, int(value.text()))
        return True, f"כל הזמני מינימום ירוק עודכנו בהצלחה"





        return True

    def update_matrix(self, changes_list):
        if self.data_manager.update_matrix(changes_list):
            return True, "Matrix Updated"
        return False, "update failed"

    def update_sk_comment(self, card_number: int, channel: int, status):
        """
        This method control the comment statement in sk channel.

        :param card_number: number of the sk card
        :param channel: channel of in the sk card
        :param status: status
        :return: None
        """
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                if sk_manager.update_sk_comment(channel, status):
                    return True
        return False

    def update_sk_color(self, card_number: int, row: int, color: str):
        """
        This method control the color statement in sk channel.

        :param color: 🔴/🟡/🟢
        :param card_number: number of the sk card
        :param row: channel of in the sk card
        :return: None
        """
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                return sk_manager.update_sk_color(row, color)
        return False

    def update_sk_name(self, card_number: int, row: int, new_name: str):
        """
        This method control changing names in sk channel.

        :param card_number: number of the sk card
        :param row: channel of in the sk card
        :param new_name: new name to set
        :return: None
        """
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                sk_manager.update_sk_name(row, new_name)
                return True
        return False

    def clear_channel(self, card_number: int, channel_num: int):
        print(f"**** [class] DataController:\t [method] clear_channel\t[start] ")
        for sk_manager in self.sk_manager:
            if sk_manager.number_card == card_number:
                sk_manager.update_sk_name(channel_num, "")
                sk_manager.update_sk_color(channel_num, to_clear=True)
                sk_manager.update_sk_comment(channel_num, to_clear=True)
        print(f"**** [class] DataController:\t [method] clear_channel\t[end] ")


    # --------------- remove methods --------------- #
    def remove_move(self, move_name):
        """
        This method removes a move.

        :param move_name: name of the move.
        :return: None
        """
        return self.data_manager.remove_move(move_name)

    def remove_sk(self, number_card):
        for i, sk_manager in enumerate(self.sk_manager):
            if sk_manager.number_card == number_card:
                del self.sk_manager[i]
                for j in range(i, len(self.sk_manager)):
                    self.sk_manager[j].number_card -= 1
                return True
        return False

    def remove_detector(self, detector_name):
        if self.data_manager.remove_detector(detector_name):
            msg = f"{detector_name} has been removed successfully"
            self.write_log(msg, "r")
            return True
        msg = f"{detector_name} has not been removed"
        self.write_log(msg, "r")
        return False

    def remove_schedule_row(self, table_num, number_row):
        for manager in self.schedule_manager:
            if manager.table_num == table_num:
                manager.remove_schedule_row(number_row)
                return True
        return False

    # --------------- general methods --------------- #
    def initialize_app(self, btn_list):
        """
        This method initialize the app.

        :param btn_list: list of button to enable after the initialization.
        :return: None
        """
        # print(f"**** [class] DataController:\t [method] initialize_app\t[start] ")

        # set new paths
        if self.path_manager.scan_set_paths() is False:
            Log.error(f"Error: The path initialization failed")
            return None

        # clean all data
        self.reset()

        # scan for moves
        self.data_manager.init_moves(self.path_manager.get_path_init_tk1())
        self.data_manager.init_matrix(self.path_manager.get_path_init_tk1())
        self.data_manager.init_detectors(self.path_manager.get_path_tk1())
        print(f"self.sk_manager: {self.sk_manager}")
        self.set_sk_list(self.path_manager.get_path_init())
        for sk in self.sk_manager:
            sk.initialize_sk(self.path_manager.get_path_init())
        self.set_schedule_list()
        for schedule in self.schedule_manager:
            schedule.initialize_schedule(self.path_manager.get_path_init_tk1())
        self.data_manager.init_images(self.path_manager.get_path_init_tk1())
        self.data_manager.init_settings(self.path_manager.get_path_init())

        # scan for phue
        self.data_manager.init_phue(self.path_manager.get_phue_list())

        # enable buttons
        for btn in btn_list:
            btn.setDisabled(False)
        print(f"**** [class] DataController:\t [method] initialize_app\t[end] ")

    def reset(self):
        """
        This method resets all the data.
        """
        print(f"**** [class] DataController:\t [method] reset\t[start] ")
        # self.path_manager.reset() # For now it's not needed because his fields overwritten if all files exist like they should be.
        self.data_manager.reset()
        self.sk_manager = []
        print(f"**** [class] DataController:\t [method] reset\t[end] ")


    def set_sk_list(self, path):
        """
        This method count how many sk cards exist and create the same instances of 'SkManager' as a list.

        :param path: path to 'init.java'.
        :return: None
        """
        # print(f"**** [class] DataController:\t [method] set_sk_list\t[start] ")
        card_num = 1
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line:

                    self.sk_manager.append(SkManager(card_num))
                    card_num = card_num + 1
        # print(f"Found {card_num - 1} sk cards")
        # print(f"**** [class] DataController:\t [method] set_sk_list\t[end] ")

    def set_log_textbox(self, textbox):
        self.log_textbox = textbox
        self.log_textbox.setPlaceholderText("ברוכה הבאה...")

    # def write_log(self, text, color):
    #     self.log_textbox.clear()  # מנקה
    #
    #     if color == "r":
    #         self.log_textbox.setStyleSheet("background-color: red; color: white")
    #         self.log_textbox.setText(text)
    #     if color == "g":
    #         self.log_textbox.setStyleSheet("background-color: green; color: white")
    #         self.log_textbox.setText(text)
    #     if color == "y":
    #         self.log_textbox.setStyleSheet("background-color: yellow; color: white")
    #         self.log_textbox.setText(text)
    #     self.log_textbox.setAlignment(Qt.AlignmentFlag.AlignLeft)

    def set_schedule_list(self):
        tables_count = 7

        for i in range (1, tables_count + 1):
            self.schedule_manager.append(ScheduleManager(i))

    def get_schedule_list(self, idx):
        return self.schedule_manager[idx].get_schedule_list()

    def update_schedule(self, table_list, is_repeat):
        all_schedules = []

        for idx, manager in enumerate (self.schedule_manager):
            if is_repeat and idx in range(1, 5):
                tbl = table_list[0]
            else:
                tbl = table_list[idx]

            for row in range(tbl.rowCount()):

                time_edit = tbl.cellWidget(row, 1)  # עמודה 0 = QTimeEdit
                combo_prog = tbl.cellWidget(row, 2)  # עמודה 1 = QComboBox

                hour = time_edit.time().hour()
                minute = time_edit.time().minute()
                program_num = int(combo_prog.currentText())

                all_schedules.append((hour, minute, program_num))

            manager.update_schedule(all_schedules)
            all_schedules.clear()

    def update_images(self, table_dict):
        self.data_manager.update_images(table_dict)

    def remove_image(self, image_name):
        self.data_manager.remove_images(image_name)

    def add_inter_stage(self, move_out, move_in):
        return self.data_manager.add_inter_stage(move_out, move_in)

    def update_inter_stage(self, table_wrap_list):
        self.data_manager.update_inter_stage(table_wrap_list)













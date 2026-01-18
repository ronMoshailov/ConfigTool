import re

from PyQt6.QtWidgets import QWidget

from Config.patterns import settings_pattern


class SettingsController(QWidget):

    def __init__(self, view, model):
        super().__init__()

        self.view = view
        self.model = model
        self.is_old = None

        # =============== Controller Methods =============== #
        self.view.update_junction_number_method = self.model.update_junction_number
        self.view.update_junction_name_method   = self.model.update_junction_name
        self.view.update_version_method         = self.model.update_version
        self.view.update_first_cycle_ext_method = self.model.update_first_ext

    def init_model(self, path):
        # data
        pattern = settings_pattern

        anlagenName = None
        tk1Name = None
        version = None
        versions = []
        lastVersionAuthor = None
        first_time_ext = None

        # read
        with open(path, "r", encoding="utf-8") as f:
            content = f.read() # read all the data from the file

        for match in pattern.finditer(content):  # return iterator each match
            gd = match.groupdict() # get dictionary from match [variable name, value of the variable]

            for key, value in gd.items():
                if not value:
                    continue

                if key == "anlagenName": # junction number
                    anlagenName = value
                elif key == "tk1Name": # junction name
                    tk1Name = value
                elif key == "version": #
                    version = value
                elif key == "versionsInside":
                    version_items = re.findall(r'"([^"]+)"', value)

                    for item in version_items:
                        m = re.match(r'(?P<date>\d{2}/\d{2}/\d{4})\s*-\s*(?P<author>.+)', item)
                        if m:
                            date = m.group("date")
                            author = m.group("author")
                            versions.append((date, author))
                elif key == "tk1Arg":
                    first_time_ext = value

        self.model.set(anlagenName, tk1Name, version, versions, first_time_ext)

    def show_view(self):
        self.view.show_view(self.model.junction_num, self.model.junction_name, self.model.version, self.model.first_ext, self.model.history)

    def hide_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def update_model(self, programmer_name, junction_num, junction_name, version, date, first_ext):
        self.model.programmer_name = programmer_name
        self.model.junction_num = junction_num
        self.model.junction_name = junction_name
        self.model.version = version
        self.model.date = date
        self.model.first_ext = first_ext

    # ============================== Logic ============================== #
    def change_state(self, btn):
        selected_btn = self.sender()  # מחזיר את ה־QPushButton שירה את הסיגנל

        if selected_btn.isChecked():
            btn.setDisabled(True)
        else:
            btn.setDisabled(False)

    def make_checkable(self, btn_list):
        for btn in btn_list:
            btn.setCheckable(True)

    def reset(self):
        self.model.reset()

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        # data
        code = []

        # read the code from the file
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        # # "write here"
        # for line in lines:
        #     if "// write settings here" in line:
        #         code.append(f"\tpublic static String anlagenName = \"{self.model.junction_num}\";\n")
        #         code.append(f"\tpublic static String tk1Name     = \"{self.model.junction_name}\";\n")
        #         code.append(f"\tpublic static String version     = \" {self.model.version}\";")
        #         code.append( "\tpublic static String[] versions = {")
        #         code.append(f"\"{self.model.date} - {self.model.programmer_name}\"")
        #         code.append("};")
        #         continue
        #     code.append(line)

        # not for now
        # for line in lines:
        #     if "public static String anlagenName" in line:
        #         code.append(f"\tpublic static String anlagenName = \"{self.model.junction_num}\";\n")
        #     elif "public static String tk1Name" in line:
        #         code.append(f"\tpublic static String tk1Name     = \"{self.model.junction_name}\";\n")
        #     elif "public static String version" in line:
        #         code.append(f"public static String version     = \" {self.model.version}\";")
        #     elif "public static String[] versions" in line:
        #         while "};" not in line:
        #             code.append(line)
        #         code.append()
        #     elif "public static String date": in line:
        #         code.append(f"\tpublic static String tk1Name     = \"{self.model.junction_name}\";\n")
        #
        #     elif "write settings here" in line:
        #
        #         code.append("\tpublic static String[] versions = {")
        #         code.append(f"\"{self.model.date} - {self.model.programmer_name}.\"")
        #         code.append("};")
        #     else:
        #         code.append(line)
        #
        # with open(path, 'w', encoding='utf-8') as f:
        #     f.writelines(code)



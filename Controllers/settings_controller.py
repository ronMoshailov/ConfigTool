import re

from PyQt6.QtWidgets import QWidget

from Config.patterns import settings_pattern


class SettingsController(QWidget):

    def __init__(self, view, model):
        super().__init__()

        self.view = view
        self.model = model

        # =============== Controller Methods =============== #
        self.view.update_settings_method = self.update_model
        self.view.change_state_method = self.change_state
        self.view.make_checkable_method = self.make_checkable

    def init_model(self, path):
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

        self.model.set(lastVersionAuthor, anlagenName, tk1Name, version, lastVersionDate, first_time_ext)

    def show_view(self):
        self.view.show_view(self.model.programmer_name, self.model.junction_num, self.model.junction_name, self.model.version, self.model.date, self.model.first_ext)

    def hide_view(self):
        self.view.show_view()

    def update_model(self, programmer_name, junction_num, junction_name, version, date, first_ext):
        self.model.programmer_name = programmer_name
        self.model.junction_num = junction_num
        self.model.junction_name = junction_name
        self.model.version = version
        self.model.date = date
        self.model.first_ext = first_ext

    def change_state(self, btn):
        selected_btn = self.sender()  # מחזיר את ה־QPushButton שירה את הסיגנל

        if selected_btn.isChecked():
            btn.setDisabled(True)
        else:
            btn.setDisabled(False)

    def make_checkable(self, btn_list):
        for btn in btn_list:
            btn.setCheckable(True)

    def write_to_file(self, path_init):
        # data
        code = []

        # read the code from the file
        with open(path_init, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        #
        for line in lines:
            if "write settings here" in line:
                code.append(f"\tpublic static String anlagenName = \"{self.model.junction_num}\";\n")
                code.append(f"\tpublic static String tk1Name     = \"{self.model.junction_name}\";\n")
                code.append(f"\tpublic static String version     = \"{self.model.version}\";\n")
                code.append("\tpublic static String[] versions = {")
                code.append(f"\"{self.model.date} - {self.model.programmer_name}.\"")
                code.append("};")
            else:
                code.append(line)

        with open(path_init, 'w', encoding='utf-8') as f:
            f.writelines(code)

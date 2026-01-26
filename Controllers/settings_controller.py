import re
import Config

class SettingsController:
    def __init__(self, view, model):
        # Fields
        self.view           = view
        self.model          = model
        self.is_old         = None

        # Data
        self.anlagenName    = None
        self.tk1Name        = None
        self.version        = None
        self.first_time_ext = None
        self.history       = []

        # Controller Methods
        self.view.update_junction_number_method = self.update_junction_number
        self.view.update_junction_name_method   = self.update_junction_name
        self.view.update_version_method         = self.update_version
        self.view.update_first_cycle_ext_method = self.update_update_first_ext
        self.view.add_to_history_method         = self.add_history

    def init_model(self, path):
        # Read
        with open(path, "r", encoding="utf-8") as f:
            content = f.read() # read all the data from the file

        for match in Config.patterns.settings_pattern.finditer(content):  # return iterator each match
            gd = match.groupdict() # get dictionary from match [variable name, value of the variable]

            for key, value in gd.items():
                if not value:
                    continue

                if key == "anlagenName": # junction number
                    self.anlagenName = value
                elif key == "tk1Name": # junction name
                    self.tk1Name = value
                elif key == "version": #
                    self.version = value
                elif key == "versionsInside":
                    version_items = re.findall(r'"([^"]+)"', value)

                    for item in version_items:
                        m = re.match(r'(?P<date>\d{2}/\d{2}/\d{4})\s*-\s*(?P<author>.+)', item)
                        if m:
                            date = m.group("date")
                            author = m.group("author")
                            self.history.append((date, author))
                elif key == "tk1Arg":
                    self.first_time_ext = value

        self.model.set(self.anlagenName, self.tk1Name, self.version, self.history, self.first_time_ext)

    def show_view(self):
        self.view.show_view(self.model.junction_num, self.model.junction_name, self.model.version, self.model.first_ext, self.model.history)

    def hide_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def add_history(self, date, author):
        self.model.add_to_history(date, author)
        self.show_view()

    def update_junction_number(self, text):
        self.model.update_junction_number(text)

    def update_junction_name(self, text):
        self.model.update_junction_name(text)

    def update_version(self, text):
        self.model.update_version(text)

    def update_update_first_ext(self, text):
        self.model.update_first_ext(text)

    # ============================== Logic ============================== #
    def reset(self):
        self.model.reset()

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        # data
        code = []

        # read the code from the file
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write settings here" in line:
                    code.append(f"\tpublic static String anlagenName = \"{self.model.junction_num}\";\n")
                    code.append(f"\tpublic static String tk1Name     = \"{self.model.junction_name}\";\n")
                    code.append(f"\tpublic static String version     = \" {self.model.version}\";")
                    code.append( "\tpublic static String[] versions = {\n")
                    for date, author in self.history:
                        code.append(f"\t\t\"{date} - {author}\",\n")
                    code[-1] = code[-1][:-2]
                    code.append("\n};\n")
                    continue
                code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)



class SettingsModel:
    def __init__(self):
        self.programmer_name = None
        self.junction_num = None
        self.junction_name = None
        self.version = None
        self.date = None
        self.first_ext = None

    def set(self, programmer_name, junction_num, junction_name, version, date, first_ext):
        self.programmer_name = programmer_name
        self.junction_num = junction_num
        self.junction_name = junction_name
        self.version = version
        self.date = date
        self.first_ext = first_ext


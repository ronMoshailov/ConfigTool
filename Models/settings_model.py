class SettingsModel:
    def __init__(self):
        self.junction_num   = None
        self.junction_name  = None     # anlagenName
        self.version        = None
        self.first_ext      = None
        self.history        = []

    # ============================== CRUD ============================== #
    def set(self, junction_num, junction_name, version, versions, first_ext):
        self.junction_num   = junction_num
        self.junction_name  = junction_name
        self.version        = version
        self.first_ext      = first_ext
        self.history        = versions

    def append_to_history(self, date, author):
        self.history.append((date, author))

    def set_junction_number(self, junction_num):
        self.junction_num = junction_num

    def set_junction_name(self, junction_name):
        self.junction_name = junction_name

    def set_version(self, version):
        self.version = version

    def set_first_ext(self, first_ext):
        self.first_ext = first_ext

    # ============================== Logic ============================== #
    def reset_settings_model(self):
        self.junction_num = None
        self.junction_name = None
        self.version = None
        self.first_ext = None
        self.history.clear()


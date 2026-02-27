class SettingsModel:
    def __init__(self):
        self.junction_num   = None
        self.junction_name  = None     # anlagenName
        self.version        = None
        self.first_ext      = None
        self.history        = []

    # ============================== CRUD ============================== #
    def set(self, junction_num, junction_name, version, first_ext, history):
        """
        This method set the data of the model
        """
        self.junction_num   = junction_num
        self.junction_name  = junction_name
        self.version        = version
        self.first_ext      = first_ext
        self.history        = history

    def push_to_history(self, date, author):
        """
        This method add date and author to the history
        """
        self.history.append((date, author))

    def pop_from_history(self, date, author):
        """
        This method pop out item from the history
        """
        for item in self.history:
            if item[0] == date and item[1] == author:
                self.history.remove(item)
                return

    def set_junction_number(self, junction_num):
        """
        This method set the junction number
        """
        self.junction_num = junction_num

    def set_junction_name(self, junction_name):
        """
        This method set the junction name
        """
        self.junction_name = junction_name

    def set_version(self, version):
        """
        This method set the version of the app (Operating System)
        """
        self.version = version

    def set_first_ext(self, first_ext):
        """
        This method set the first time extension
        """
        self.first_ext = first_ext

    # ============================== Logic ============================== #
    def reset_settings_model(self):
        """
        This method reset the data of the model
        """
        self.junction_num = None
        self.junction_name = None
        self.version = None
        self.first_ext = None
        self.history.clear()

    def to_dict(self):
        return {
            'junction_num': self.junction_num,
            'junction_name': self.junction_name,
            'version': self.version,
            'first_ext': self.first_ext,
            'history': self.history
        }
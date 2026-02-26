from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class SettingsController:
    def __init__(self, view, model):
        # Fields
        self.view           = view
        self.model          = model

        # Set View Methods
        self.view.update_junction_number_method = self.update_junction_number
        self.view.update_junction_name_method   = self.update_junction_name
        self.view.update_version_method         = self.update_version
        self.view.update_first_cycle_ext_method = self.update_first_ext
        self.view.add_to_history_method         = self.add_to_history
        self.view.remove_from_history_method    = self.remove_from_history

    def init_model(self, path):
        """
        This method initialize the settings model
        """
        anlagenName, tk1Name, version, first_time_ext, history = LoadDataManager.load_settings_data(path)
        self.model.set(anlagenName, tk1Name, version, first_time_ext, history)

    def show_view(self):
        """
        This method show the view
        """
        self.view.show_view(self.model.junction_num, self.model.junction_name, self.model.version, self.model.first_ext, self.model.history)

    def hide_view(self):
        """
        This method hide the view
        """
        self.view.hide_view()

    # ============================== CRUD ============================== #
    def add_to_history(self, date, author):
        """
        This method add to the history and date and the author
        """
        self.model.append_to_history(date, author)
        self.show_view()

    def remove_from_history(self, date, author):
        """
        This method removes date and author from the history
        """
        self.model.pop_from_history(date, author)
        self.show_view()

    def update_junction_number(self, text):
        """
        This method update the junction number
        """
        self.model.set_junction_number(text)

    def update_junction_name(self, text):
        """
        This method update the junction name
        """
        self.model.set_junction_name(text)

    def update_version(self, text):
        """
        This method update the version of the app
        """
        self.model.set_version(text)

    def update_first_ext(self, text):
        """
        This method update the first extension
        """
        self.model.set_first_ext(text)

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_settings_model()

    # ============================== Write To File ============================== #
    def write_settings_to_project(self, path):
        """
        This method write the data from the model to the project
        """
        code = WriteDataManager.create_settings_init_code(path, self.model.to_dict())
        WriteDataManager.write_code(path, code)

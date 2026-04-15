from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class Io64Controller:

    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

    def init_model(self, path):
        all_cells = LoadDataManager.load_io_data(path)
        for card_number, var_name, channel, is_commented, comment in all_cells:
            if not card_number == 64:
                continue
            self.model.set_channel(var_name, channel, comment, is_commented)

    def show_view(self):
        self.view.show_view(self.model.get_all_channels())

    def hide_view(self):
        self.view.hide_view()

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_io64_model()

    # ============================== Write To File ============================== #
    # def write_io24_to_project(self, path_init_tk1):
    #     # update tk1.java file
    #     code = WriteDataManager.create_io24_code(path_init_tk1, self.model.get_io_list())
    #     WriteDataManager.write_code(path_init_tk1, code)



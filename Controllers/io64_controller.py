from Managers.load_data_manager import LoadDataManager


class Io64Controller:

    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

    def init_model(self, path):
        all_cells = LoadDataManager.load_io_data(path)
        for card_number, var_name, channel, is_commented in all_cells:
            if not card_number == 64:
                continue
            self.model.set_channel(var_name, card_number, channel, is_commented)
        self.model.print_data()

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_io64_model()

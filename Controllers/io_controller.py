from Managers.load_data_manager import LoadDataManager


class IoController:

    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

    def init_model(self, path):
        io_count = LoadDataManager.check_io_count(path)
        for _ in range(io_count):
            self.model.add_io_card()
        print(len(self.model.io_list))

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_io_model()

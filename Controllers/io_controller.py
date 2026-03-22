from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class IoController:

    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

    def init_model(self, path):
        io_count = LoadDataManager.check_io_count(path)
        for _ in range(io_count):
            self.model.add_io_card()

        all_cells = LoadDataManager.load_io_data(path)
        for card_number, var_name, channel, is_commented, comment in all_cells:
            if card_number == 64:
                continue
            self.model.set_channel(var_name, card_number, channel, is_commented)

        # self.model.print_data()
        print("done")

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_io_model()

    # ============================== Write To File ============================== #
    def write_io24_to_project(self, path_init_tk1):
        # update tk1.java file
        code = WriteDataManager.create_io24_code(path_init_tk1, self.model.get_io_list())
        WriteDataManager.write_code(path_init_tk1, code)



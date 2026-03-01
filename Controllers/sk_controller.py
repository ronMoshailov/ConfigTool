from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class SkController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model
        self.all_moves = None

        # Controller Methods
        self.view.add_sk_card_method = self.add_sk_card
        self.view.remove_sk_card_method = self.remove_sk_card_card
        self.view.set_channel_method = self.set_channel

    def init_model(self, path):
        sk_count = LoadDataManager.check_sk_count(path)
        for _ in range(sk_count):
            self.model.add_sk_card()

        all_cells = LoadDataManager.load_sk_data(path)
        for card_number, move_name, color, channel, is_commented in all_cells:
            self.set_channel(card_number, move_name, color, channel, is_commented)

    def show_view(self, all_moves):
        self.all_moves = all_moves
        self.view.show_view(self.model.sk_list, all_moves)

    def hide_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def add_sk_card(self):
        """
        This method add new sk card to the model
        """
        self.model.add_sk_card()
        self.view.show_view(self.model.sk_list, self.all_moves)

    def set_channel(self, card_number, move_name, color, channel, is_commented):
        self.model.set_channel(card_number, move_name, color, channel, is_commented)

    def remove_sk_card_card(self, card_num):
        """
        This method remove sk card from the model
        """
        self.model.remove_sk_card(card_num)
        self.view.show_view(self.model.sk_list, self.all_moves)

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move from all the sk cards in the model
        """
        self.model.rename_move(old_name, new_name)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method set a removed move as comment
        """
        self.model.remove_move(move_name)

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_sk_model()

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        code = WriteDataManager.create_sk_init_code(path, self.model.sk_list)
        WriteDataManager.write_code(path, code)


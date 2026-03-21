class _IoChannel:
    def __init__(self, name: str = "", channel: int = None, is_comment: bool = False):
        self.name           = name
        self.channel        = channel
        self.is_comment     = is_comment

    def set(self, name, is_comment):
        """
        This method sets the values of the channel
        """
        self.name           = name
        self.is_comment     = is_comment

class _IoCard:
    def __init__(self, card_number):
        self.card_number    = card_number
        self.all_channels   = []

        for i in range(24):
            channel = _IoChannel(channel=i+1)
            self.all_channels.append(channel)

    def set_channel(self, name, channel_number, is_comment):
        """
        This method sets the values of the channel
        """
        for sk_channel in self.all_channels:
            if sk_channel.channel == channel_number:
                sk_channel.set(name, is_comment)

class IoModel:
    def __init__(self):
        self.io_list = []

    # ============================== CRUD ============================== #
    def add_io_card(self):
        """
        This method add new io card to the model
        """
        io_card = _IoCard(len(self.io_list) + 1)
        self.io_list.append(io_card)

    def remove_card_sk(self, card_num):
        """
        This method removes sk card from the model
        """
        is_removed = False
        io_to_remove = None

        for card in self.io_list:
            if card.card_number == card_num and is_removed is False:
                io_to_remove = card
                is_removed = True
                continue
            if is_removed:
                card.card_number = card.card_number - 1

        self.io_list.remove(io_to_remove)

    # def rename_move(self, old_name, new_name):
    #     """
    #     This method rename a move from all the sk cards in the model
    #     """
    #     for sk in self.sk_list:
    #         all_channels = sk.all_channels
    #         for channel in all_channels:
    #             if channel.name == old_name:
    #                 channel.name = new_name

    # def remove_move(self, move_name):
    #     """
    #     This method set a removed move as comment
    #     """
    #     for sk in self.sk_list:
    #         for channel in sk.all_channels:
    #             if channel.name == move_name:
    #                 channel.is_comment = True

    # ============================== Logic ============================== #
    # def set_channel(self, sk_num, name, color, channel, is_comment):
    #     """
    #     This method set channel of a sk card
    #     """
    #     for sk_card in self.sk_list:
    #         if sk_card.card_number == sk_num:
    #             sk_card.set_channel(name, color, channel, is_comment)

    def reset_io_model(self):
        """
        This method removes all the data in the model
        """
        self.io_list.clear()


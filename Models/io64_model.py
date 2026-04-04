class _IoChannel:
    def __init__(self, name: str = "", channel: int = None, is_comment: bool = False, comment: str = None):
        self.name           = name
        self.channel        = channel
        self.is_comment     = is_comment
        self.comment        = comment

    def set(self, name, is_comment, comment):
        """
        This method sets the values of the channel
        """
        self.name           = name
        self.is_comment     = is_comment
        self.comment        = comment

class _IoCard:
    def __init__(self):
        self.all_channels   = []

        for i in range(64):
            channel = _IoChannel(channel=i+1)
            self.all_channels.append(channel)

    def set_channel(self, name, channel_number, is_comment, comment):
        """
        This method sets the values of the channel
        """
        for sk_channel in self.all_channels:
            if sk_channel.channel == channel_number:
                sk_channel.set(name, is_comment, comment)

    def reset(self):
        self.all_channels.clear()
        for i in range(64):
            channel = _IoChannel(channel=i+1)
            self.all_channels.append(channel)

class Io64Model:
    def __init__(self):
        self.io64_card = _IoCard()

    # ============================== CRUD ============================== #
    # def add_io_card(self):
    #     """
    #     This method add new io card to the model
    #     """
    #     io_card = _IoCard(len(self.io_list) + 1)
    #     self.io_list.append(io_card)

    def get_all_channels(self):
        return self.io64_card.all_channels

    # def remove_card_sk(self, card_num):
    #     """
    #     This method removes sk card from the model
    #     """
    #     is_removed = False
    #     io_to_remove = None
    #
    #     for card in self.io_list:
    #         if card.card_number == card_num and is_removed is False:
    #             io_to_remove = card
    #             is_removed = True
    #             continue
    #         if is_removed:
    #             card.card_number = card.card_number - 1
    #
    #     self.io_list.remove(io_to_remove)

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
    def set_channel(self, var_name, channel, is_commented, comment):
        """
        This method set channel of a sk card
        """
        self.io64_card.set_channel(var_name, channel, is_commented, comment)

    def reset_io64_model(self):
        """
        This method removes all the data in the model
        """
        self.io64_card.reset()

    def print_data(self):
        channels = self.io64_card.all_channels

        print(f"==== IO64 ====")
        for channel in channels:
            if channel.name == "":
                continue
            print(f"name: {channel.name:<30}, channel: {channel.channel:<10}, is_comment: {channel.is_comment:<10}, comment: {channel.comment}")
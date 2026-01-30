class _SkChannel:
    def __init__(self, name: str = "", color: str = "", channel: int = None, is_comment: bool = False):
        self.name           = name
        self.color          = color
        self.channel        = channel
        self.is_comment     = is_comment

    def set(self, name, color, is_comment):
        """
        This method sets the values of the channel
        """
        self.name           = name
        self.color          = color
        self.is_comment     = is_comment

class _SkCard:
    def __init__(self, card_number):
        self.card_number    = card_number
        self.all_channels   = []

        for i in range(24):
            channel = _SkChannel(channel=i+1)
            self.all_channels.append(channel)

    def set_channel(self, name, color, channel_number, is_comment):
        """
        This method sets the values of the channel
        """
        for sk_channel in self.all_channels:
            if sk_channel.channel == channel_number:
                sk_channel.set(name, color, is_comment)

class SkModel:
    def __init__(self):
        self.sk_list = []

    # ============================== CRUD ============================== #
    def add_sk_card(self):
        """
        This method add new sk card to the model
        """
        sk_card = _SkCard(len(self.sk_list) + 1)
        self.sk_list.append(sk_card)

    def remove_card_sk(self, card_num):
        """
        This method removes sk card from the model
        """
        is_removed = False
        sk_to_remove = None

        for sk in self.sk_list:
            if sk.card_number == card_num and is_removed is False:
                sk_to_remove = sk
                is_removed = True
                continue
            if is_removed:
                sk.card_number = sk.card_number - 1

        self.sk_list.remove(sk_to_remove)

    def rename_move(self, old_name, new_name):
        """
        This method rename a move from all the sk cards in the model
        """
        for sk in self.sk_list:
            all_channels = sk.all_channels
            for channel in all_channels:
                if channel.name == old_name:
                    channel.name = new_name

    def remove_move(self, move_name):
        """
        This method set a removed move as comment
        """
        for sk in self.sk_list:
            for channel in sk.all_channels:
                if channel.name == move_name:
                    channel.is_comment = True

    # ============================== Logic ============================== #
    def set_channel(self, sk_num, name, color, channel, is_comment):
        """
        This method set channel of a sk card
        """
        for sk_card in self.sk_list:
            if sk_card.card_number == sk_num:
                sk_card.set_channel(name, color, channel, is_comment)

    def reset_sk_model(self):
        """
        This method removes all the data in the model
        """
        self.sk_list.clear()


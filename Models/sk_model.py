class SkChannel:
    """
    This class is used to represent a sk channel.
    """
    def __init__(self, name: str = "", color: str = "", channel: int = None, is_comment: bool = False):
        self.name = name
        self.color = color
        self.channel = channel
        self.is_comment = is_comment

    def set(self, name, color, is_comment):
        self.name = name
        self.color = color
        self.is_comment = is_comment


class SkCard:
    def __init__(self, card_number):
        self.card_number = card_number
        self.all_channels = []

        for i in range(24):
            channel = SkChannel(channel=i+1)
            self.all_channels.append(channel)

    def set_channel(self, name, color, channel_number, is_comment):
        for sk_channel in self.all_channels:
            if sk_channel.channel == channel_number:
                sk_channel.set(name, color, is_comment)


class SkModel:
    def __init__(self):
        self.sk_list = []

    def add_sk(self):
        sk_card = SkCard(len(self.sk_list) + 1)
        self.sk_list.append(sk_card)

    def remove_sk(self, card_num):
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

    def set_channel(self, sk_num, name, color, channel, is_comment):
        for sk_card in self.sk_list:
            if sk_card.card_number == sk_num:
                sk_card.set_channel(name, color, channel, is_comment)

    # def update_data(self, card_number, name, color, channel, is_comment):
    #     for sk_card in self.sk_list:
    #         if sk_card.card_number == card_number:
    #             sk_card.set_channel(name, color, channel, is_comment)

    def reset(self):
        self.sk_list.clear()

    def update_names(self, old_name, new_name):
        for sk in self.sk_list:
            all_channels = sk.all_channels
            for channel in all_channels:
                if channel.name == old_name:
                    channel.name = new_name

    def remove_move(self, move_name):
        for sk in self.sk_list:
            for channel in sk.all_channels:
                if channel.name == move_name:
                    channel.is_comment = True
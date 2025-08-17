import re

from config.patterns import sk_pattern
from entities.log import Log
from entities.sk_channel import SkChannel


class SkManager:

    # =========================================== #
    #                Construction                 #
    # =========================================== #
    def __init__(self, number_card):
        self.number_card = number_card
        self.sk_channel_list = []

    # =========================================== #
    #                 add methods                 #
    # =========================================== #

    # =========================================== #
    #                  get methods                #
    # =========================================== #
    def get_sk_channel_list(self):
        """
        This method return a list of sk channel.

        :return: list of sk channel.
        """
        return self.sk_channel_list

    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_sk_comment(self, channel: str):
        """
        This method update the comment state of sk channel.

        :param channel: sk channel to change
        :return: None
        """
        print(f"[class] SkManager:\t [method] update_sk_comment\t[start] ")
        for sk_channel in self.sk_channel_list:
            if sk_channel.channel == int(channel):
                sk_channel.is_comment = not sk_channel.is_comment
        print(f"[class] SkManager:\t [method] update_sk_comment\t[end] ")

    def update_sk_color(self, row: int):
        print(f"[class] SkManager:\t [method] update_sk_color\t[start] ")
        cur_color = self.sk_channel_list[row].color
        if cur_color == "hwRed200":
            self.sk_channel_list[row].color = "hwAmber200"
        elif cur_color == "hwAmber200":
            self.sk_channel_list[row].color = "hwGreen200"
        elif cur_color == "hwGreen200":
            self.sk_channel_list[row].color = "hwRed200"
        else:
            Log.error("The color is not 'hwRed20' or 'hwAmber200' or 'hwGreen200'")
        print(f"[class] SkManager:\t [method] update_sk_color\t[end] ")

    def update_sk_name(self, row: int, new_name: str):
        print(f"[class] SkManager:\t [method] update_sk_name\t[start] ")
        self.sk_channel_list[row].name = new_name
        print(f"[class] SkManager:\t [method] update_sk_name\t[end] ")

    # =========================================== #
    #               remove methods                #
    # =========================================== #

    # =========================================== #
    #                general methods              #
    # =========================================== #
    def initialize_sk(self, path):
        """
        This method initializes the sk channel list for every sk card

        :param path: path of the file that holds the sk channel list
        :return: None
        """
        print(f"** [class] SkManager:\t [method] initialize_sk\t[start] ")
        pattern = sk_pattern

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()

                if "new SchaltKanal" not in line:
                    continue
                match = pattern.match(line)
                if match:
                    is_commented = bool(match.group(1))
                    name = match.group(2)
                    color = match.group(3)
                    card = int(match.group(4))
                    channel = int(match.group(5))
                    if card == self.number_card:
                        self.sk_channel_list.append(SkChannel(name, color, channel, is_commented))
        print(f"** [class] SkManager:\t [method] initialize_sk\t[end] ")



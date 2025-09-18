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


    # --------------- add methods ---------------


    # --------------- get methods ---------------
    def get_sk_channel_list(self):
        """
        This method return a list of sk channel.

        :return: list of sk channel.
        """
        return self.sk_channel_list

    def get_pervious_name(self, num_channel):
        for ch in self.sk_channel_list:
            if ch.channel == num_channel:
                return ch.name
        return False

    # =========================================== #
    #               update methods                #
    # =========================================== #
    def update_sk_comment(self, channel: str, status, to_clear: bool = False):
        """
        This method update the comment state of sk channel.

        :param channel: sk channel to change
        :return: None
        """
        for sk_channel in self.sk_channel_list:
            if sk_channel.channel == int(channel) + 1:
                if to_clear:
                    sk_channel.is_comment = False
                    return True
                sk_channel.is_comment = status
                return True
        return False

    def update_sk_color(self, row: int, color: str, to_clear: bool = False):
        if to_clear:
            self.sk_channel_list[row].color = ""
            return True

        if color == "🔴":
            self.sk_channel_list[row].color = "hwRed200"
        elif color == "🟡":
            self.sk_channel_list[row].color = "hwAmber200"
        elif color == "🟢":
            self.sk_channel_list[row].color = "hwGreen200"
        elif color == "":
            self.sk_channel_list[row].color = ""
        return True

    def update_sk_name(self, row: int, new_name: str):
        self.sk_channel_list[row].name = new_name


    # =========================================== #
    #               remove methods                #
    # =========================================== #
    def remove(self):
        pass

    # =========================================== #
    #                general methods              #
    # =========================================== #
    def initialize_sk(self, path):
        """
        This method initializes the sk channel list for every sk card

        :param path: path of the file that holds the sk channel list
        :return: None
        """
        # print(f"** [class] SkManager:\t [method] initialize_sk\t[start] ")
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
        self.initialize_channels()
        # print(f"** [class] SkManager:\t [method] initialize_sk\t[end] ")


    def initialize_channels(self):
        print(f"[class] SkManager:\t [method] initialize_channels\t[start] ")
        # create list of all the channels
        channel_numbers = list(range(1, 25))

        # remove the exist channels
        for channel in self.sk_channel_list:
            channel_numbers.remove(channel.channel)

        # initialize the empty channels
        for channel in channel_numbers:
            self.sk_channel_list.append(SkChannel("", "", channel, False))

        print(f"[class] SkManager:\t [method] initialize_channels\t[start] ")

import re

from entities.sk24_channel import SK24_channel
from config.constants import RED


class Sk24:
    def __init__(self, number_card, path):
        """
        Initializes an SK24 instance, loads all relevant channels from the given init.java file.

        :param number_card: The card number
        :param path: Path to the 'init.java' file
        """
        self.number_card = number_card
        self.channels = []

        self.set_all_channels(path)
        self._print_channels()

    # =============== methods =============== #
    def set_all_channels(self, path):
        """
        This method get a path to 'init.java' file and set all channels that belong this card number

        :param path: path to 'init.java' file
        :return: None
        """
        with open(path, 'r', encoding='utf-8') as file:
            cleared_code = self._clear_code(file)
            for code_line in cleared_code:
                stripped = code_line.strip()

                phase = self._find_phase(stripped)                              # find phase
                color = self._find_color(stripped)                              # find color
                channel = self._find_channel(stripped)                          # find channel
                new_channel = SK_channel(phase, color, channel, True)   # create new instance of Channel
                self.channels.append(new_channel)                               # add the new channel to list


    def _clear_code(self, old_code):
        """
        This method get an object file of 'init.java' and return just the code lines that start with 'new SchaltKanal'

        :param old_code: Object of file
        :return: list of code that start with 'new SchaltKanal'
        """
        code = []
        for line in old_code:
            line = line.strip()
            if line.startswith("new SchaltKanal") and f"sk{self.number_card}" in line:
                code.append(line)
        return code


    def _find_phase(self, code_line):
        """
        This method find the phase of the code line

        :param code_line: the line of the code
        :return: name of the phase
        """
        pattern = r"Var\.tk1\.(k\d+|p[A-Za-z]|B[A-Za-z])(?=[ ,])"
        match = re.search(pattern, code_line)
        if match:
            # print(f"נמצא: {match.group(1)}")
            return match.group(1)
        else:
            # print("לא נמצא")
            return None


    def _find_color(self, code_line):
        """
        This method find the color of the code line

        :param code_line: the line of the code
        :return: Color of the channel, or None if not found.
        """
        color_red = "red"
        color_amber = "amber"
        color_green = "green"

        if color_red in code_line:
            return color_red
        elif color_amber in code_line:
            return color_amber
        elif color_green in code_line:
            return color_green
        print(f"{RED}Color not found{RED}")
        return None

    def _find_channel(self, code_line):
        """
        This method find the number channel of the code line
        :param code_line:
        :return:
        """
        pattern = r"sk\d+\s*,\s*(\d+)"

        match = re.search(pattern, code_line)
        if match:
            # print(f"המספר הוא: {match.group(1)}")
            return match.group(1)
        else:
            # print("לא נמצא מספר אחרי skX")
            return None


    def _print_channels(self):
        """
        This method print all the channels that belong to this SK card
        :return:
        """
        for channel in self.channels:
            channel.print()



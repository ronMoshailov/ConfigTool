from PyQt5.QtWidgets import QWidget


class SkChannel:
    def __init__(self, name, color, channel, is_comment):
        self.name = name
        self.color = color
        self.channel = channel
        self.is_comment = is_comment


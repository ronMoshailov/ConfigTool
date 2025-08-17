class SkChannel:
    """
    This class is used to represent a sk channel.
    """
    def __init__(self, name: str, color: str, channel: int, is_comment: bool):
        self.name = name
        self.color = color
        self.channel = channel
        self.is_comment = is_comment


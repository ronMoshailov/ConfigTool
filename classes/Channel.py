class Channel:
    def __init__(self, phase, color, channel, is_loaded = False):
        self.phase = phase
        self.color = color
        self.channel = channel
        self.is_loaded = is_loaded

    def print(self):
        print(f"phase: {self.phase:<4}  color: {self.color:<6}  channel: {self.channel:<4}  is_loaded: {self.is_loaded}")

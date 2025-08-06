class Io24:
    def __init__(self, number_card):
        self.number_card = number_card
        for i in range(1, 25):
            setattr(self, f"chanel{i:02}", None)
        for i in range(1, 25):
            setattr(self, f"chanel{i:02}_is_loaded", None)



class InterStage:
    def __init__(self, move_out, move_in, transitions=None):
        self.image_out   = move_out
        self.image_in    = move_in
        self.transitions = []

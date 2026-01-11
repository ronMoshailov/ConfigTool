class Phue:
    def __init__(self, move_out: str, move_in: str, length: int, transitions: list = []):
        self.image_out   = move_out
        self.image_in    = move_in
        self.length = length
        self.transitions = transitions

class Transition:
    def __init__(self, move, state, duration):
        self.move = move
        self.state = state
        self.duration = duration


class PhueModel:
    def __init__(self):
        self.all_phue = []
        self.phue_paths = []

    def new_phue(self, image_out_arg, image_in_arg, length = 0, transitions = []):
        for phue in self.all_phue:
            img_out = phue.image_out
            img_in = phue.image_in
            if img_out == image_out_arg and img_in == image_in_arg:
                return False

        phue = Phue(image_out_arg, image_in_arg, length, transitions)
        self.all_phue.append(phue)
        return True

    def new_transition(self, move, state, duration):
        transition = Transition(move, state, duration)
        return transition

    def remove_phue(self, img_out, img_in):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                self.all_phue.remove(phue)
                return

    def update_phue(self, img_out, img_in, length, move_name_list, color_list, time_list):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                phue.length = length
                phue.transitions.clear()
                for move, color, time in zip(move_name_list, color_list, time_list):
                    phue.transitions.append(Transition(move, color, time))
                return

    def update_length(self, img_out, img_in, length):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                phue.length = length
                return

    def reset(self):
        self.all_phue.clear()
        self.phue_paths.clear()


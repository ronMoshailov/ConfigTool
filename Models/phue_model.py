class _Phue:
    def __init__(self, move_out: str, move_in: str, length: int, transitions: list = []):
        self.image_out   = move_out
        self.image_in    = move_in
        self.length = length
        self.transitions = transitions

class _Transition:
    def __init__(self, move_name, state, duration):
        self.move_name = move_name
        self.state = state
        self.duration = duration


class PhueModel:
    def __init__(self):
        self.all_phue = []
        self.phue_paths = []

    # ============================== CRUD ============================== #
    def new_phue(self, image_out_arg, image_in_arg, length = 0, transitions = []):
        for phue in self.all_phue:
            img_out = phue.image_out
            img_in = phue.image_in
            if img_out == image_out_arg and img_in == image_in_arg:
                return False

        phue = _Phue(image_out_arg, image_in_arg, length, transitions)
        self.all_phue.append(phue)
        return True

    def new_transition(self, move, state, duration):
        transition = _Transition(move, state, duration)
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
                    phue.transitions.append(_Transition(move, color, time))
                return

    def update_length(self, img_out, img_in, length):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                phue.length = length
                return

    def rename_move(self, old_name, new_name):
        for phue in self.all_phue:
            for transition in phue.transitions:
                if transition.move_name == old_name:
                    transition.move_name = new_name

    def remove_move(self, move_name):
        for phue in self.all_phue:
            for transition in phue.transitions:
                if transition.move_name == move_name:
                    phue.transitions.remove(transition)

    def update_duration(self, img_out, img_in, move_name, duration):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                for t in phue.transitions:
                    if t.move_name == move_name:
                        t.duration = duration

    def update_move_name(self, image_out, image_in, old_name, new_name):
        transition_to_change = None

        for phue in self.all_phue:
            if phue.image_out == image_out and phue.image_in == image_in:
                for t in phue.transitions:
                    if t.move_name == old_name:
                        transition_to_change = t
                    elif t.move_name == new_name:
                        raise ValueError("המופע כבר קיים במעבר")
                transition_to_change.move_name = new_name
                return

    def update_move_color(self, image_out, image_in, move_name):
        for phue in self.all_phue:
            if phue.image_out == image_out and phue.image_in == image_in:
                for t in phue.transitions:
                    if t.move_name == move_name:
                        if t.state == "TurnOn":
                            t.state = "TurnOff"
                        else:
                            t.state = "TurnOn"

    def update_phue_len(self, img_out, img_in, length):
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                phue.length = length

    # ============================== Logic ============================== #
    def reset(self):
        self.all_phue.clear()
        self.phue_paths.clear()



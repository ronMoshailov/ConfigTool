from Config.exceptions import DuplicateMoveError, DuplicatePhueError


class _Phue:
    def __init__(self, move_out: str, move_in: str, length: int, transitions: list = []):
        self.image_out      = move_out
        self.image_in       = move_in
        self.length         = length
        self.transitions    = transitions

class _Transition:
    def __init__(self, move_name, state, duration):
        self.move_name      = move_name
        self.state          = state
        self.duration       = duration

class PhueModel:
    def __init__(self):
        self.all_phue       = []
        self.phue_paths     = []

    # ============================== CRUD ============================== #
    def create_new_phue(self, image_out_arg, image_in_arg, length = 0, transitions = []):
        """
        This method create new phue in the model with length of 0 and empty transitions
        """
        for phue in self.all_phue:
            img_out = phue.image_out
            img_in = phue.image_in
            if img_out == image_out_arg and img_in == image_in_arg:
                raise DuplicatePhueError("המעבר כבר קיים במערכת")

        phue = _Phue(image_out_arg, image_in_arg, length, transitions)
        self.all_phue.append(phue)
        return True

    def create_new_transition(self, move, state, duration):
        """
        This method create a new transition
        """
        transition = _Transition(move, state, duration)
        return transition

    def add_transition(self, img_out, img_in):
        """
        This method add a new transition to the model with default data
        """
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                t = self.create_new_transition("-", "TurnOff", 0)
                phue.transitions.append(t)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method remove all transitions that has the same 'move_name'
        """
        for phue in self.all_phue:
            for transition in phue.transitions:
                if transition.move_name == move_name:
                    phue.transitions.remove(transition)

    def remove_phue(self, img_out, img_in):
        """
        This method remove phue from the model
        """
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                self.all_phue.remove(phue)
                return

    def remove_transition(self, img_out, img_in, move_name):
        """
        This method removes a transition from the phue
        """
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                for t in phue.transitions:
                    if t.move_name == move_name:
                        phue.transitions.remove(t)
                        return

    def set_transition_move(self, image_out, image_in, old_name, new_name):
        """
        This method set a new value for the transition move
        """
        transition_to_change = None

        for phue in self.all_phue:
            if phue.image_out == image_out and phue.image_in == image_in:
                for t in phue.transitions:
                    if t.move_name == old_name:
                        transition_to_change = t
                    elif t.move_name == new_name:
                        raise DuplicateMoveError("המופע כבר קיים במעבר")
                transition_to_change.move_name = new_name
                return

    def set_duration(self, img_out, img_in, move_name, duration):
        """
        This method set a new value for the duration
        """
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                for t in phue.transitions:
                    if t.move_name == move_name:
                        t.duration = duration

    def swap_move_color(self, image_out, image_in, move_name):
        """
        This method swap the value for the color
        """
        for phue in self.all_phue:
            if phue.image_out == image_out and phue.image_in == image_in:
                for t in phue.transitions:
                    if t.move_name == move_name:
                        if t.state == "TurnOn":
                            t.state = "TurnOff"
                        else:
                            t.state = "TurnOn"
                        return

    def set_phue_len(self, img_out, img_in, length):
        """
        This method set a new value for the phue length
        """
        for phue in self.all_phue:
            if phue.image_out == img_out and phue.image_in == img_in:
                phue.length = length
                return

    # ============================== Logic ============================== #
    def reset_phue_model(self):
        """
        This method clear all the data in the model
        """
        self.all_phue.clear()
        self.phue_paths.clear()

    def rename_move(self, old_name, new_name):
        """
        This method rename a move in all the cells
        """
        for phue in self.all_phue:
            for transition in phue.transitions:
                if transition.move_name == old_name:
                    transition.move_name = new_name

    def is_names_valid(self):
        """
        This method check if the names contain "-"
        """
        for phue in self.all_phue:
            for transition in phue.transitions:
                if transition.move_name == "-":
                    return False
        return True
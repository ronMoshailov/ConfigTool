from ui.min_green_layout import MinGreenLayout
from ui.set_move_layout import SetMoveLayout

class UIController:
    def __init__(self):
        """
        This method runs when the object initialized.
        """
        self.set_move_layout = SetMoveLayout()
        self.min_green_layout = MinGreenLayout()

    # =============== get methods =============== #
    def get_set_move_layout(self):
        return self.set_move_layout

    def get_min_green_layout(self):
        return self.min_green_layout

    # =============== SetMove methods =============== #
    def show_set_move_layout(self, moves_list):
        print("show_set_move_layout")
        self.set_move_layout.show_panel(moves_list)

    # def add_move(self, move_list):
    #     print("show_set_move_layout")
    #     self.set_move_layout.show_panel(move_list)

    # =============== MinGreen methods =============== #
    def show_min_green_layout(self, moves_list):
        self.min_green_layout.show_panel(moves_list)

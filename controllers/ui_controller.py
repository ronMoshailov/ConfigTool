from ui.min_green_layout import MinGreenLayout
from ui.set_move_layout import SetMoveLayout

class UIController:
    def __init__(self, add_move_ref):
        """
        This method runs when the object initialized.
        """
        self.set_move_layout = SetMoveLayout(add_move_ref)
        self.min_green_layout = MinGreenLayout()



    # =============== SetMove methods =============== #
    def get_set_move_layout(self):
        """
        This method returns the 'set move' layout.

        :return: Returns the 'set move' layout.
        """
        return self.set_move_layout

    def show_set_move_layout(self, moves_list):
        """
        This method shows the 'set move' layout.

        :param moves_list: List of all moves.
        :return: None
        """
        self.set_move_layout.show_panel(moves_list)



    # =============== MinGreen methods =============== #
    def get_min_green_layout(self):
        """
        This method returns the 'min green' layout.

        :return: Returns the 'min green' layout.
        """
        return self.min_green_layout

    def show_min_green_layout(self, moves_list):
        """
        This method shows the 'min green' layout.

        :param moves_list: List of all moves.
        :return: None
        """
        self.min_green_layout.show_panel(moves_list)

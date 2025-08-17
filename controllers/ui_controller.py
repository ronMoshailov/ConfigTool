from ui.matrix_window import Matrix
from ui.min_green_layout import MinGreenLayout
from ui.set_move_layout import SetMoveLayout
from ui.sk_layout import SkLayout


class UIController:
    _instance = None

    # =========================================== #
    #                Construction                 #
    # =========================================== #
    def __new__(cls):
        """
        This method runs before __init__ when new instance is created.
        """
        if cls._instance is None:                                       # checks if there is an instance of the class
            cls._instance = super(UIController, cls).__new__(cls)       # create new instance and store him in _instance before __init__
            cls._instance.__init__()                                    # run _init
            print("** UI controller was set successfully")
        return cls._instance                                            # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        self.set_move_layout = SetMoveLayout()
        self.min_green_layout = MinGreenLayout()
        self.matrix_layout = Matrix()
        self.sk_layout = SkLayout()


    # =========================================== #
    #                 add methods                 #
    # =========================================== #


    # =========================================== #
    #                 get methods                 #
    # =========================================== #
    def get_set_move_layout(self):
        """
        This method returns the 'set move' layout.

        :return: Returns the 'set move' layout.
        """
        return self.set_move_layout

    def get_min_green_layout(self):
        """
        This method returns the 'min green' layout.

        :return: Returns the 'min green' layout.
        """
        return self.min_green_layout

    def get_matrix_layout(self):
        """
        This method returns the 'matrix' layout.

        :return: Returns the 'matrix' layout.
        """
        return self.matrix_layout

    def get_sk_layout(self):
        """
        This method returns the 'sk' layout.

        :return: Returns the 'sk' layout.
        """
        return self.sk_layout


    # =========================================== #
    #               update methods                #
    # =========================================== #


    # =========================================== #
    #               remove methods                #
    # =========================================== #


    # =========================================== #
    #               general methods               #
    # =========================================== #
    def show_set_move_layout(self):
        """
        This method shows the 'set move' layout.

        :return: None
        """
        self.matrix_layout.hide()
        self.min_green_layout.hide()
        self.sk_layout.hide()
        self.set_move_layout.show_panel()

    def show_min_green_layout(self):
        """
        This method shows the 'min green' layout.

        :return: None
        """
        self.matrix_layout.hide()
        self.set_move_layout.hide()
        self.sk_layout.hide()
        self.min_green_layout.show_panel()

    def show_matrix_layout(self):
        """
        This method shows the 'matrix' layout.

        :return: None
        """
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.sk_layout.hide()
        self.matrix_layout.show_panel()

    def show_sk_layout(self):
        """
        This method shows the 'sk' layout.

        :return: None
        """
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.matrix_layout.hide()
        self.sk_layout.show_layout()


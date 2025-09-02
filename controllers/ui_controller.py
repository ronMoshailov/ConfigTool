from ui.detector_panel import DetectorPanel
from ui.image_panel import ImagePanel
from ui.matrix_panel import MatrixPanel
from ui.min_green_panel import MinGreenLayout
from ui.schedule_panel import SchedulePanel
from ui.set_move_panel import SetMovePanel
from ui.sk_panel import SkPanel


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
            cls.set_move_layout = SetMovePanel()
            cls.min_green_layout = MinGreenLayout()
            cls.matrix_layout = MatrixPanel()
            cls.sk_layout = SkPanel()
            cls.detector_panel = DetectorPanel()
            cls.schedule_panel = SchedulePanel()
            cls.image_panel = ImagePanel()

            print("** UI controller was set successfully")
        return cls._instance                                            # return _instance

    def __init__(self):
        """
        This method runs when the object initialized.
        """
        pass

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

    def get_detector_panel(self):
        """
        This method returns the 'sk' layout.

        :return: Returns the 'sk' layout.
        """
        return self.detector_panel

    def get_schedule_panel(self):
        """
        This method returns the 'sk' layout.

        :return: Returns the 'sk' layout.
        """
        return self.schedule_panel

    def get_image_panel(self):
        """
        This method returns the 'sk' layout.

        :return: Returns the 'sk' layout.
        """
        return self.image_panel

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
        self.detector_panel.hide()
        self.schedule_panel.hide()
        self.image_panel.hide()
        self.set_move_layout.show_panel()

    def show_min_green_layout(self):
        """
        This method shows the 'min green' layout.

        :return: None
        """
        self.matrix_layout.hide()
        self.set_move_layout.hide()
        self.sk_layout.hide()
        self.detector_panel.hide()
        self.schedule_panel.hide()
        self.image_panel.hide()
        self.min_green_layout.show_panel()

    def show_matrix_layout(self):
        """
        This method shows the 'matrix' layout.

        :return: None
        """
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.sk_layout.hide()
        self.detector_panel.hide()
        self.schedule_panel.hide()
        self.image_panel.hide()
        self.matrix_layout.show_panel()

    def show_sk_layout(self):
        """
        This method shows the 'sk' layout.

        :return: None
        """
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.matrix_layout.hide()
        self.detector_panel.hide()
        self.schedule_panel.hide()
        self.image_panel.hide()
        self.sk_layout.show_panel()

    def show_detector_panel(self):
        """
        This method shows the 'sk' layout.

        :return: None
        """
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.matrix_layout.hide()
        self.sk_layout.hide()
        self.schedule_panel.hide()
        self.image_panel.hide()
        self.detector_panel.show_panel()

    def show_schedule_panel(self):
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.matrix_layout.hide()
        self.sk_layout.hide()
        self.detector_panel.hide()
        self.image_panel.hide()
        self.schedule_panel.show_panel()

    def show_image_panel(self):
        self.min_green_layout.hide()
        self.set_move_layout.hide()
        self.matrix_layout.hide()
        self.sk_layout.hide()
        self.detector_panel.hide()
        self.schedule_panel.hide()
        self.image_panel.show_panel()

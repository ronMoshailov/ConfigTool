class DisplayManager:
    def __init__(self):
        """
        Class that manages all the display & hide of the views.
        Each controller need to have the methods "show_view" and "hide_view"
        """
        self.views = {}

    def register(self, name, controller):
        """
        This method registers a view to the dictionary of the class.

        :param name: easy name for the view
        :param controller: controller of the view
        """
        self.views[name] = controller

    def show(self, name, *args):
        """
        This method shows the view with the given name.
        If the view is not exit nothing is shown.

        :param name: Easy name of the view
        :param args: Necessary data from different controllers
        """
        self.hide_all()

        controller = self.views.get(name)
        if controller:
            controller.show_view(*args)

    def hide_all(self):
        """
        This method hides all views.
        """
        for controller in self.views.values():
            controller.hide_view()


class DisplayManager:
    def __init__(self):
        self.views = {}

    def register(self, name, controller):
        self.views[name] = controller

    def show(self, name, *args):
        self.hide_all()

        controller = self.views.get(name)
        if controller:
            controller.show_view(*args)

    def hide_all(self):
        for controller in self.views.values():
            controller.hide_view()


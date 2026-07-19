from View.base_view import BaseView


class MainView(BaseView):
    def __init__(self, all_views):
        super().__init__()

        root_layout = self.create_h_layout(all_views.values(), is_centered=True)
        self.setLayout(root_layout)

    def show_view(self):
        self.show()


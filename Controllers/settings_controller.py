from PyQt6.QtWidgets import QWidget


class SettingsController(QWidget):

    def __init__(self, view, model):
        super().__init__()

        self.view = view
        self.model = model

        # =============== Controller Methods =============== #
        self.view.update_settings_method = self.update_model
        self.view.change_state_method = self.change_state
        self.view.make_checkable_method = self.make_checkable

    def show_view(self):
        self.view.show_view(self.model.programmer_name, self.model.junction_num, self.model.junction_name, self.model.version, self.model.date, self.model.first_ext)

    def hide_view(self):
        self.view.show_view()

    def update_model(self, programmer_name, junction_num, junction_name, version, date, first_ext):
        self.model.programmer_name = programmer_name
        self.model.junction_num = junction_num
        self.model.junction_name = junction_name
        self.model.version = version
        self.model.date = date
        self.model.first_ext = first_ext

    def change_state(self, btn):
        selected_btn = self.sender()  # מחזיר את ה־QPushButton שירה את הסיגנל

        if selected_btn.isChecked():
            btn.setDisabled(True)
        else:
            btn.setDisabled(False)

    def make_checkable(self, btn_list):
        for btn in btn_list:
            btn.setCheckable(True)
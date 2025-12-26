from PyQt6.QtWidgets import QMessageBox


class MoveController:
    """
    This class represents a traffic signal group.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.add_move_method = self.add_move
        self.view.remove_move_method = self.remove_move

    def show_view(self):
        self.view.show_view(self.model.all_moves)

    def add_move(self, move_name, move_type, is_main, min_green):
        # check if the name is empty
        if move_name.strip() == '':
            QMessageBox.critical(self.view, "שגיאה", f"שם ריק")
            return


        # check if move name contain numbers and digits
        if any(c.isalpha() for c in move_name) and any(c.isdigit() for c in move_name):
            QMessageBox.critical(self.view, "שגיאה", f"שם המופע לא תקין [{move_name}]")
            return

        # fix 'name' depend on 'type'
        if move_type == "Traffic" or move_type == "Traffic_Flashing":
            move_name = "k" + move_name
        elif move_type == "Pedestrian":
            move_name = "p" + move_name
        elif move_type == "Blinker" or move_type == "Blinker_Conditional":
            move_name = "B" + move_name

        if not self.model.add_move(move_name, move_type, is_main, min_green):
            QMessageBox.critical(self.view, "שגיאה", "מופע כבר קיים במערכת")
            return
        self.show_view()

    def remove_move(self, move_name):
        if self.model.remove_move(move_name):
            self.show_view()
            return
        QMessageBox.critical(self.view, "שגיאה", "שגיאה שלא אמורה להתרחש")




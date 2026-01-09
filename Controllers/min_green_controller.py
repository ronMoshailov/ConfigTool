from PyQt6.QtWidgets import QMessageBox


class MinGreenController:
    """
    This class represents controller of a minimum green time.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.update_method = self.update_min_green

    def show_view(self):
        self.view.show_view(self.model.all_moves)

    ####################################################################################
    #                                     CRUD                                         #
    ####################################################################################
    def update_min_green(self, min_green_dict):
        for key, value in min_green_dict.items():
            if not self.model.update_min_green(key, value.text()):
                QMessageBox.critical(self.view, "שגיאה", "שגיאה שלא אמורה להתרחש")
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    ####################################################################################
    #                           Write to file                                          #
    ####################################################################################


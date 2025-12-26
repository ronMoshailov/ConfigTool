from PyQt6.QtWidgets import QCheckBox, QMessageBox


class ImageController:
    def __init__(self, view, model):
        self.view = view
        self.model = model
        self.all_moves = None

        self.view.add_image_method = self.add_image
        self.view.remove_image_method = self.remove_image
        self.view.update_image_method = self.update_image

    def show_view(self, all_moves):
        self.all_moves = all_moves
        self.view.show_view(self.model.all_images, self.all_moves)

    def add_image(self, name, num, skeleton, sp):
        name = name.capitalize()
        if not self.model.new_image(name, num, skeleton, sp):
            QMessageBox.critical(self.view, "שגיאה", "התמונה כבר קיימת במערכת")
        self.show_view(self.all_moves)

    def remove_image(self, name):
        self.model.remove_image(name)
        self.show_view(self.all_moves)

    def update_image(self, table_dict):
        for image_name, table in table_dict.items():
            skeleton_num = int(table.skeleton_textbox.text())
            image_number = int(table.image_number.text())
            results = []
            for row in range(table.rowCount()):
                container = table.cellWidget(row, 1)
                if not container:
                    continue

                checkbox = container.findChild(QCheckBox)
                if checkbox and checkbox.isChecked():
                    label = table.cellWidget(row, 0)
                    if label:
                        for move in self.all_moves:
                            if move.name == label.text():
                                results.append(move)
                                break
            self.model.update_image(image_name, skeleton_num, image_number, results)
        self.show_view(self.all_moves)
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

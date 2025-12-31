import re

from PyQt6.QtWidgets import QCheckBox, QMessageBox

from Config.patterns import image_pattern


class ImageController:
    def __init__(self, view, model):
        self.view = view
        self.model = model
        self.all_moves = None

        self.view.add_image_method = self.add_image
        self.view.remove_image_method = self.remove_image
        self.view.update_image_method = self.update_image

    def init_model(self, path):
        pattern = image_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                m = pattern.search(line)
                if m:
                    image_name = m.group(1)
                    image_num = m.group(2).strip()
                    image_skeleton = m.group(3).strip()
                    image_sp = m.group(4).strip()
                    is_police = True if m.group(5).strip() == 'true' else False
                    moves_raw = m.group(6)
                    image_moves = re.findall(r'tk\.([A-Za-z0-9_]+)', moves_raw)

                    if image_name == 'A':
                        image_num = 10
                        image_skeleton = re.search(r'\{([^}]*)\}', m.group(3)).group(1).strip()
                        image_sp = 0

                    all_moves = self.model.all_moves
                    collection = []

                    for move in all_moves:
                        if move.name in image_moves:
                            collection.append(move)
                    self.model.new_image(image_name, image_num, image_skeleton, image_sp, is_police, collection)
                    # self.image_model.new_image(image_name, image_num, image_skeleton, image_sp, is_police)

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

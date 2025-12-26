from PyQt6.QtWidgets import QWidget, QMessageBox


class PhueController:

    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.add_phue_method = self.add_phue
        self.view.remove_phue_method = self.remove_phue
        self.view.update_phue_method = self.update_phue

        self.all_images = None
        self.all_moves = None

    def add_phue(self, img_out, img_in):
        if img_out == img_in:
            QMessageBox.critical(self.view, "שגיאה", f"מעבר לא תקין [{img_out} -> {img_in}]")
            return
        if not self.model.new_phue(img_out, img_in, 0, []):
            QMessageBox.critical(self.view, "שגיאה", "המעבר כבר קיים במערכת")
            return
        self.show_view(self.all_images, self.all_moves)

    def remove_phue(self, img_out, img_in):
        self.model.remove_phue(img_out, img_in)
        self.show_view(self.all_images, self.all_moves)

    def update_phue(self, table_wrap_list):
    # check if move exist twice
        for wrap in table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            table = wrap.table

            seen = set()  # מאגר של ערכים שכבר הופיעו
            row_count = table.rowCount()
            for row in range(row_count):
                combo = table.cellWidget(row, 0)  # נניח שזה QComboBox
                value = combo.currentText()  # הערך הנבחר
                if value in seen:
                    QMessageBox.critical(self.view, "שגיאה", f"המופע [{value}] מופיע לפחות פעמיים במעבר [{img_out} → {img_in}]")
                    return
                seen.add(value)

        for wrap in table_wrap_list:
            img_out = wrap.img_out
            img_in = wrap.img_in
            table = wrap.table
            row_count = table.rowCount()
            length = wrap.len_textbox.text()

            move_name_list = []
            color_list = []
            time_list = []

            for row in range(row_count):
                # move_name
                combo = table.cellWidget(row, 0)
                move_name = combo.currentText()  # הערך הנבחר
                move_name_list.append(move_name)

                # color
                color_mapping = {"🔴": "ROT", "🟢": "GRUEN"}
                color = color_mapping[table.item(row, 1).text()]
                color_list.append(color)

                time = table.item(row, 2).text()
                time_list.append(time)

            self.model.update_phue(img_out, img_in, length, move_name_list, color_list, time_list)

        # update the DB
        # success, message = self.data_controller.update_inter_stage(self.table_wrap_list)
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    def show_view(self, all_images, all_moves):
        self.all_images = all_images
        self.all_moves = all_moves
        self.view.show_view(self.model.all_phue, self.all_images, self.all_moves)

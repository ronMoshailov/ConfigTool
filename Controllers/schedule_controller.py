from PyQt6.QtWidgets import QMessageBox


class ScheduleController:
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.get_all_channels_method = self.get_all_channels
        self.view.remove_row_method = self.remove_row
        self.view.add_row_method = self.add_row
        self.view.update_schedule_method = self.update_schedule


    def show_view(self):
        self.view.show_view()

    def get_all_channels(self, table_num):
        return self.model.get_all_channels(table_num)

    def remove_row(self, table_number, table, btn):
        for row_index in range(table.rowCount()):
            if table.cellWidget(row_index, 0) is btn:
                self.model.remove_row(table_number, row_index)
        self.show_view()

    def add_row(self, table_number):
        self.model.add_row(table_number)
        self.show_view()

    def update_schedule(self, is_copy_sunday, table_list):
        if not self._check_time(table_list):
            return
        self.model.update_schedule(is_copy_sunday)
        self.show_view()
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    def _check_time(self, table_list):
        for idx, table in enumerate (table_list):      # for each table

            prev = None                     # reset
            current = None                  # reset
            row_count = table.rowCount()    # get row count

            for row in range(row_count):                    # for each row in table
                prev = current
                current = table.cellWidget(row, 1).time()

                # if first iteration or if last iteration
                if prev is None or current is None:
                    continue

                # compare
                if current <= prev:
                    QMessageBox.critical(self.view, "שגיאה", f"יש בעיה עם הזמנים בטבלה מספר {idx + 1}")
                    return False
        return True

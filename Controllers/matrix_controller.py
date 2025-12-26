from PyQt6.QtWidgets import QMessageBox

from Config.patterns import matrix_pattern


class MatrixController:
    """
    This class represents a matrix group.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.update_method = self.update_matrix

    def show_view(self, all_moves):
        self.view.show_view(all_moves, self.model.all_cells)

    def update_matrix(self, changed_cells):
        self.model.update_matrix(changed_cells)
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    def write_to_file(self, path):
        is_found = False

        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        new_lines = []
        for line in lines:
            line = line.strip() # ignore what after //, split maximum 1 time

            if matrix_pattern.match(line):
                if not is_found:
                    is_found = True
                    self.add_new_lines(new_lines)
                continue

            new_lines.append(line + "\n")

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)

    def add_new_lines(self, new_lines):
        for cell in self.model.all_cells:
            move_out = cell.move_out
            move_in = cell.move_in
            wait_time = cell.wait_time
            opposite_wait_time = None

            for c in self.model.all_cells:
                if move_in == c.move_out and move_out == c.move_in:
                    opposite_wait_time = c.wait_time
                    break

            line = f"\ttk.zwz.setzeZwz( tk.{move_out}\t, tk.{move_in}\t,\t{wait_time}\t,\t{opposite_wait_time}\t);"
            new_lines.append(line + "\n")

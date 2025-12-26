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

            if matrix_pattern.match(line.strip()):
                if not is_found:
                    is_found = True
                    self.add_new_lines(new_lines)
                continue

            new_lines.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)

    def add_new_lines(self, new_lines):
        tuple_list = []
        for cell in self.model.all_cells:
            move_out = cell.move_out
            move_in = cell.move_in
            wait_time = cell.wait_time
            opposite_wait_time = None
            spaces_1 = " " * max(1, 5 - len(move_out))
            spaces_2 = " " * max(1, 6 - len(move_in))
            spaces_3 = " " * max(1, 6 - len(str(wait_time)))


            for c in self.model.all_cells:
                if (c.move_out, c.move_in) in tuple_list or (c.move_in, c.move_out) in tuple_list:
                    continue

                if move_in == c.move_out and move_out == c.move_in:
                    opposite_wait_time = c.wait_time
                    spaces_4 = " " * max(1, 4 - len(str(opposite_wait_time)))
                    break

            line = f"\t\ttk.zwz.setzeZwz( tk.{move_out}{spaces_1}, tk.{move_in}{spaces_2},    {wait_time}{spaces_3},    {opposite_wait_time}{spaces_4});"
            tuple_list.append((move_out, move_in))
            new_lines.append(line + "\n")

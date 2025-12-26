class MatrixCell:
    def __init__(self, move_out, move_in, wait_time):
        self.move_out = move_out
        self.move_in = move_in
        self.wait_time = wait_time


class MatrixModel:
    def __init__(self):
        self.all_cells = []

    def new_cell(self, move_out, move_in, wait_time):
        cell = MatrixCell(move_out, move_in, wait_time)
        self.all_cells.append(cell)

    def update_matrix(self, changed_cells):
        is_found = False

        for row_name, col_name, value in changed_cells:
            if value == "":
                for i, cell in enumerate(self.all_cells):
                    if row_name == cell.move_out and col_name == cell.move_in:
                        del self.all_cells[i]
                        break
                continue
            for cell in self.all_cells:
                if row_name == cell.move_out and col_name == cell.move_in:
                    cell.wait_time = value
                    is_found = True
                    break
            if not is_found:
                self.all_cells.append(MatrixCell(row_name, col_name, value))
            is_found = False

        return True

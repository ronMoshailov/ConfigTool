class _MatrixCell:
    def __init__(self, move_out: str, move_in: str, wait_time: int):
        self.move_out = move_out
        self.move_in = move_in
        self.wait_time = wait_time


class MatrixModel:
    def __init__(self):
        self.all_cells = []

    # ============================== CRUD ============================== #
    def new_cell(self, move_out, move_in, wait_time):
        cell = _MatrixCell(move_out, move_in, wait_time)
        self.all_cells.append(cell)

    def update_matrix(self, out_name, in_name, val):
        for cell in self.all_cells:
            if cell.move_out == out_name and cell.move_in== in_name:
                cell.wait_time = val
                return
        self.all_cells.append(_MatrixCell(out_name, in_name, val))

    def rename_move(self, old_name, new_name):
        for cell in self.all_cells:
            if cell.move_in == old_name:
                cell.move_in = new_name
            if cell.move_out == old_name:
                cell.move_out = new_name
        if new_name.startswith('p'):
            self.clear_cells()

    def remove_move(self, move_name):
        # O(n)
        self.all_cells = [cell for cell in self.all_cells if cell.move_out != move_name and cell.move_in != move_name]

    def clear_cells(self):
        # O(n)
        self.all_cells = [cell for cell in self.all_cells if not (cell.move_out.startswith("p") and cell.move_in.startswith("p"))]

    # ============================== Logic ============================== #
    def reset(self):
        self.all_cells.clear()

    def is_cell_exist(self, name_out, name_in):
        for cell in self.all_cells:
            if name_out == cell.move_in and name_in == cell.move_out:
                return True
        return False


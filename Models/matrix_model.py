class MatrixCell:
    def __init__(self, move_out: str, move_in: str, wait_time: int):
        self.move_out = move_out
        self.move_in = move_in
        self.wait_time = wait_time


class MatrixModel:
    def __init__(self):
        self.all_cells = []

    def new_cell(self, move_out, move_in, wait_time):
        """
        This method creates a new matrix cell.

        :param move_out: Move name out
        :param move_in: Move name in
        :param wait_time: Waiting time between moves
        """
        cell = MatrixCell(move_out, move_in, wait_time)
        self.all_cells.append(cell)

    def update_matrix(self, out_name, in_name, val):
        for cell in self.all_cells:
            if cell.move_out == out_name and cell.move_in== in_name:
                cell.wait_time = val
                return
        self.all_cells.append(MatrixCell(out_name, in_name, val))

    def reset(self):
        self.all_cells.clear()

    def is_cell_exist(self, name_out, name_in):
        for cell in self.all_cells:
            if name_out == cell.move_in and name_in == cell.move_out:
                return True
        return False

    def update_names(self, old_name, new_name):
        for cell in self.all_cells:
            if cell.move_in == old_name:
                cell.move_in = new_name
            if cell.move_out == old_name:
                cell.move_out = new_name

    def remove_move(self, move_name):
        # O(n)
        self.all_cells = [cell for cell in self.all_cells if cell.move_out != move_name and cell.move_in != move_name]
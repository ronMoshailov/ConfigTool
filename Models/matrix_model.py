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
        """
        This method create new cell to the model
        """
        cell = _MatrixCell(move_out, move_in, wait_time)
        self.all_cells.append(cell)

    def set_wait_time(self, out_name, in_name, val):
        """
        This method set new value to cell
        """
        for cell in self.all_cells:
            if cell.move_out == out_name and cell.move_in == in_name:
                cell.wait_time = val
                return
        self.new_cell(out_name, in_name, val)

    def rename_move(self, old_name, new_name):
        """
        This method rename move name in all cells
        """
        for cell in self.all_cells:
            if cell.move_in == old_name:
                cell.move_in = new_name
            if cell.move_out == old_name:
                cell.move_out = new_name
        if new_name.startswith('p') or new_name.startswith('B'):
            self._clear_cells()

    def remove_move(self, move_name):
        """
        This method remove all cells that related to the move
        """
        # O(n)
        self.all_cells = [cell for cell in self.all_cells if cell.move_out != move_name and cell.move_in != move_name]

    def remove_cell(self, out_name, in_name):
        """
        This method removed the cell and the opposite cell
        """
        self.all_cells = [cell for cell in self.all_cells if not ((cell.move_out == out_name and cell.move_in == in_name) or (cell.move_out == in_name and cell.move_in == out_name))]


    def _clear_cells(self):
        """
        This method clear all values from pedestrian and blinkers
        """
        # O(n)
        self.all_cells = [cell for cell in self.all_cells if not (cell.move_out.startswith("p") and cell.move_in.startswith("p"))]
        self.all_cells = [cell for cell in self.all_cells if not (cell.move_out.startswith("B") or cell.move_in.startswith("B"))]

    # ============================== Logic ============================== #
    def reset_matrix_model(self):
        """
        This method clear all the data in the model
        """
        self.all_cells.clear()


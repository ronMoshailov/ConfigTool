from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class MatrixController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

        # Set View Methods
        self.view.update_wait_time_method   = self.update_wait_time
        self.view.remove_cell_method        = self.remove_cell

        # Set Main Controller Methods
        self.get_move_type = None

        # Data
        self.all_moves = None

    def init_model(self, path):
        """
        This method initialize the matrix model
        """
        all_cells = LoadDataManager.load_matrix_data(path)
        for move_out, move_in, t1, t2 in all_cells:
            self.model.new_cell(move_out, move_in, t1)
            self.model.new_cell(move_in, move_out, t2)

    def show_view(self, all_moves):
        """
        This method show the view
        """
        self.all_moves = all_moves
        self.view.show_view(self.all_moves, self.model.all_cells)

    def hide_view(self):
        """
        This method hide the view
        """
        self.view.hide_view()

    # ============================== CRUD ============================== #
    def update_wait_time(self, out_name, in_name, val):
        """
        This method update the cell
        """
        if not val.isdigit():
            self.view.show_error("הערך במטריצה חייב להיות מספר")
            self.show_view(self.all_moves)
        else:
            self.model.set_wait_time(out_name, in_name, val)

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move in all the cells
        """
        self.model.rename_move(old_name, new_name)

    def remove_move(self, move_name):
        # Used by Move Controller
        """
        This method remove all cells that related to the move
        """
        self.model.remove_move(move_name)

    def remove_cell(self, out_name, in_name):
        self.model.remove_cell(out_name, in_name)
        self.show_view(self.all_moves)

    # ============================== Logic ============================== #
    def is_matrix_valid(self):
        # Used by Main Controller
        """
        This method check if the matrix has valid logic
        * if (x,y) has value so the method check if (y,x) has also value

        Used by Main Controller before writing to project
        """
        # Get cell's moves
        pairs = {(cell.move_out, cell.move_in) for cell in self.model.all_cells}

        # Check if the cells valid
        for move_out, move_in in pairs:
            if (move_in, move_out) not in pairs:
                self.view.show_error("הודעה", f"חסר תא במטריצה ב-[{move_in}, {move_out}]")
                return False
        return True

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data from the model
        """
        self.model.reset_matrix_model()

    # ============================== Write To File ============================== #
    def write_matrix_to_file(self, path_init_tk1):
        """
        This method write the data from the model to the project
        """
        code = WriteDataManager.create_matrix_init_tk1_code(path_init_tk1, self.model.all_cells, self.get_move_type)
        WriteDataManager.write_code(path_init_tk1, code)


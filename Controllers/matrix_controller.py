import Config

from PyQt6.QtWidgets import QMessageBox

class MatrixController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

        # Set View Methods
        self.view.update_wait_time_method = self.update_wait_time

        # Set Main Controller Methods
        self.get_move_type = None

    def init_model(self, path):
        # path: initTk1.java
        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                line = line.split("//", 1)[0].strip() # ignore what after //, split maximum 1 time
                if not line:
                    continue

                match = Config.patterns.matrix_pattern.match(line)
                if match:
                    move_out, move_in, t1, t2 = match.groups()

                    # Add cells to the model
                    self.model.new_cell(move_out, move_in, t1)
                    self.model.new_cell(move_in, move_out, t2)

    def show_view(self, all_moves):
        self.view.show_view(all_moves, self.model.all_cells)

    def hide_view(self):
        self.view.show_view()

    # ============================== CRUD ============================== #
    def update_wait_time(self, out_name, in_name, val):
        """
        This method update the cell
        """
        self.model.set_wait_time(out_name, in_name, int(val))

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
        # self.model.all_cells = [cell for cell in self.model.all_cells if cell.move_in != move_name and cell.move_out != move_name]

    # ============================== Logic ============================== #
    def is_matrix_valid(self):
        """
        This method check if the matrix has valid logic
        * if (x,y) has value so the method check if (y,x) has also value
        """
        # Get cell's moves
        pairs = {(cell.move_out, cell.move_in) for cell in self.model.all_cells}

        # Check if the cells valid
        for move_out, move_in in pairs:
            if (move_in, move_out) not in pairs:
                QMessageBox.critical(self.view, "הודעה", f"חסר תא במטריצה ב-[{move_in}, {move_out}]")
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
        code = []

        self._create_code(path_init_tk1, code)
        self._write_code(path_init_tk1, code)

    def _create_code(self, path, code):
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write matrix here" in line:
                    tuple_list = []
                    line = ""
                    all_cells = self.model.all_cells
                    prev_start = all_cells[0].move_out

                    for cell in all_cells:
                        if (cell.move_out, cell.move_in) in tuple_list:
                            continue
                        if prev_start != cell.move_out:
                            code.append("\n")
                        line += "\t\ttk.zwz.setzeZwz( "
                        line += f"tk.{cell.move_out}"
                        line += " " * (26 - len(line))  # add spaces
                        line += f", tk.{cell.move_in}"
                        line += " " * (37 - len(line))  # add spaces
                        line += f", "

                        time = int(cell.wait_time)
                        if self.get_move_type(cell.move_out) == "Traffic_Flashing":
                            time += 3

                        if time >= 10:
                            line += f"   {time}"
                        else:
                            line += f"    {time}"
                        line += "    ,  "

                        # add opposite
                        for c in all_cells:
                            if c.move_in == cell.move_out and c.move_out == cell.move_in:
                                time = int(c.wait_time)
                                if self.get_move_type(c.move_out) == "Traffic_Flashing":
                                    time += 3
                                if time >= 10:
                                    line += f"{time}"
                                else:
                                    line += f" {time}"
                                tuple_list.append((c.move_out, c.move_in))
                                break
                        line += " );\n"

                        code.append(line)
                        prev_start = cell.move_out
                        line = ""
                    continue
                code.append(line)

    def _write_code(self, path, code):
        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

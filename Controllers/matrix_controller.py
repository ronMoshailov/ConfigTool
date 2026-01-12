from PyQt6.QtWidgets import QMessageBox

from Config.patterns import matrix_pattern

class MatrixController:
    """
    This class represents a matrix controller.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.update_method = self.update_matrix

    def show_view(self, all_moves):
        self.view.show_view(all_moves, self.model.all_cells)


    ####################################################################################
    #                                     CRUD                                         #
    ####################################################################################
    def update_matrix(self, out_name, in_name, val):
        self.model.update_matrix(out_name, in_name, int(val))
        QMessageBox.information(self.view, "הודעה", "העדכון הצליח")

    def remove_from_matrix(self, move_name):
        self.model.all_cells = [cell for cell in self.model.all_cells if cell.move_in != move_name and cell.move_out != move_name]

    ####################################################################################
    #                           Write to file                                          #
    ####################################################################################
    def write_to_file(self, path):
        # data
        code = []

        # update file
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write matrix here" in line:
                self.add_new_lines(code)
                continue
            code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def add_new_lines(self, new_lines):
        code = []
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
            line += " " * (26 - len(line))                          # add spaces
            line += f", tk.{cell.move_in}"
            line += " " * (37 - len(line))                          # add spaces
            line += f", "
            if int(cell.wait_time) >= 10:
                line += f"   {cell.wait_time}"
            else:
                line += f"    {cell.wait_time}"
            line += "    ,  "

            # add opposite
            for c in all_cells:
                if c.move_in == cell.move_out and c.move_out == cell.move_in:
                    if int(c.wait_time) >= 10:
                        line += f"{c.wait_time}"
                    else:
                        line += f" {c.wait_time}"
                    tuple_list.append((c.move_out, c.move_in))
                    break
            line += " );\n"

            code.append(line)
            prev_start = cell.move_out
            line = ""

        new_lines.extend(code)

    ####################################################################################
    #                               Logic                                              #
    ####################################################################################
    def init_model(self, path):
        """
        This method set from path the matrix cells in the app.

        :param path: path to "InitTk1.java'
        :return: None
        """
        self.model.reset_model()
        pattern = matrix_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                line = line.split("//", 1)[0].strip() # ignore what after //, split maximum 1 time
                if not line:
                    continue

                match = pattern.search(line)
                if match:
                    out = match.group("out")
                    inn = match.group("inn")
                    t1 = int(match.group("t1"))
                    t2 = int(match.group("t2"))

                    self.model.new_cell(out, inn, t1)
                    self.model.new_cell(inn, out, t2)

    ####################################################################################
    #                               Logic                                              #
    ####################################################################################
    def is_matrix_valid(self):
        pairs = {(cell.move_out, cell.move_in) for cell in self.model.all_cells}

        for move_out, move_in in pairs:
            if (move_in, move_out) not in pairs:
                QMessageBox.critical(self.view, "הודעה", f"חסר תא במטריצה ב-[{move_in}, {move_out}]")
                return False

        return True

    def update_names(self, old_name, new_name):
        self.model.update_names(old_name, new_name)
class WriteDataManager:

    @staticmethod
    def create_settings_init_code(path, model_dict):
        code = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write settings here" in line:
                    code.append(f"\tpublic static String anlagenName = \"{model_dict['junction_num']}\";\n")
                    code.append(f"\tpublic static String tk1Name     = \"{model_dict['junction_name']}\";\n")
                    code.append(f"\tpublic static String version     = \" {model_dict['version']}\";\n")
                    code.append( "\tpublic static String[] versions = {\n")
                    for date, author in model_dict['history']:
                        code.append(f"\t\t\"{date} - {author}\",\n")
                    code[-1] = code[-1][:-2]
                    code.append("\n};\n")
                    continue
                code.append(line)
        return code

    @staticmethod
    def create_moves_tk1_code(path, all_moves_names):
        code = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write moves here" in line:
                    cars_line = "\tpublic Move "
                    pedestrians_line = "\tpublic Move "
                    blinkers_line = "\tpublic Move "
                    moves_dictionary = {"k": [], "p": [], "B": []}

                    for name in all_moves_names:
                        moves_dictionary[name[0]].append(name)

                    for item in moves_dictionary["k"]:
                        cars_line += f"{item}, "
                    cars_line = cars_line[:-2] + ";  // traffic\n"

                    for item in moves_dictionary["p"]:
                        pedestrians_line += f"{item}, "
                    pedestrians_line = pedestrians_line[:-2] + "; // pedestrians\n"

                    for item in moves_dictionary["B"]:
                        blinkers_line += f"{item}, "
                    blinkers_line = blinkers_line[:-2] + ";	// blinkers\n"

                    code.append(cars_line)
                    code.append(pedestrians_line)
                    code.append(blinkers_line)
                    continue
                code.append(line)
        return code

    @staticmethod
    def create_moves_init_tk1_code(path, all_moves, calc_min_green):
        all_moves_names = [m.name for m in all_moves]
        code = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write moves here" in line:
                    car_lines = []
                    pedestrians_lines = []
                    blinkers_lines = []
                    moves_dictionary = {"k": [], "p": [], "B": []}

                    # Get all moves names
                    for name in all_moves_names:
                        moves_dictionary[name[0]].append(name)

                    for move in all_moves:
                        # calc min green
                        min_green = calc_min_green(move)

                        line = ""
                        line += f"\t\ttk.{move.name}"
                        line += " " * (12 - (len(line)))
                        line += f"=  new Move(   tk    , \"_{move.name}\""
                        line += " " * (42 - (len(line)))
                        line += f", MoveType.{move.type}"
                        line += " " * (72 - (len(line)))
                        line += ",     "
                        if min_green >= 10:
                            line += f"{min_green}"
                        else:
                            line += f" {min_green}"
                        line += " ,   0 , "
                        if move.is_main:
                            line += "true "
                        else:
                            line += "false"
                        line += ");\n"

                        if move.name.startswith("k"):
                            car_lines.append(line)
                        elif move.name.startswith("p"):
                            pedestrians_lines.append(line)
                        else:
                            blinkers_lines.append(line)

                    code.extend(car_lines)
                    code.append("\n")
                    code.extend(pedestrians_lines)
                    code.append("\n")
                    code.extend(blinkers_lines)
                    continue
                code.append(line)
        return code

    @staticmethod
    def create_matrix_init_tk1_code(path, all_cells, get_move_type):
        code = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write matrix here" in line:
                    tuple_list = []
                    line = ""
                    all_cells = all_cells
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
                        if get_move_type(cell.move_out) == "Traffic_Flashing":
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
                                if get_move_type(c.move_out) == "Traffic_Flashing":
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
        return code

    @staticmethod
    def write_code(path, code):
        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)


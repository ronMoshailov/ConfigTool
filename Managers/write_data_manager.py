import Config

class WriteDataManager:
    # ============================================================================== #
    # ---------------------------------- Settings ---------------------------------- #
    # ============================================================================== #
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

    # ============================================================================== #
    # ------------------------------------ Moves ----------------------------------- #
    # ============================================================================== #
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

    # ============================================================================== #
    # ----------------------------------- Matrix ----------------------------------- #
    # ============================================================================== #
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

    # ============================================================================== #
    # ------------------------------------- SK ------------------------------------- #
    # ============================================================================== #
    @staticmethod
    def create_sk_init_code(path, sk_list):
        code = []
        # update tk1.java file
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write sk cards here" in line:
                for idx, card in enumerate(sk_list):
                    code.append(f"\t\tSK24 sk{idx+1} = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);\n")

            if "write sk channels here" in line:
                for sk_card in sk_list:
                    card_num = sk_card.card_number
                    all_channels = sk_card.all_channels
                    for channel in all_channels:
                        if not channel.name:
                            continue
                        line = ""
                        color_mapping = {"hwRed200": "lred", "hwAmber200": "lamber", "hwGreen200": "lgreen"}
                        line += f"\t\tnew SchaltKanal(Var.tk1.{channel.name}"
                        line += " " * (33 - len(line))
                        line += f", Move.{color_mapping[channel.color]}"
                        line += " " * (51 - len(line))
                        line += f", {channel.color}"
                        line += " " * (63 - len(line))
                        line += f", Hw.HF, sk{card_num},"
                        line += " " * (33 - len(line))
                        if channel.channel >= 10:
                            line += f"{channel.channel}, Hw.SK);\n"
                        else:
                            line += f" {channel.channel}, Hw.SK);\n"

                        if channel.is_comment:
                            line = "//" + line

                        code.append(line)
                continue

            code.append(line)
        return code

    # ============================================================================== #
    # ---------------------------------- Detectors --------------------------------- #
    # ============================================================================== #
    @staticmethod
    def create_detectors_init_tk1_code(path, all_detectors):
        code = []
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    for detector in all_detectors:
                        line = ""
                        line += f"\t\ttk.{detector.var_name}"                   # add code
                        line += " " * (12 - len(line))                          # add spaces
                        line += f"= new {detector.class_name}"                  # add code
                        line += " " * (28 - len(line))                          # add spaces
                        line += f"( \"{detector.datector_name}\""               # add code
                        line += " " * (38 - len(line))                          # add spaces
                        line += f", tk.{detector.move_name}"                    # add code
                        line += " " * (46 - len(line))                          # add spaces
                        line += ", true    , true        , true );\n"           # add code
                        code.append(line)
                    continue
                code.append(line)
        return code

    @staticmethod
    def create_detectors_tk1_code(path, all_d_detectors, all_e_detectors, all_de_detectors, all_tp_detectors, all_q_detectors):
        code = []

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    # DDETECTOR
                    if len(all_d_detectors) > 0:
                        line = "\t"
                        line += "public DDetector "
                        for detector in all_d_detectors:
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # EDETECTOR
                    if len(all_e_detectors) > 0:
                        line = "\t"
                        line += "public EDetector "
                        for detector in all_e_detectors:
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # DE_DETECTOR
                    if len(all_de_detectors) > 0:
                        line = "\t"
                        line += "public DEDetector "
                        for detector in all_de_detectors:
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # TPDETECTOR
                    if len(all_tp_detectors) > 0:
                        line = "\t"
                        line += "public TPDetector "
                        for detector in all_tp_detectors:
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # QDETECTOR
                    if len(all_q_detectors) > 0:
                        line = "\t"
                        line += "public QDetector "
                        for detector in all_q_detectors:
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)
                    continue
                code.append(line)
        return code

    # ============================================================================== #
    # ---------------------------------- Schedule ---------------------------------- #
    # ============================================================================== #
    @staticmethod
    def create_schedule_init_tk1_code(path, is_copy_sunday, all_schedule_tables):
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        new_lines = []
        for line in lines:
            if "write schedule here" in line:
                first_row = True

                # sunday - thursday
                if is_copy_sunday:
                    table = all_schedule_tables[0]
                    cell_list = table.cell_list  # get schedule cells
                    for cell in cell_list:  #
                        # prepare data
                        if cell.prog_num < 10:
                            prog_num = f"0{cell.prog_num}"
                        else:
                            prog_num = f"{cell.prog_num}"

                        # first row
                        if first_row:
                            first_row = False
                            line = f"\t\tTagesPlan sun_thur = new TagesPlan(\"Sun_Thur\", tk.p{prog_num});\n"
                            new_lines.append(line)
                            continue

                        # all other rows
                        line = f"\t\tsun_thur.initProgWunsch("
                        if cell.hour >= 10:
                            line += f"{cell.hour}, "
                        else:
                            line += f" {cell.hour}, "

                        if cell.minute >= 10:
                            line += f"{cell.minute},  tk.p{prog_num} );\n"
                        else:
                            line += f" {cell.minute},  tk.p{prog_num} );\n"

                        new_lines.append(line)
                    new_lines.append("\n")

                else:
                    for i in range(5):
                        table = all_schedule_tables[i]  # get table
                        cell_list = table.cell_list  # get schedule cells
                        day = {0: "sunday", 1: "monday", 2: "tuesday", 3: "wednesday", 4: "thursday"}.get(i, "לא קיים")
                        for cell in cell_list:  #
                            # prepare data
                            if cell.prog_num < 10:
                                prog_num = f"0{cell.prog_num}"
                            else:
                                prog_num = f"{cell.prog_num}"

                            # first row
                            if first_row:
                                first_row = False
                                line = f"\t\tTagesPlan {day} = new TagesPlan(\"{day}\", tk.p{prog_num});\n"
                                new_lines.append(line)
                                continue

                            # all other rows
                            line = f"\t\t{day}.initProgWunsch("
                            if cell.hour >= 10:
                                line += f"{cell.hour}, "
                            else:
                                line += f" {cell.hour}, "

                            if cell.minute >= 10:
                                line += f"{cell.minute},  tk.p{prog_num} );\n"
                            else:
                                line += f" {cell.minute},  tk.p{prog_num} );\n"
                                print(f"{day}, {prog_num}")
                            new_lines.append(line)
                        new_lines.append("\n")
                        first_row = True

                # friday
                new_lines.append("\n")

                friday_table = all_schedule_tables[5]
                first_row = True
                cell_list = friday_table.cell_list
                for cell in cell_list:
                    # make program number for the line
                    if cell.prog_num < 10:
                        prog_num = f"0{cell.prog_num}"
                    else:
                        prog_num = f"{cell.prog_num}"

                    # if it's the first row
                    if first_row:
                        first_row = False
                        line = f"\t\tTagesPlan fr = new TagesPlan(\"Fr\",  tk.p{prog_num});\n"
                        new_lines.append(line)
                        continue

                    #
                    line = (f"\t\tfr.initProgWunsch("
                            f"{Config.special.get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                            f"{Config.special.get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
                    new_lines.append(line)
                new_lines.append("\n")

                # saturday
                saturday_table = all_schedule_tables[6]
                first_row = True
                cell_list = saturday_table.cell_list
                for cell in cell_list:
                    # make program number for the line
                    if cell.prog_num < 10:
                        prog_num = f"0{cell.prog_num}"
                    else:
                        prog_num = f"{cell.prog_num}"

                    # if it's the first row
                    if first_row:
                        first_row = False
                        line = f"\t\tTagesPlan sa = new TagesPlan(\"Sat\",  tk.p{prog_num});\n"
                        new_lines.append(line)
                        continue

                    #
                    line = (f"\t\tsa.initProgWunsch("
                            f"{Config.special.get_space(0, 1, str(cell.hour))}{cell.hour} ,"
                            f"{Config.special.get_space(1, 2, str(cell.minute))}{cell.minute},  tk.p{prog_num} );\n")
                    new_lines.append(line)
                new_lines.append("\n")
                continue

            if "write schedule order here" in line:
                if is_copy_sunday:
                    new_lines.append("\t\tnew WochenPlan(\"time table\", sun_thur, sun_thur, sun_thur, sun_thur, fr, sa, sun_thur);")
                else:
                    new_lines.append("\t\tnew WochenPlan(\"time table\", monday, tuesday, wednesday, thursday, fr, sa, sunday);")
                continue

            new_lines.append(line)

        return new_lines

    # ============================================================================== #
    # ----------------------------------- Images ----------------------------------- #
    # ============================================================================== #
    @staticmethod
    def create_images_file_code(path, all_images_names):
        for image_name in all_images_names:
            if image_name == "A":
                continue

            line = ""
            line += "package phase;\n"
            line += "\n"
            line += "import vt.*;\n"
            line += "import enums.ExtensionType;\n"
            line += "import special.Move;\n"
            line += "import special.Stage;\n"
            line += f"import {Config.constants.PROJECT_NUMBER}.Tk1;\n"
            line += f"import {Config.constants.PROJECT_NUMBER}.ParametersTelAviv;\n"
            line += f"import tk.Var;\n"
            line += "\n"
            line += f"public class Phase{image_name}"
            line += " extends Stage {\n"
            line += "\tprivate Tk1 node;"
            line += "\n"
            line += "/**\n"
            line += "\t * Constructor for Haifa applications or for Jerusalem preemption applications\n"
            line += "\t * @param node - the node to which the stage refers\n"
            line += "\t * @param name - the name of the stage\n"
            line += "\t * @param number - the number of the stage\n"
            line += "\t * @param length - the minimal (skeleton) length of the stage\n"
            line += "\t * @param isStopInPolice - whether a non-fixed police program should stop in this stage\n"
            line += "\t * @param sgs - list of moves that must open in this stage\n"
            line += "\t */\n"
            line += f"\tpublic Phase{image_name}(Tk1 node, String name, int number, int length, boolean isStopInPolice, Move[] sgs) \n"
            line += "{\n"
            line += "\t\tsuper(node, name, number, length, isStopInPolice, sgs);\n"
            line += "\t\tthis.node = node;\n"
            line += "}\n"
            line += "\n"
            line += "/**\n"
            line += " * Constructor for applications with parameters based on stop-points\n"
            line += " * @param node - the node to which the stage refers\n"
            line += " * @param name - the name of the stage\n"
            line += " * @param number - the number of the stage\n"
            line += " * @param length - the minimal (skeleton) length of the stage\n"
            line += " * @param sp - this stage's stop-point number\n"
            line += " * @param isStopInPolice - whether a non-fixed police program should stop in this stage\n"
            line += " * @param sgs - list of moves that must open in this stage\n"
            line += " */\n"
            line += f"\tpublic Phase{image_name}(Tk1 node, String name, int numberr, int length, int sp, boolean isStopInPolice, Move[] sgs) "
            line += "{\n"
            line += "\t\tsuper(node, name, numberr, length, sp, isStopInPolice, sgs);\n"
            line += "\t\tthis.node = node;\n"
            line += "}\n"
            line += "\n"
            line += "\t/**\n"
            line += "\t * This methods does two things:<br>\n"
            line += "\t * <ol>\n"
            line += "\t * 	<li>\n"
            line += "\t * 		Sets the type of Minimum extension:\n"
            line += "\t * 		<ul>\n"
            line += "\t * 			<li>duration</li>\n"
            line += "\t * 			<li>absolute</li>\n"
            line += "\t * 			<li>complement</li>\n"
            line += "\t * 		</ul>\n"
            line += "\t * 	</li>\n"
            line += "\t * 	<li>Returns the required value of the Minimum extension</li>\n"
            line += "\t * </ol>\n"
            line += "\t * @return returns the Minimum extension value\n"
            line += "\t */\n"
            line += "\tpublic int getMinExt() {\n"
            line += "\t\tminType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;\n"
            line += "\t\treturn ((ParametersTelAviv)Var.controller.dvi35Parameters).getMinimum(spNum);\n"
            line += "\t}\n"
            line += "\n"
            line += "\t/**\n"
            line += "\t * This methods does two things:<br>\n"
            line += "\t * <ol>\n"
            line += "\t * 	<li>\n"
            line += "\t * 		Sets the type of Maximum extension:\n"
            line += "\t * 		<ul>\n"
            line += "\t * 			<li>duration</li>\n"
            line += "\t * 			<li>absolute</li>\n"
            line += "\t * 			<li>complement</li>\n"
            line += "\t * 		</ul>\n"
            line += "\t * 	</li>\n"
            line += "\t * 	<li>Returns the required value of the Maximum extension</li>\n"
            line += "\t * </ol>\n"
            line += "\t * @return returns the Maximum extension value\n"
            line += "\tpublic int getMaxExt() {\n"
            line += "\t\tmaxType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;\n"
            line += "\t\treturn ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum);\n"
            line += "\t}\n"
            line += "\n"
            line += "\t/**\n"
            line += "\t * This method is called when the stage is terminated\n"
            line += "\t */\n"
            line += "\tprotected void deactivated() {\n"
            line += "\t}\n"
            line += "\n"
            line += "\t/**\n"
            line += "\t * This method is called every scan-cycle while the stage is active\n"
            line += "\t */\n"
            line += "\tpublic Phase phasenFunktion() {\n"
            line += "\t\tstopInPolice();\n"
            line += "\n"
            line += "\t\tif (getPhasenZeit() >= node.lenPhue + getPhLenMS()) \n"
            line += "\t\t{\n"
            line += "\t\t\tgaps = true;\n"
            line += "//\t\t\tgaps = node.e_6.IsActive();\n"
            line += "\n"
            line += "\t\t\tisStageDoneFlag = isStageDone(gaps);\n"
            line += "\n"
            line += "\t\t\tif (isStageDoneFlag) \n"
            line += "\t\t\t{\n"
            line += "//\t\t\tif (node.d_8.IsActive())\n"
            line += "//\t\t\t\t\treturn startNextPhase(node.PhueC_D);\n"
            line += "\t\t\t}\n"
            line += "\t\t}\n"
            line += "\t\treturn KEINE_UMSCHALTUNG;\n"
            line += "\t}\n"
            line += "}\n"

            # create file
            file_path = path / f"Phase{image_name}.java"
            with open(file_path, "w", encoding="utf-8") as f:
                f.write(line)

    @staticmethod
    def create_image_tk1_code(path, all_images_names):
        code = []

        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write images here" in line:
                line = "\tpublic Stage "
                for image_name in all_images_names:
                    if image_name == "A":
                        continue
                    line += f"Ph{image_name}, "
                line = line[:-2]
                line += ";\n"
                code.append(line)
                continue
            code.append(line)
        return code

    @staticmethod
    def create_image_init_tk1_code(path_init_tk, all_images):
        code = []

        # update tk1.java file
        with open(path_init_tk, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write image A here" in line:
                for image in all_images:
                    if image.image_name == "A":
                        line = "\t\ttk.MainPhase = tk.PhA = new PhaseA(tk, \"PhaseA\"    , 10  , new int[] "
                        if int(image.skeleton) >= 10:
                            line += "{"
                            line += f"{image.skeleton}"
                            line += " }"
                        else:
                            line += "{ "
                            line += f"{image.skeleton}"
                            line += " }"
                        line += " , new int[] { 0 } , false      , new Move[] {"
                        for move_name in image.move_names_list:
                            line += f"tk.{move_name}, "
                        line = line[:-2] + "});\n"
                        break

            if "write images here" in line:
                all_images = all_images

                for image in all_images:
                    if image.image_name == "A":
                        continue
                    line = "\t\ttk.Ph"  # tk.Ph
                    line += f"{image.image_name}"  # tk.PhEQA
                    line += " " * (17 - len(line))  # add spaces
                    line += f"= new Phase{image.image_name}"  # tk.PhEQA = new PhaseEQA
                    line += " " * (36 - len(line))  # add spaces
                    line += f"(tk, \"Phase{image.image_name}\""  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA"
                    line += " " * (53 - len(line))  # add spaces
                    if int(image.image_num) >= 10:  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11
                        line += f", {image.image_num}  "  #
                    else:  #
                        line += f",  {image.image_num}  "  #
                    if int(image.skeleton) >= 10:
                        line += f",   {image.skeleton} "  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3
                    else:
                        line += f",    {image.skeleton} "  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3
                    line += f",  {image.sp} , "  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3, 1
                    line += f"  {str(image.is_police).lower()}"  # tk.PhEQA = new PhaseEQA (tk, "PhaseEQA", 11, 3, 1, true
                    line += " " * (84 - len(line))  # add spaces
                    line += ", new Move[] {"
                    for move_name in image.move_names_list:
                        line += f"tk.{move_name}, "
                    line = line[:-2] + "});\n"
                    code.append(line)  #
                continue
            code.append(line)
        return code

    # ============================================================================== #
    # ------------------------------------ Phue ------------------------------------ #
    # ============================================================================== #
    @staticmethod
    def create_phue_file_code(all_phue):
        for phue in all_phue:
            line = ""
            line += "package phue;\n"
            line += "\n"
            line += "\n"
            line += "import vt.*;\n"
            line += "import special.InterStage;\n"
            line += f"import {Config.constants.PROJECT_NUMBER}.Tk1;"
            line += "\n"
            line += f"public class Phue{phue.image_out}_{phue.image_in} "
            line += "extends InterStage {\n"
            line += "\tprivate static Tk1 _tk;\n"
            line += f"public Phue{phue.image_out}_{phue.image_in}(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) "
            line += "{\n"
            line += "\t\tsuper(tk, name, laenge, quelle, ziel);\n"
            line += "\t\t_tk = tk;\n"
            line += "\t\t}\n"
            line += "\n"
            line += "\tpublic Phase phasenFunktion() {\n"

            for transition in phue.transitions:
                if transition.state == "TurnOff":
                    line += f"\t\t_tk.{transition.move_name}.TurnOff("
                    if int(transition.duration) >= 10:
                        line += f"{transition.duration});\n"
                    else:
                        line += f" {transition.duration});\n"

            line += "\n"

            for transition in phue.transitions:
                if transition.state == "TurnOn":
                    line += f"\t\t_tk.{transition.move_name}.TurnOn ("
                    if int(transition.duration) >= 10:
                        line += f"{transition.duration});\n"
                    else:
                        line += f" {transition.duration});\n"

            line += "\n"
            line += "\t\tif (isTargetStageBuilt()) {\n"
            line += "\t\t\t_tk.lenPhue = this.getPhasenZeit();\n"
            line += "\t\t\tthis.entfernen();\n"
            line += "\t\t}\n"
            line += "\t\treturn KEINE_UMSCHALTUNG;\n"
            line += "\t}\n"
            line += "}\n"
            line += "\n"
            line += "\n"

            # create file
            file_path = phue_folder_dst / f"Phue{phue.image_out}_{phue.image_in}.java"
            with open(file_path, "w", encoding="utf-8") as f:
                f.write(line)

            return line

    # ============================================================================== #
    # ----------------------------------- General ---------------------------------- #
    # ============================================================================== #
    @staticmethod
    def write_code(path, code):
        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)


import Config

class DetectorController:
    def __init__(self, view, model):
        self.view = view
        self.model = model

        # Set View Methods
        self.view.update_detectors_method = self.update_detectors
        self.view.remove_detector_method = self.remove_row

    def init_model(self, path):
        """
        This method extracts detector declarations from InitTk1.java.

        :param path: path to "InitTk1.java"
        :return: None
        """

        pattern = Config.patterns.detectors_pattern

        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                if line.startswith("//"):
                    continue
                matches = pattern.findall(line)
                for var_name, class_name, detector_name, move_name in matches:
                    self.model.new_detector(var_name=var_name, class_name=class_name, detector_name=detector_name, move_name=move_name, ext_unit=0)

    def show_view(self, all_moves_names):
        self.view.show_view(self.model.all_detectors, self.model.get_all_types(), all_moves_names)

    # ============================== CRUD ============================== #
    def rename_move(self, old_name, new_name):
        self.model.rename_move(old_name, new_name)

    def update_detectors(self, tbl):
        for row in range(tbl.rowCount()):
            # col 1 – var_name (QTableWidgetItem)
            item = tbl.item(row, 1)
            var_name = item.text() if item else ""

            # col 2 – class_name (QComboBox)
            combo = tbl.cellWidget(row, 2)
            class_name = combo.currentText() if combo else ""

            # col 3 – detector_name (QTableWidgetItem)
            item = tbl.item(row, 3)
            detector_name = item.text() if item else ""

            # col 4 – move_name (QComboBox)
            combo = tbl.cellWidget(row, 4)
            move_name = combo.currentText() if combo else ""

            # col 5 – ext_unit (QTableWidgetItem)
            item = tbl.item(row, 5)
            ext_unit = int(item.text()) if item and item.text().isdigit() else 0

            self.model.new_detector(var_name, class_name, detector_name, move_name, int(ext_unit))

    def remove_row(self, tbl, btn):
        for row_index in range(tbl.rowCount()):
            if tbl.cellWidget(row_index, 0) is btn:
                tbl.removeRow(row_index)
                return

    # ============================== Logic ============================== #
    def reset(self):
        self.model.reset()

    # ============================== Write To File ============================== #
    def write_to_file(self, path_init_tk1, path_tk1):
        self._write_to_init_tk1(path_init_tk1)
        self._write_to_tk1(path_tk1)

    def _write_to_init_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    for detector in self.model.all_detectors:
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

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def _write_to_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    # DDETECTOR
                    if len(self.model.get_all_d_detectors()) > 0:
                        line = "\t"
                        line += "public DDetector "
                        for detector in self.model.get_all_d_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # EDETECTOR
                    if len(self.model.get_all_e_detectors()) > 0:
                        line = "\t"
                        line += "public EDetector "
                        for detector in self.model.get_all_e_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # DE_DETECTOR
                    if len(self.model.get_all_de_detectors()) > 0:
                        line = "\t"
                        line += "public DEDetector "
                        for detector in self.model.get_all_de_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # TPDETECTOR
                    if len(self.model.get_all_tp_detectors()) > 0:
                        line = "\t"
                        line += "public TPDetector "
                        for detector in self.model.get_all_tp_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # QDETECTOR
                    if len(self.model.get_all_q_detectors()) > 0:
                        line = "\t"
                        line += "public QDetector "
                        for detector in self.model.get_all_q_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)
                    continue
                code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

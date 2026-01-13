from PyQt6.QtWidgets import QMessageBox

from Config.patterns import detectors_pattern


class DetectorController:
    """
    This class represents a matrix group.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        # self.view.remove_detector_method = self.remove_detector
        # self.view.add_detector_method = self.add_detector
        self.view.update_detectors_method = self.update_detectors

        self.view.remove_detector_method = self.remove_row
    def init_model(self, path):
        """
        This method extracts detector declarations from InitTk1.java.

        :param path: path to "InitTk1.java"
        :return: None
        """

        pattern = detectors_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                if line.startswith("//"):
                    continue
                matches = pattern.findall(line)
                for var_name, class_name, detector_name, move_name in matches:
                    # צור את הדטקטור במודל
                    self.model.new_detector(
                        var_name=var_name,
                        class_name=class_name,
                        detector_name=detector_name,
                        move_name=move_name,
                        ext_unit=0  # אם צריך אפשר לשנות
                    )

        # if len(self.moves) == 0:
        #     Log.warning(f"Warning: Moves not found")

    # def remove_detector(self, name):
    #     for detector in self.model.all_detectors:
    #         if detector.name == name:
    #             self.model.all_detectors.remove(detector)
    #             self.show_view()
    #             return
    #
    # def add_detector(self, name, type, ext_time):
    #     if name.isdigit() and type in ["TPDetector"]:
    #         QMessageBox.critical(self.view, "שגיאה", f"שם הגלאי לא תקין [tp_{name}]")
    #         return
    #     if name.isalpha() and type in ["DDetector", "EDetector", "DEDetector", "QDetector"]:
    #         type_mapping = {"DDetector": f"d_{name}", "EDetector": f"e_{name}", "DEDetector": f"de_{name}", "QDetector": f"q_{name}"}
    #         QMessageBox.critical(self.view, "שגיאה", f"שם הגלאי לא תקין [{type_mapping[type]}]")
    #         return
    #     type_mapping = {"DDetector": f"d_{name}", "EDetector": f"e_{name}", "TPDetector": f"tp_{name}", "DEDetector": f"de_{name}", "QDetector": f"q_{name}"}
    #     name = type_mapping[type]
    #     if not self.model.new_detector(name, type, ext_time):
    #         QMessageBox.critical(self.view, "שגיאה", "הגלאי כבר קיים במערכת")
    #         return
    #     self.show_view()

    def remove_row(self, tbl, btn):
        for row_index in range(tbl.rowCount()):
            if tbl.cellWidget(row_index, 0) is btn:
                tbl.removeRow(row_index)
                return

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

    def show_view(self, all_moves_names):
        self.view.show_view(self.model.all_detectors, self.model.get_all_types(), all_moves_names)

    def write_to_file(self, path_init_tk1, path_tk1):
        self._write_to_init_tk1(path_init_tk1)
        self._write_to_tk1(path_tk1)

    def _write_to_init_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write detectors here" in line:
                for detector in self.model.all_detectors:
                    line = ""
                    line += f"\t\ttk.{detector.var_name}"                   # add code
                    line += " " * (12 - len(line))                          # add spaces
                    line += f"= new {detector.class_name}"                  # add code
                    line += " " * (28 - len(line))                          # add spaces
                    line += f"( \"{detector.datector_name}\""               #
                    line += " " * (38 - len(line))                          # add spaces
                    line += f", tk.{detector.move_name}"               #
                    line += " " * (46 - len(line))                          # add spaces
                    line += ", true    , true        , true );\n"
                    code.append(line)
                continue
            code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def _write_to_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
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

            #ד
            #     for detector in self.model.all_detectors:
            #         # array
            #
            #
            #         line = ""
            #         line += f"\t\ttk.{detector.var_name}"                   # add code
            #         line += " " * (12 - len(line))                          # add spaces
            #         line += f"= new {detector.class_name}"                  # add code
            #         line += " " * (28 - len(line))                          # add spaces
            #         line += f"( \"{detector.datector_name}\""               #
            #         line += " " * (38 - len(line))                          # add spaces
            #         line += f", tk.{detector.move_name}"               #
            #         line += " " * (46 - len(line))                          # add spaces
            #         line += ", true    , true        , true );\n"
            #         code.append(line)
            #     continue
            # code.append(line)


    # def write_to_file(self, path):
    #     is_found = False
    #
    #     with open(path, 'r', encoding='utf-8') as f:
    #         lines = f.readlines()
    #
    #     new_lines = []
    #     for line in lines:
    #         if is_found == False and detectors_pattern.match(line):
    #             is_found = True
    #             self.get_lines(new_lines)
    #         else:
    #             new_lines.append(line)
    #
    #     with open(path, 'w', encoding='utf-8') as f:
    #         f.writelines(new_lines)
    #
    # def get_lines(self, new_lines):
    #     for detector in self.model.all_detectors:
    #         new_lines.append(f"tk.{detector.name} = new DDetector(\"{detector.name.upper()}\", tk.k1, true, true, true);")

    def update_names(self, old_name, new_name):
        self.model.update_names(old_name, new_name)

    def reset(self):
        self.model.reset()
